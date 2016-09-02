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
package com.dmainardi.pipeer.presentation.item;

import com.dmainardi.pipeer.business.item.boundary.ItemService;
import com.dmainardi.pipeer.business.item.entity.Item;
import com.dmainardi.pipeer.business.item.entity.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
public class ItemLazyDataModel extends LazyDataModel<Item> {
    private final ItemService service;
    private final List<Tag> tags;

    public ItemLazyDataModel(ItemService service) {
        this.service = service;
        this.tags = new ArrayList<>();
    }
    
    @Override
    public Object getRowKey(Item object) {
        return object.getId();
    }

    @Override
    public Item getRowData(String rowKey) {
        try {
            return service.readItem(Long.parseLong(rowKey));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<Item> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        Boolean isAscending = null;
        
        switch (sortOrder) {
            case ASCENDING:
                isAscending = Boolean.TRUE;
                break;
            case DESCENDING:
                isAscending = Boolean.FALSE;
                break;
            default:
        }
        List<Item> result = service.listItems(tags, first, pageSize, filters, sortField, isAscending);
        this.setRowCount(service.getItemsCount(tags, filters).intValue());
        
        return result;
    }
    
    public void updateTags(List<Tag> tags) {
        this.tags.clear();
        this.tags.addAll(tags);
    }
}
