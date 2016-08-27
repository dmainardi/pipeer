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

import com.dmainardi.pipeer.business.billMaterials.entity.ItemNode;
import com.dmainardi.pipeer.business.billMaterials.entity.ProcessNode;
import com.dmainardi.pipeer.business.item.boundary.ItemService;
import com.dmainardi.pipeer.business.item.entity.Item;
import com.dmainardi.pipeer.business.workshop.entity.Process;
import com.dmainardi.pipeer.presentation.ExceptionUtility;
import com.dmainardi.pipeer.presentation.billMaterials.BillMaterialsPresenter;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJBException;
import javax.enterprise.context.ContextNotActiveException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Named
@FlowScoped("itemF")
public class ItemPresenter implements Serializable {

    @Inject
    ItemService service;
    @Inject
    BillMaterialsPresenter billMaterialsPresenter;

    private Item item;
    private Long id;
    
    private String returnOutcome = "exitFlow";
    private String returnOutcomeFromMethod = returnOutcome;
    
    @PostConstruct
    public void init() {
        /*try {
            if (billMaterialsPresenter.getNode() != null && billMaterialsPresenter.getNode() instanceof ItemNode) {
                item = ((ItemNode) billMaterialsPresenter.getNode()).getItem();
                returnOutcome = "returnToItemNode";
                returnOutcomeFromMethod = "returnToItemNode";
            }
        } catch (ContextNotActiveException e) { //billMaterials flow is not active
        }*/
        System.out.println("Item presenter created");
    }
    
    @PreDestroy
    public void end() {
        System.out.println("Item presenter destroyed");
    }
    
    public String detailProcess(Process processBeingOpened) {
        if (processBeingOpened == null)
            item.setProcess(new Process(item.getName()));
        else
            item.setProcess(processBeingOpened);
        return "openProcess";
    }

    public String saveItem() {
        try {
            service.saveItem(item);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
            return null;
        }
        
        /*try {
            if (billMaterialsPresenter.getNode() != null && billMaterialsPresenter.getNode() instanceof ProcessNode) {
                billMaterialsPresenter.setSelectedItem(item);
                billMaterialsPresenter.getNode().setPrice(new BigDecimal(item.getStandardCost().doubleValue()));
            }
        } catch (ContextNotActiveException e) { //billMaterials flow is not active
        }*/

        return returnOutcomeFromMethod;
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

    public String getReturnOutcome() {
        return returnOutcome;
    }

    public void setReturnOutcome(String returnOutcome) {
        this.returnOutcome = returnOutcome;
    }

    public String getReturnOutcomeFromMethod() {
        return returnOutcomeFromMethod;
    }

    public void setReturnOutcomeFromMethod(String returnOutcomeFromMethod) {
        this.returnOutcomeFromMethod = returnOutcomeFromMethod;
    }
    
}
