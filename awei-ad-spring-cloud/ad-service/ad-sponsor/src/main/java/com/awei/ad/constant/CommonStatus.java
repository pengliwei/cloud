package com.awei.ad.constant;

import lombok.Getter;

/**
 * @description: 状态枚举
 * @author: PENGLW
 * @date: 2020/10/28
 */
@Getter
public enum CommonStatus {

    VALID(1,"有效状态"),
    INVALID(0,"无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status,String desc){
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
