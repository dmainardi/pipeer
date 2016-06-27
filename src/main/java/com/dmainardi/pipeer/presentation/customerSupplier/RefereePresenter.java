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
import com.dmainardi.pipeer.business.customerSupplier.entity.Referee;
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
public class RefereePresenter implements Serializable {
    @Inject
    CustomerPresenter customerPresenter;
    @Inject
    SupplierPresenter supplierPresenter;
    
    private String returnOutcome;
    private CustomerSupplier customerSupplier;
    private Referee referee;
    
    @PostConstruct
    public void init() {
        try {
            if (customerPresenter.getCustomer() != null) {
                customerSupplier = customerPresenter.getCustomer();
                referee = customerPresenter.getReferee();
                returnOutcome = "customer";
            }
        } catch (ContextNotActiveException e) { //customer flow is not active
        }
        
        try {
            if (supplierPresenter.getSupplier() != null) {
                customerSupplier = supplierPresenter.getSupplier();
                referee = supplierPresenter.getReferee();
                returnOutcome = "supplier";
            }
        } catch (ContextNotActiveException e) { //supplier flow is not active
        }
        
        if (customerSupplier == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "No customer or supplier has been detected. Cancel and retry."));
        }
    }
    
    public void removeReferee(Referee refereeBeingRemoved) {
        refereeBeingRemoved.setCustomerSupplier(null);
        customerSupplier.getReferees().remove(refereeBeingRemoved);
    }
    
    public String addReferee() {
        if (!customerSupplier.getReferees().contains(referee)) {
            referee.setCustomerSupplier(customerSupplier);
            customerSupplier.getReferees().add(referee);
        }
        return returnOutcome + "?faces-redirect=true";
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public String getReturnOutcome() {
        return returnOutcome;
    }
    
}
