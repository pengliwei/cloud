package com.awei.ad.service;


import com.awei.ad.vo.CreativeRequest;
import com.awei.ad.vo.CreativeResponse;

/**
 * @description: 创意service层
 * @author: PENGLW
 * @date: 2020/10/28
 */
public interface ICreativeService {

    /**
     * 创建创意
     *
     * @param request
     * @return
     */
    CreativeResponse createCreative(CreativeRequest request);
}
