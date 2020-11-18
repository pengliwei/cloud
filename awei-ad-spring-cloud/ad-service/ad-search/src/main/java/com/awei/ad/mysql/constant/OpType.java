package com.awei.ad.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @description: 数据库操作类型枚举
 * @author: PENGLW
 * @date: 2020/11/5
 */
public enum OpType {

    ADD,
    UPDATE,
    DELETE,
    OTHER;

    public static OpType to(EventType eventType) {

        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}
