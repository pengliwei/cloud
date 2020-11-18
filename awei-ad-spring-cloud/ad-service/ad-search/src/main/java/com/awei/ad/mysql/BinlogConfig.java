package com.awei.ad.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: binlog配置类
 * @author: PENGLW
 * @date: 2020/11/16
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "adconf.mysql")
public class BinlogConfig {

    private String host;
    private Integer port;
    private String username;
    private String password;

    private String binlogName;
    private Long position;
}
