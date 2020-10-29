package com.awei.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 用户返回参数
 * @author: PENGLW
 * @date: 2020/10/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {

    private String userId;
    private String username;
    private String token;
    private String createTime;
    private String updateTime;
}
