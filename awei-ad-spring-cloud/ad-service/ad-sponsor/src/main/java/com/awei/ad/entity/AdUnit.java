package com.awei.ad.entity;

import com.awei.ad.constant.CommonStatus;
import com.awei.ad.utils.IDGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @description: 推广单元
 * @author: PENGLW
 * @date: 2020/10/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_unit")
public class AdUnit {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Basic
    @Column(name = "plan_id", nullable = false)
    private String planId;

    @Basic
    @Column(name = "unit_name", nullable = false)
    private String unitName;

    @Basic
    @Column(name = "unit_status", nullable = false)
    private Integer unitStatus;

    /**
     * 广告位类型(开屏, 贴片, 中贴...)
     */
    @Basic
    @Column(name = "position_type", nullable = false)
    private Integer positionType;

    @Basic
    @Column(name = "budget", nullable = false)
    private Long budget;

    @Basic
    @Column(name = "create_time", nullable = false)
    private String createTime;

    @Basic
    @Column(name = "update_time", nullable = false)
    private String updateTime;

    public AdUnit(String planId, String unitName,
                  Integer positionType, Long budget) {
        this.id = IDGenerator.newID();
        this.planId = planId;
        this.unitName = unitName;
        this.unitStatus = CommonStatus.VALID.getStatus();
        this.positionType = positionType;
        this.budget = budget;
        this.createTime = String.valueOf(System.currentTimeMillis());
        this.updateTime = this.createTime;
    }
}
