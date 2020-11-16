package com.awei.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @description: binlog实体
 * @author: PENGLW
 * @date: 2020/11/13
 */
@Data
public class BinlogRowData {

    private TableTemplate table;

    private EventType eventType;

    private List<Map<String, String>> after;

    private List<Map<String, String>> before;

}
