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
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Named
@ViewScoped
public class ItemPresenter implements Serializable {

    @Inject
    ItemService service;
    
    @Inject
    TagService tagService;

    private Item item;
    private Long id;

    public String saveItem() {
        try {
            service.saveItem(item);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
            return null;
        }

        return "/secured/item/items?faces-redirect=true";
    }

    public void detailItem() {
        if (id != null) {
            if (id == 0) {
                item = new Item();
            } else {
                item = service.readItem(id);
            }
            id = null;
        }
    }

    public void deleteItem(Item item) {
        service.deleteItem(item.getId());
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
