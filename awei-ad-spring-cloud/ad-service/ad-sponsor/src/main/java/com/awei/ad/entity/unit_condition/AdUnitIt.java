package com.awei.ad.entity.unit_condition;

import com.awei.ad.utils.IDGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 推广单元标签实体
 * @author: PENGLW
 * @date: 2020/10/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_unit_it")
public class AdUnitIt {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Basic
    @Column(name = "unit_id", nullable = false)
    private String unitId;

    @Basic
    @Column(name = "it_tag", nullable = false)
    private String itTag;

    public AdUnitIt(String unitId, String itTag) {
        this.id = IDGenerator.newID();
        this.unitId = unitId;
        this.itTag = itTag;
    }
}
