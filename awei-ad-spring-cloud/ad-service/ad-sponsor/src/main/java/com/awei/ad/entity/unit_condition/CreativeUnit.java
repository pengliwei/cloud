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
 * @description: 推广单元关联创意实体
 * @author: PENGLW
 * @date: 2020/10/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creative_unit")
public class CreativeUnit {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Basic
    @Column(name = "creative_id", nullable = false)
    private String creativeId;

    @Basic
    @Column(name = "unit_id", nullable = false)
    private String unitId;

    public CreativeUnit(String creativeId, String unitId) {
        this.id = IDGenerator.newID();
        this.creativeId = creativeId;
        this.unitId = unitId;
    }
}
