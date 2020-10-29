package com.awei.ad.constant;

import lombok.Getter;

/**
 * @description: 创意类型枚举
 * @author: PENGLW
 * @date: 2020/10/28
 */
@Getter
public enum CreativeType {

    IMAGE(1,"图片"),
    VIDEO(2,"视频"),
    TEXT(3,"文本");

    private int type;
    private String desc;

    CreativeType(int type, String desc){
        this.type = type;
        this.desc = desc;
    }
}
