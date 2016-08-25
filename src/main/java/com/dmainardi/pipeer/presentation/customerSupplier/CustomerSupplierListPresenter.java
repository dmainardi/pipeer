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
import com.dmainardi.pipeer.presentation.ExceptionUtility;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
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
public class CustomerSupplierListPresenter implements Serializable{
    @Inject
    CustomerSupplierService customerSupplierService;
    
    private CustomerSupplierLazyDataModel lazySuppliers;
    private CustomerSupplierLazyDataModel lazyCustomers;
    private List<CustomerSupplier> selectedCustomerSuppliers;
    
    @PostConstruct
    public void init() {
        lazyCustomers = new CustomerSupplierLazyDataModel(customerSupplierService, Boolean.TRUE, null);
        lazySuppliers = new CustomerSupplierLazyDataModel(customerSupplierService, null, Boolean.TRUE);
    }
    
    public void deleteCustomerSuppliers() {
        if (selectedCustomerSuppliers != null && !selectedCustomerSuppliers.isEmpty()) {
            for (CustomerSupplier customerSupplierTemp : selectedCustomerSuppliers) {
                try {
                    customerSupplierService.deleteCustomerSupplier(customerSupplierTemp.getId());
                } catch (EJBException e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
                }
            }
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Missing selection", "Select a row before deleting"));
    }
    
    public List<CustomerSupplier> completeCustomer(String query) {
        String field = "name";
        Map<String, Object> filter = new HashMap<>();
        filter.put(field, query);
        return customerSupplierService.listCustomerSuppliers(Boolean.TRUE, null, 0, 10, filter, field, Boolean.TRUE);
    }

    public CustomerSupplierLazyDataModel getLazySuppliers() {
        return lazySuppliers;
    }

    public CustomerSupplierLazyDataModel getLazyCustomers() {
        return lazyCustomers;
    }

    public List<CustomerSupplier> getSelectedCustomerSuppliers() {
        return selectedCustomerSuppliers;
    }

    public void setSelectedCustomerSuppliers(List<CustomerSupplier> selectedCustomerSuppliers) {
        this.selectedCustomerSuppliers = selectedCustomerSuppliers;
    }
    
}
