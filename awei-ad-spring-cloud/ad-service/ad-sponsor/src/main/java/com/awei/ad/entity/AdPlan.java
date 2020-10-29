package com.awei.ad.entity;

import com.awei.ad.constant.CommonStatus;
import com.awei.ad.utils.IDGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: 推广计划实体
 * @author: PENGLW
 * @date: 2020/10/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_plan")
public class AdPlan {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Basic
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Basic
    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Basic
    @Column(name = "plan_status", nullable = false)
    private Integer planStatus;

    @Basic
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Basic
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Basic
    @Column(name = "create_time", nullable = false)
    private String createTime;

    @Basic
    @Column(name = "update_time", nullable = false)
    private String updateTime;

    public AdPlan(String userId, String planName,
                  Date startDate, Date endDate) {
        this.id = IDGenerator.newID();
        this.userId = userId;
        this.planName = planName;
        this.planStatus = CommonStatus.VALID.getStatus();
        this.startDate = startDate;
        this.endDate = endDate;
        this.createTime = String.valueOf(System.currentTimeMillis());
        this.updateTime = this.createTime;
    }
}
