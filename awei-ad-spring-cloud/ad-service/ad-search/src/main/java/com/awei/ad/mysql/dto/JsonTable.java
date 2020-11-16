package com.awei.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 数据库模板解析类
 * @author: PENGLW
 * @date: 2020/11/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonTable {

    /**
     *数据库表名
     */
    private String tableName;
    /**
     * 层级
     */
    private Integer level;

    private List<Column> insert;
    private List<Column> update;
    private List<Column> delete;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Column{
        private String column;
    }


}
