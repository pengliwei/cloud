package com.awei.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @description: 推广计划请求参数
 * @author: PENGLW
 * @date: 2020/10/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanRequest {

    private String id;
    private String userId;
    private String planName;
    private String startDate;
    private String endDate;

    public boolean createValidate() {

        return userId != null
                && !StringUtils.isEmpty(planName)
                && !StringUtils.isEmpty(startDate)
                && !StringUtils.isEmpty(endDate);
    }

    public boolean updateValidate() {

        return id != null && userId != null;
    }

    public boolean deleteValidate() {

        return id != null && userId != null;
    }
}
