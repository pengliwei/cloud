package com.awei.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: 推广计划实体
 * @author: PENGLW
 * @date: 2020/10/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlan {

    private String id;

    private String userId;

    private String planName;

    private Integer planStatus;

    private Date startDate;

    private Date endDate;

    private String createTime;

    private String updateTime;
}
