package com.awei.ad.constant;

import lombok.Getter;

/**
 * @description: 物料类型枚举
 * @author: PENGLW
 * @date: 2020/10/28
 */
@Getter
public enum CreativeMetrialType {

    JPG(1, "jpg"),
    BMP(2, "bmp"),

    MP4(3, "mp4"),
    AVI(4, "avi"),

    TXT(5, "txt");

    private int type;
    private String desc;

    CreativeMetrialType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
