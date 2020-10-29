package com.awei.ad.dao;

import com.awei.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description:  推广计划dao
 * @author: PENGLW
 * @date: 2020/10/28
 */
public interface AdPlanRepository extends JpaRepository<AdPlan, String> {

    AdPlan findByIdAndUserId(String id, String userId);

    List<AdPlan> findAllByIdInAndUserId(List<String> ids, String userId);

    AdPlan findByUserIdAndPlanName(String userId, String planName);

    List<AdPlan> findAllByPlanStatus(Integer status);
}
