package com.awei.ad.entity;

import com.awei.ad.constant.CommonStatus;
import com.awei.ad.utils.IDGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @description: 用户实体
 * @author: PENGLW
 * @date: 2020/10/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_user")
public class AdUser {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Basic
    @Column(name = "username", nullable = false)
    private String username;

    @Basic
    @Column(name = "token", nullable = false)
    private String token;

    @Basic
    @Column(name = "user_status", nullable = false)
    private Integer userStatus;

    @Basic
    @Column(name = "create_time", nullable = false)
    private String createTime;

    @Basic
    @Column(name = "update_time", nullable = false)
    private String updateTime;

    public AdUser(String username,String token){
        this.username = username;
        this.token = token;
        this.id = IDGenerator.newID();
        this.userStatus = CommonStatus.VALID.getStatus();
        this.createTime = String.valueOf(System.currentTimeMillis());
        this.updateTime = this.createTime;
    }
}
