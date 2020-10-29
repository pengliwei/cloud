package com.awei.ad.entity.unit_condition;

import com.awei.ad.utils.IDGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 推广单元区域实体
 * @author: PENGLW
 * @date: 2020/10/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_unit_district")
public class AdUnitDistrict {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Basic
    @Column(name = "unit_id", nullable = false)
    private String unitId;

    @Basic
    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "city", nullable = false)
    @Basic
    private String city;

    public AdUnitDistrict(String unitId, String province, String city) {
        this.id = IDGenerator.newID();
        this.unitId = unitId;
        this.province = province;
        this.city = city;
    }
}
