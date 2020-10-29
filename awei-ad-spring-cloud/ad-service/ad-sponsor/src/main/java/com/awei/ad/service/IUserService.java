package com.awei.ad.service;

import com.awei.ad.exception.AdException;
import com.awei.ad.vo.CreateUserRequest;
import com.awei.ad.vo.CreateUserResponse;

/**
 * @description: 用户service层
 * @author: PENGLW
 * @date: 2020/10/28
 */
public interface IUserService {

    /**
     * 创建用户
     *
     * @param request
     * @return
     * @throws AdException
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
