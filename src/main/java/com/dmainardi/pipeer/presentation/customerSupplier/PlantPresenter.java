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
package com.dmainardi.pipeer.presentation.customerSupplier;

import com.dmainardi.pipeer.business.customerSupplier.entity.CustomerSupplier;
import com.dmainardi.pipeer.business.customerSupplier.entity.Plant;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ContextNotActiveException;
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
public class PlantPresenter implements Serializable {
    @Inject
    CustomerPresenter customerPresenter;
    @Inject
    SupplierPresenter supplierPresenter;
    
    private String returnOutcome;
    private CustomerSupplier customerSupplier;
    private Plant plant;
    
    @PostConstruct
    public void init() {
        try {
            if (customerPresenter.getCustomer() != null) {
                customerSupplier = customerPresenter.getCustomer();
                plant = customerPresenter.getPlant();
                returnOutcome = "customer";
            }
        } catch (ContextNotActiveException e) { //customer flow is not active
        }
        
        try {
            if (supplierPresenter.getSupplier() != null) {
                customerSupplier = supplierPresenter.getSupplier();
                plant = supplierPresenter.getPlant();
                returnOutcome = "supplier";
            }
        } catch (ContextNotActiveException e) { //supplier flow is not active
        }
        
        if (customerSupplier == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "No customer or supplier has been detected. Cancel and retry."));
        }
    }
    
    public void removePlant(Plant plantBeingRemoved) {
        plantBeingRemoved.setCustomerSupplier(null);
        customerSupplier.getPlants().remove(plantBeingRemoved);
    }
    
    public String addPlant() {
        if (!customerSupplier.getPlants().contains(plant)) {
            plant.setCustomerSupplier(customerSupplier);
            customerSupplier.getPlants().add(plant);
        }
        return returnOutcome + "?faces-redirect=true";
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public String getReturnOutcome() {
        return returnOutcome;
    }
    
}
