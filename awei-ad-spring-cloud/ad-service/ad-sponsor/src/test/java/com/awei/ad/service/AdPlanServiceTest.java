package com.awei.ad.service;

import com.awei.ad.Application;
import com.awei.ad.exception.AdException;
import com.awei.ad.vo.AdPlanGetRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * @description: 推广计划
 * @author: PENGLW
 * @date: 2020/11/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdPlanServiceTest {

    @Autowired
    private IAdPlanService planService;

    @Test
    public void testGetAdPlan() throws AdException{
        System.out.println(planService.getAdPlanByIds(
                new AdPlanGetRequest(15L, Collections.singletonList(10L))
        ));
    }
}
