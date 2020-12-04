package com.awei.ad.search.impl;

import com.alibaba.fastjson.JSON;
import com.awei.ad.index.CommonStatus;
import com.awei.ad.index.DataTable;
import com.awei.ad.index.adunit.AdUnitIndex;
import com.awei.ad.index.adunit.AdUnitObject;
import com.awei.ad.index.creative.CreativeIndex;
import com.awei.ad.index.creative.CreativeObject;
import com.awei.ad.index.creativeunit.CreativeUnitIndex;
import com.awei.ad.index.district.UnitDistrictIndex;
import com.awei.ad.index.interest.UnitItIndex;
import com.awei.ad.index.keyword.UnitKeywordIndex;
import com.awei.ad.search.ISearch;
import com.awei.ad.search.vo.SearchRequest;
import com.awei.ad.search.vo.SearchResponse;
import com.awei.ad.search.vo.feature.DistrictFeature;
import com.awei.ad.search.vo.feature.FeatureRelation;
import com.awei.ad.search.vo.feature.ItFeature;
import com.awei.ad.search.vo.feature.KeywordFeature;
import com.awei.ad.search.vo.media.AdSlot;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:检索服务service实现层
 * @author: PENGLW
 * @date: 2020/11/12
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {

    public SearchResponse fallback(SearchRequest request, Throwable e) {
        return null;
    }

    /**
     * HystrixCommand ：发生错误时回退方法，原理：通过try，catch，finally形式，反射调用fallbackMethod方法
     * @param request
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "fallback")
    public SearchResponse fetchAds(SearchRequest request) {

        // 请求广告位信息
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();

        // 三个feature:位置信息,兴趣，关键字，匹配规则
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        FeatureRelation relation = request.getFeatureInfo().getRelation();

        // 构造响应对象
        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlotToAds = response.getAdSlotToAds();

        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIdSet;

            // 根据流量类型获取初始AdUnit
            Set<Long> adUnitIdSet = DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());

            if (relation == FeatureRelation.AND){
                filterKeywordFeature(adUnitIdSet,keywordFeature);
                filterDistrictFeature(adUnitIdSet, districtFeature);
                filterItTagFeature(adUnitIdSet, itFeature);

                targetUnitIdSet = adUnitIdSet;
            }else{
                targetUnitIdSet = getORRelationUnitIds(
                        adUnitIdSet,
                        keywordFeature,
                        districtFeature,
                        itFeature
                );
            }

            List<AdUnitObject> unitObjects =
                    DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);
            // 根据状态过滤出
            filterAdUnitAndPlanStatus(unitObjects, CommonStatus.VALID);
            // 获取创意对象
            List<Long> creativeIds = DataTable.of(CreativeUnitIndex.class)
                    .selectAds(unitObjects);
            List<CreativeObject> creatives = DataTable.of(CreativeIndex.class)
                    .fetch(creativeIds);

            // 通过 AdSlot 实现对 CreativeObject 的过滤
            filterCreativeByAdSlot(
                    creatives,
                    adSlot.getWidth(),
                    adSlot.getHeight(),
                    adSlot.getType()
            );

            adSlotToAds.put(
                    adSlot.getAdSlotCode(), buildCreativeResponse(creatives)
            );
        }

        log.info("fetchAds: {}-{}",
                JSON.toJSONString(request),
                JSON.toJSONString(response));

        return response;
    }

    /**
     * 获取过滤的uAdUnitIdSet
     * @param adUnitIdSet
     * @param keywordFeature
     * @param districtFeature
     * @param itFeature
     * @return
     */
    private Set<Long> getORRelationUnitIds(Set<Long> adUnitIdSet,
                                           KeywordFeature keywordFeature,
                                           DistrictFeature districtFeature,
                                           ItFeature itFeature) {

        if (CollectionUtils.isEmpty(adUnitIdSet)) {
            return Collections.emptySet();
        }

        Set<Long> keywordUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> itUnitIdSet = new HashSet<>(adUnitIdSet);

        filterKeywordFeature(keywordUnitIdSet, keywordFeature);
        filterDistrictFeature(districtUnitIdSet, districtFeature);
        filterItTagFeature(itUnitIdSet, itFeature);

        return new HashSet<>(
                CollectionUtils.union(
                        CollectionUtils.union(keywordUnitIdSet, districtUnitIdSet),
                        itUnitIdSet
                )
        );
    }

    /**
     * 关键词过滤
     *
     * @param adUnitIds
     * @param keywordFeature
     */
    private void filterKeywordFeature(
            Collection<Long> adUnitIds, KeywordFeature keywordFeature) {

        if (CollectionUtils.isEmpty(keywordFeature.getKeywords())) {
            return;
        }

        if (CollectionUtils.isNotEmpty(adUnitIds)) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId->
                            DataTable.of(UnitKeywordIndex.class)
                                    .match(adUnitId,keywordFeature.getKeywords()));
        }
    }

    /**
     * 地域过滤
     * @param adUnitIds
     * @param districtFeature
     */
    private void filterDistrictFeature(
            Collection<Long> adUnitIds, DistrictFeature districtFeature
    ) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {

            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId ->
                            DataTable.of(UnitDistrictIndex.class)
                                    .match(adUnitId,
                                            districtFeature.getDistricts())
            );
        }
    }

    /**
     * 兴趣过滤
     * @param adUnitIds
     * @param itFeature
     */
    private void filterItTagFeature(Collection<Long> adUnitIds,
                                    ItFeature itFeature) {

        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {

            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId ->
                            DataTable.of(UnitItIndex.class)
                                    .match(adUnitId,
                                            itFeature.getIts())
            );
        }
    }

    /**
     * 根据状态过滤
     * @param unitObjects
     * @param status
     */
    private void filterAdUnitAndPlanStatus(List<AdUnitObject> unitObjects,
                                           CommonStatus status) {

        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }
        CollectionUtils.filter(
                unitObjects,
                object -> object.getUnitStatus().equals(status.getStatus())
                        && object.getAdPlanObject().getPlanStatus().equals(status.getStatus())
        );
    }

    private void filterCreativeByAdSlot(List<CreativeObject> creatives,
                                        Integer width,
                                        Integer height,
                                        List<Integer> type) {

        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        CollectionUtils.filter(
                creatives,
                creative ->
                        creative.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                                && creative.getWidth().equals(width)
                                && creative.getHeight().equals(height)
                                && type.contains(creative.getType())
        );
    }

    /**
     * 构建返回对象
     * @param creatives
     * @return
     */
    private List<SearchResponse.Creative> buildCreativeResponse(
            List<CreativeObject> creatives
    ) {

        if (CollectionUtils.isEmpty(creatives)) {
            return Collections.emptyList();
        }

        // 随机获取list中的一个对象
        CreativeObject randomObject = creatives.get(
                Math.abs(new Random().nextInt()) % creatives.size()
        );

        return Collections.singletonList(
                SearchResponse.convert(randomObject)
        );
    }
}
