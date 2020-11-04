package com.awei.ad.client;

import com.awei.ad.client.vo.AdPlan;
import com.awei.ad.client.vo.AdPlanGetRequest;
import com.awei.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @description: 调用投放系统微服务
 * @author: PENGLW
 * @date: 2020/11/2
 * 调用微服务异常，则服务降级
 */
@FeignClient(value = "eureka-client-ad-sponsor", fallback = SponsorClientHystrix.class)
public interface SponsorClient {

    /**
     * 调用推广计划接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ad-sponsor/get/adPlan", method = RequestMethod.POST)
    CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request);
}
