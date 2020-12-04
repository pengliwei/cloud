package com.awei.ad.mysql.listener;

import com.awei.ad.mysql.TemplateHolder;
import com.awei.ad.mysql.dto.BinlogRowData;
import com.awei.ad.mysql.dto.TableTemplate;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 收集binlog日志
 * @author: PENGLW
 * @date: 2020/11/13
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName;
    private String tableName;

    private Map<String, IListener> listenerMap = new HashMap<>();

    private final TemplateHolder templateHolder;

    @Autowired
    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    public void register(String _dbName ,String _tableName, IListener iListener){
        log.info("register : {}-{}",_dbName,_tableName);
        this.listenerMap.put(getKey(_dbName,_tableName),iListener);

    }

    /**
     * binlog监听,获取对应的表监听，并转换为想要的java对象
     * @param event
     */
    @Override
    public void onEvent(Event event) {
        // 获取监听的binlog类型
        EventType type = event.getHeader().getEventType();
        log.debug("event type :{}",type);

        // 监听当前存储了数据库名和表名
        if (type == EventType.TABLE_MAP){
            TableMapEventData data = event.getData();
            this.dbName = data.getDatabase();
            this.tableName = data.getTable();
        }
        // 只关注数据表记录改变的监听
        if(type != EventType.EXT_WRITE_ROWS
                && type != EventType.EXT_UPDATE_ROWS
                && type != EventType.EXT_DELETE_ROWS){
            return;
        }
        // 表名和数据库名是否已填充
        if (StringUtils.isBlank(dbName) || StringUtils.isBlank(tableName)){
            return;
        }

        // 获取对应表的监听
        String key = getKey(dbName,tableName);
        IListener listener = this.listenerMap.get(key);
        if (null == listener){
            return;
        }

        log.info("event :{}",type.name());
        try{
            //java对象转换
            BinlogRowData rowData = buildRowData(event.getData());
            if (rowData == null){
                return ;
            }
            rowData.setEventType(type);
            //完成事件监听处理
            listener.onEvent(rowData);
        }catch (Exception ex){
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }

    /**
     * 获取数据库+表名
     *
     * @param dbName
     * @param tableName
     * @return
     */
    private String getKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    /**
     * 获取binlog中的输出值
     * @param eventData
     * @return
     */
    private List<Serializable[]> getAfterValues(EventData eventData) {

        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData)eventData).getRows();
        }
        // update中before和update数据放在map中的key和value中
        if (eventData instanceof UpdateRowsEventData) {
            ((UpdateRowsEventData)eventData).getRows().stream()
                    .map(Map.Entry::getValue).collect(Collectors.toList());
        }

        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData)eventData).getRows();
        }

        // 如果不是新增，修改，删除，则返回空的list
        return Collections.emptyList();
    }

    /**
     * 对象转换
     * @param eventData
     * @return
     */
    private BinlogRowData buildRowData(EventData eventData){

        TableTemplate table = templateHolder.getTable(tableName);
        if (null == table){
            log.warn("table {} not found",tableName);
            return null;
        }

        List<Map<String, String>> afterMapList = new ArrayList<>();

        List<Serializable[]> afterValues = getAfterValues(eventData);
        for (Serializable[] after : afterValues) {
            Map<String, String> afterMap = new HashMap<>();

            for (int i = 0;i<after.length;i++) {
                // 取出当前位置对应的列名
                String colName = table.getPosMap().get(i);
                // 如果没有则说明不关心这个列
                if (null == colName) {
                    log.debug("ignore position: {}",i);
                    continue;
                }

                afterMap.put(colName,after[i].toString());
            }
            afterMapList.add(afterMap);
        }

        BinlogRowData binlogRowData = new BinlogRowData();
        binlogRowData.setTable(table);
        binlogRowData.setAfter(afterMapList);

        return binlogRowData;
    }
}
