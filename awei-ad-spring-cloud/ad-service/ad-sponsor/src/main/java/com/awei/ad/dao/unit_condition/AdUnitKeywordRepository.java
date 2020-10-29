package com.awei.ad.dao.unit_condition;

import com.awei.ad.entity.unit_condition.AdUnitKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:  推广单元关键字dao
 * @author: PENGLW
 * @date: 2020/10/28
 */
public interface AdUnitKeywordRepository extends
        JpaRepository<AdUnitKeyword, String> {
}
