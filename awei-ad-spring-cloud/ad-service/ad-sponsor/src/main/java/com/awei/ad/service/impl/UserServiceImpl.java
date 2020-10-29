package com.awei.ad.service.impl;

import com.awei.ad.constant.Constants;
import com.awei.ad.dao.AdUserRepository;
import com.awei.ad.entity.AdUser;
import com.awei.ad.service.IUserService;
import com.awei.ad.utils.CommonUtils;
import com.awei.ad.vo.CreateUserRequest;
import com.awei.ad.vo.CreateUserResponse;
import com.awei.ad.exception.AdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 用户service实现层
 * @author: PENGLW
 * @date: 2020/10/28
 */
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository userRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
        //1.判断参数是否为空
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        //2.判断是否存在相同用户名的用户
        AdUser oldUser = userRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }
        //3.保存用户信息
        AdUser newUser = userRepository.save(new AdUser(request.getUsername(), CommonUtils.md5(request.getUsername())));

        return new CreateUserResponse(
                newUser.getId(), newUser.getUsername(), newUser.getToken(),
                newUser.getCreateTime(), newUser.getUpdateTime()
        );
    }
}
