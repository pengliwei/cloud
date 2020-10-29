package com.awei.ad.dao;

import com.awei.ad.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: 创意dao
 * @author: PENGLW
 * @date: 2020/10/28
 */
public interface CreativeRepository extends JpaRepository<Creative, String> {
}
