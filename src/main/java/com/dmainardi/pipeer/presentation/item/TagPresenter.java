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

import com.dmainardi.pipeer.business.item.boundary.TagService;
import com.dmainardi.pipeer.business.item.entity.Tag;
import com.dmainardi.pipeer.presentation.ExceptionUtility;
import java.io.Serializable;
import java.util.List;
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
public class TagPresenter implements Serializable {
    @Inject
    TagService service;
    
    private Tag tag;
    private Long id;
    
    public List<Tag> listTags() {
        return service.listTags();
    }
    
    public String listTagsStrCSV() {
        StringBuilder b = new StringBuilder();
        for (Tag tagTemp : service.listTags()) {
            if (b.length() > 0)
                b.append(",");
            b.append(tagTemp.getName());
        }
        
        return b.toString();
    }
    
    public String saveTag() {
        try {
            service.saveTag(tag);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
            return null;
        }
        
        return "/secured/item/tags?faces-redirect=true";
    }
    
    public void detailTag() {
        if (id != null) {
            if (id == 0)
                tag = new Tag();
            else
                tag = service.readTag(id);
            id = null;
        }
    }
    
    public void deleteTag(Tag tag) {
        try {
            service.deleteTag(tag.getId());
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
        }
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
