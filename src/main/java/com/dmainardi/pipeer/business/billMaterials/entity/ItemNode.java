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
package com.dmainardi.pipeer.business.billMaterials.entity;

import com.dmainardi.pipeer.business.item.entity.Item;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Entity
@DiscriminatorValue(value = "itm")
public class ItemNode extends Node {
    
    @NotNull
    @ManyToOne(optional = false)
    private Item item;

    @Override
    public String getDescription() {
        if (item != null)
            return item.getCode() + " - " + item.getName();
        else
            return "Select an item";
    }

    @Override
    public String getUnitMeasure() {
        return item.getUnitMeasure().getSymbol();
    }
    
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    Node duplicate() {
        ItemNode result = new ItemNode();
        
        super.duplicateCommonFields(result);
        result.setItem(item);
        
        return result;
    }
    
}
