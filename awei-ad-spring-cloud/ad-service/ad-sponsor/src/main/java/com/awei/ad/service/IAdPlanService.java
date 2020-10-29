package com.awei.ad.service;


import com.awei.ad.entity.AdPlan;
import com.awei.ad.exception.AdException;
import com.awei.ad.vo.AdPlanGetRequest;
import com.awei.ad.vo.AdPlanRequest;
import com.awei.ad.vo.AdPlanResponse;

import java.util.List;

/**
 * @description: 推广计划service层
 * @author: PENGLW
 * @date: 2020/10/28
 */
public interface IAdPlanService {


    /**
     * 创建推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 获取推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    /**
     * 更新推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 删除推广计划
     *
     * @param request
     * @throws AdException
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
