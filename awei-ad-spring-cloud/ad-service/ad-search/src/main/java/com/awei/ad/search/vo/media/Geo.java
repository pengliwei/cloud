package com.awei.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 地理位置信息
 * @author: PENGLW
 * @date: 2020/11/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {

    /**
     * 纬度和经度
     */
    private Float latitude;
    private Float longitude;

    private String city;
    private String province;
}
