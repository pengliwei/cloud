package com.awei.ad.mysql.dto;

import com.awei.ad.mysql.constant.OpType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @description:
 * @author: PENGLW
 * @date: 2020/11/12
 */
@Data
public class ParseTemplate {

    private String database;

    private Map<String, TableTemplate> tableTemplateMap = new HashMap<>();

    /**
     * 对象转换
     * @param _template
     * @return
     */
    public static ParseTemplate parse(Template _template) {

        ParseTemplate template = new ParseTemplate();
        template.setDatabase(_template.getDatabase());

        for (JsonTable table : _template.getTableList()) {

            String name = table.getTableName();
            Integer level = table.getLevel();

            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(name);
            tableTemplate.setLevel(level.toString());
            template.tableTemplateMap.put(name, tableTemplate);

            Map<OpType, List<String>> opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();
            for (JsonTable.Column column : table.getInsert()) {
                getAndCreateIfNeed(
                        OpType.ADD,
                        opTypeFieldSetMap,
                        ArrayList::new
                        ).add(column.getColumn());
            }
            for (JsonTable.Column column : table.getUpdate()) {
                getAndCreateIfNeed(
                        OpType.UPDATE,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
            for (JsonTable.Column column : table.getDelete()) {
                getAndCreateIfNeed(
                        OpType.DELETE,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }

        }

        return template;
    }

    private static <T, R> R getAndCreateIfNeed(T key, Map<T, R> map,
                                               Supplier<R> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }
}
