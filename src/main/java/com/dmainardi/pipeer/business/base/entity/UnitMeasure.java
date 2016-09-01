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
package com.dmainardi.pipeer.business.base.entity;

import com.dmainardi.pipeer.business.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Entity
public class UnitMeasure extends BaseEntity<Long>{
    @Id
    @GeneratedValue
    private Long id;
    
    @Transient
    public static final int MAX_SYMBOL_TEXT_SIZE = 10;
    
    @Column(nullable = false, unique = true, length = MAX_SYMBOL_TEXT_SIZE)
    @NotNull
    @Size(max = MAX_SYMBOL_TEXT_SIZE)
    private String symbol;
    
    @Column(nullable = false)
    @NotNull
    private String name;
    
    @Version
    private int version;

    public UnitMeasure() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
