package com.awei.ad.dao;

import com.awei.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:  用户dao
 * @author: PENGLW
 * @date: 2020/10/28
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    /**
     * 根据用户名查找用户记录
     * @param username
     * @return
     */
    AdUser findByUsername(String username);

}
