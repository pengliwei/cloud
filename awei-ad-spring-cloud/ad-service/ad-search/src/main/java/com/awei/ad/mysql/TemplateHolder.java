package com.awei.ad.mysql;

import com.alibaba.fastjson.JSON;
import com.awei.ad.mysql.constant.OpType;
import com.awei.ad.mysql.dto.ParseTemplate;
import com.awei.ad.mysql.dto.TableTemplate;
import com.awei.ad.mysql.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @description: 解析模板文件，列索引——>列名映射关系
 * @author: PENGLW
 * @date: 2020/11/13
 */
@Slf4j
@Component
public class TemplateHolder {

    private ParseTemplate parseTemplate;

    private final JdbcTemplate jdbcTemplate;

    /**
     * 查询列名和索引sql
     */
    private String SQL_SCHEMA = "select table_schema, table_name, " +
            "column_name, ordinal_position from information_schema.columns " +
            "where table_schema = ? and table_name = ?";

    @Autowired
    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 当TemplateHolder被容器加载时，这个方法会被执行
     */
    @PostConstruct
    private void init(){
        loadJson("template.json");
    }

    public TableTemplate getTable(String tableName) {
        return parseTemplate.getTableTemplateMap().get(tableName);
    }

    /**
     * 解析模板文件
     * @param path
     */
    private void loadJson(String path) {
        // 获取当前线程的上下文的输入流
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inStream = classLoader.getResourceAsStream(path);

        try{
            Template template = JSON.parseObject(inStream, Charset.defaultCharset(), Template.class);
            this.parseTemplate = ParseTemplate.parse(template);

            loadMeta();
        }catch (IOException ex){
            log.error(ex.getMessage());
            throw new RuntimeException("fail to parse json file");
        }
    }

    /**
     * 列索引——>列名映射关系
     */
    private void loadMeta() {

        //循环表，列名映射处理
        for (Map.Entry<String, TableTemplate> entry : parseTemplate.getTableTemplateMap().entrySet()) {

            TableTemplate tableTemplate = entry.getValue();
            List<String> insertFields = tableTemplate.getOpTypeFieldSetMap().get(
                    OpType.ADD);
            List<String> updateFields = tableTemplate.getOpTypeFieldSetMap().get(
                    OpType.UPDATE
            );
            List<String> deleteFields = tableTemplate.getOpTypeFieldSetMap().get(
                    OpType.DELETE
            );

            jdbcTemplate.query(SQL_SCHEMA, new Object[]{
                    parseTemplate.getDatabase(), tableTemplate.getTableName()
            }, (rs, i) -> {

                int pos = rs.getInt("ORDINAL_POSITION");
                String colName = rs.getString("COLUMN_NAME");

                if ((null != updateFields && updateFields.contains(colName))
                        || (null != insertFields && insertFields.contains(colName))
                        || (null != deleteFields && deleteFields.contains(colName))) {
                    tableTemplate.getPosMap().put(pos - 1, colName);
                }

                return null;
            });
        }
    }
}
