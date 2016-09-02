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
import com.dmainardi.pipeer.business.item.boundary.TagService;
import com.dmainardi.pipeer.business.item.entity.Item;
import com.dmainardi.pipeer.business.item.entity.Tag;
import com.dmainardi.pipeer.presentation.ExceptionUtility;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Named
@ViewScoped
public class ItemListPresenter implements Serializable{
    @Inject
    ItemService itemService;
    @Inject
    TagService tagService;
    
    private ItemLazyDataModel lazyItem;
    private List<Item> selectedItems;
    
    private DualListModel<Tag> tagsForPickList = new DualListModel<>();
    
    @PostConstruct
    public void init() {
        lazyItem = new ItemLazyDataModel(itemService);
    }
    
    public void deleteItems() {
        if (selectedItems != null && !selectedItems.isEmpty()) {
            for (Item itemTemp : selectedItems) {
                try {
                    itemService.deleteItem(itemTemp.getId());
                } catch (EJBException e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
                }
            }
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Missing selection", "Select a row before deleting"));
    }

    public ItemLazyDataModel getLazyItem() {
        return lazyItem;
    }

    public List<Item> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Item> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public DualListModel<Tag> getTagsForPickList() {
        if (tagsForPickList.getSource().isEmpty() && tagsForPickList.getTarget().isEmpty()) {
            tagsForPickList.setSource(tagService.listTags());
        }
        return tagsForPickList;
    }

    public void setTagsForPickList(DualListModel<Tag> tagsForPickList) {
        this.tagsForPickList = tagsForPickList;
    }
    
    public void updateTagsForSearching() {
        lazyItem.updateTags(tagsForPickList.getTarget());
    }
}
