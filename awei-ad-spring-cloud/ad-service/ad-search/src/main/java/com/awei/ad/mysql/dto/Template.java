package com.awei.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: PENGLW
 * @date: 2020/11/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    private String database;
    private List<JsonTable> tableList;

}
