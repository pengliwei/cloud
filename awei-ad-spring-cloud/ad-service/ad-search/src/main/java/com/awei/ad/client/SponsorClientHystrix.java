package com.awei.ad.client;

import com.awei.ad.client.vo.AdPlan;
import com.awei.ad.client.vo.AdPlanGetRequest;
import com.awei.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 断路器
 * @author: PENGLW
 * @date: 2020/11/2
 */
@Component
public class SponsorClientHystrix implements SponsorClient {

    /**
     * 调用sponsor服务异常，则服务降级，实现调用此接口
     *
     * @param request
     * @return
     */
    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1, "eureka-client-ad-sponsor error");
    }
}
