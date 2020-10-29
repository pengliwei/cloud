package com.awei.ad.service;


import com.awei.ad.exception.AdException;
import com.awei.ad.vo.*;

/**
 * @description: 推广单位service层
 * @author: PENGLW
 * @date: 2020/10/28
 */
public interface IAdUnitService {

    /**
     * 创建推广单位
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;

    /**
     * 创建推广单位关键字
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request)
            throws AdException;

    /**
     * 创建推广单位标签
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitItResponse createUnitIt(AdUnitItRequest request)
            throws AdException;

    /**
     * 创建推广单位区域
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request)
            throws AdException;

    /**
     * 创建单位创意中间表数据
     *
     * @param request
     * @return
     * @throws AdException
     */
    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request)
            throws AdException;
}
