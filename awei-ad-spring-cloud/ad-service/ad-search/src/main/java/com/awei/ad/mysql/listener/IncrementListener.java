package com.awei.ad.mysql.listener;

import com.awei.ad.mysql.constant.Constant;
import com.awei.ad.mysql.constant.OpType;
import com.awei.ad.mysql.dto.BinlogRowData;
import com.awei.ad.mysql.dto.MySqlRowData;
import com.awei.ad.mysql.dto.TableTemplate;
import com.awei.ad.sender.ISender;
import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 增量数据监听
 * @author: PENGLW
 * @date: 2020/11/16
 */
@Slf4j
@Component
public class IncrementListener implements IListener{

    @Resource(name = "kafkaSender")
    private ISender sender;

    private final AggregationListener aggregationListener;

    @Autowired
    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    @PostConstruct
    @Override
    public void registe() {

        log.info("IncrementListener register db and table info");
        Constant.table2Db.forEach((k,v)->
                aggregationListener.register(v,k,this));
    }

    @Override
    public void onEvent(BinlogRowData eventData) {

        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();

        // 包装成最后需要投递的数据
        MySqlRowData rowData = new MySqlRowData();

        rowData.setTableName(table.getTableName());
        rowData.setLevel(table.getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);


        List<String> fieldList = table.getOpTypeFieldSetMap().get(opType);
        if (null == fieldList) {
            log.warn("{} not support for {}", opType, table.getTableName());
            return;
        }

        for (Map<String, String> afterMap : eventData.getAfter()) {
            Map<String, String> _afterMap = new HashMap<>();

            for (Map.Entry<String, String> entry : afterMap.entrySet()) {

                String colName = entry.getKey();
                String colValue = entry.getValue();

                _afterMap.put(colName, colValue);
            }

            rowData.getFieldValueMap().add(_afterMap);
        }
        sender.sender(rowData);
    }
}
