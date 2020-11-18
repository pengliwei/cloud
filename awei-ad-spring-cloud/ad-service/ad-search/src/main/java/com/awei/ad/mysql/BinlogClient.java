package com.awei.ad.mysql;

import com.awei.ad.mysql.listener.AggregationListener;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @description: 连接mysql并监听binlog
 * @author: PENGLW
 * @date: 2020/11/16
 */
@Slf4j
@Component
public class BinlogClient {

    private BinaryLogClient client;

    private final BinlogConfig config;
    private final AggregationListener listener;

    @Autowired
    public BinlogClient(BinlogConfig config, AggregationListener listener) {
        this.config = config;
        this.listener = listener;
    }

    /**
     * binlog监听，通过新线程
     */
    public void connect(){
        new Thread(() ->{
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );
            if (StringUtils.isNotBlank(config.getBinlogName()) && !config.getPosition().equals(-1L)){
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }
            // 设置监听类
            client.registerEventListener(listener);

            try{
                log.info("connecting to mysql start");
                client.connect();
                log.info("connecting to mysql done");
            }catch (IOException ex){
                ex.printStackTrace();
            }

        }).start();
    }

    /**
     * 关闭binlog监听
     */
    public void close() {
        try {
            client.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
