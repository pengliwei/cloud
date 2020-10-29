package com.awei.ad.controller;

import com.alibaba.fastjson.JSON;
import com.awei.ad.exception.AdException;
import com.awei.ad.service.IUserService;
import com.awei.ad.vo.CreateUserRequest;
import com.awei.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 用户control
 * @author: PENGLW
 * @date: 2020/10/29
 */
@Slf4j
@RestController
public class UserOPContoller {

    @Autowired
    private IUserService userService;

    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException {
        log.info("ad-sponsor：createUser -> {}", JSON.toJSONString(request));
        return userService.createUser(request);
    }

}
