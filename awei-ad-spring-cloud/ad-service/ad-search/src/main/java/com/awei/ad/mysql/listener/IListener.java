package com.awei.ad.mysql.listener;

import com.awei.ad.mysql.dto.BinlogRowData;

/**
 * @description: binlog监听
 * @author: PENGLW
 * @date: 2020/11/13
 */
public interface IListener {

    void registe();

    /**
     * 数据监听
     * @param eventData
     */
    void onEvent(BinlogRowData eventData);
}
