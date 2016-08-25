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
package com.dmainardi.pipeer.presentation.billMaterials;

import com.dmainardi.pipeer.business.billMaterials.boundary.BillMaterialsService;
import com.dmainardi.pipeer.business.billMaterials.entity.BillMaterials;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
public class BillMaterialsLazyDataModel extends LazyDataModel<BillMaterials> {
    private final BillMaterialsService service;

    public BillMaterialsLazyDataModel(BillMaterialsService service) {
        this.service = service;
    }
    
    @Override
    public Object getRowKey(BillMaterials object) {
        return object.getId();
    }

    @Override
    public BillMaterials getRowData(String rowKey) {
        try {
            return service.readBillMaterials(Long.parseLong(rowKey));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<BillMaterials> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
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
        List<BillMaterials> result = service.listBillsMaterials(first, pageSize, filters, sortField, isAscending);
        this.setRowCount(service.getBillsMaterialsCount(filters).intValue());
        
        return result;
    }
}
