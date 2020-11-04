package com.awei.ad.dao.unit_condition;

import com.awei.ad.entity.unit_condition.CreativeUnit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:  推广单位创意dao
 * @author: PENGLW
 * @date: 2020/10/28
 */
public interface CreativeUnitRepository
        extends JpaRepository<CreativeUnit, Long> {
}
