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

import com.dmainardi.pipeer.business.customerSupplier.boundary.CustomerSupplierService;
import com.dmainardi.pipeer.business.customerSupplier.entity.CustomerSupplier;
import com.dmainardi.pipeer.business.customerSupplier.entity.Plant;
import com.dmainardi.pipeer.business.customerSupplier.entity.Referee;
import com.dmainardi.pipeer.presentation.ExceptionUtility;
import java.io.Serializable;
import javax.ejb.EJBException;
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
@FlowScoped("customerF")
public class CustomerPresenter implements Serializable{
    @Inject
    CustomerSupplierService service;
    
    private CustomerSupplier customer;
    private Plant plant;
    private Referee referee;
    private Long id;
    
    public String detailPlant(Plant plantBeingOpened) {
        if (plantBeingOpened == null)
            plant = new Plant();
        else
            plant = plantBeingOpened;
        return "plant?faces-redirect=true";
    }
    
    public String detailReferee(Referee refereeBeingOpened) {
        if (refereeBeingOpened == null)
            referee = new Referee();
        else
            referee = refereeBeingOpened;
        return "referee?faces-redirect=true";
    }
    
    public String saveCustomer() {
        try {
            service.saveCustomerSupplier(customer);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
            return null;
        }
        
        return "exitFlow";
    }
    
    public void detailCustomer() {
        if (id != null) {
            if (id == 0) {
                customer = new CustomerSupplier();
                customer.setIsCustomer(Boolean.TRUE);
            }
            else
                customer = service.readCustomerSupplier(id);
            id = null;
        }
    }

    public CustomerSupplier getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerSupplier customer) {
        this.customer = customer;
    }

    public Plant getPlant() {
        return plant;
    }

    public Referee getReferee() {
        return referee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
