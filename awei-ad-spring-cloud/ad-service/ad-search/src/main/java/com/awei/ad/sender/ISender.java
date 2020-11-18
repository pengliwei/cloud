package com.awei.ad.sender;


import com.awei.ad.mysql.dto.MySqlRowData;

/**
 * @description: 投递增量数据接口
 * @author: PENGLW
 * @date: 2020/11/5
 */
public interface ISender {

    void sender(MySqlRowData rowData);
}
