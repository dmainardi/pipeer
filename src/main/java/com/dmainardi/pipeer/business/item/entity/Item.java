/*
 * Copyright (C) 2016 Davide Mainardi <ingmainardi at live.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dmainardi.pipeer.business.item.entity;

import com.dmainardi.pipeer.business.base.entity.UnitMeasure;
import com.dmainardi.pipeer.business.entity.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Entity
public class Item extends BaseEntity<Long>{
    @Id
    @GeneratedValue
    private Long id;
    
    @Transient
    private final int maxCodeTextSize = 50;
    
    @NotNull
    @Size(max = maxCodeTextSize)
    @Column(nullable = false, unique = true, length = maxCodeTextSize)
    private String code;
    
    private String warehouseCode;
    
    @NotNull
    @Column(nullable = false)
    private String name;
    
    @NotNull
    @ManyToOne(optional = false)
    private UnitMeasure unitMeasure;
    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Tag> tags;
    
    @Version
    private int version;

    public Item() {
        tags = new ArrayList<>();
    }

    @Override
    public Long getId() {
        return id;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnitMeasure getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(UnitMeasure unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getMaxCodeTextSize() {
        return maxCodeTextSize;
    }
    
}
