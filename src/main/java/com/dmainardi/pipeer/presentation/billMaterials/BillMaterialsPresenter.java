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
import com.dmainardi.pipeer.business.customerSupplier.boundary.CustomerSupplierService;
import com.dmainardi.pipeer.business.customerSupplier.entity.CustomerSupplier;
import com.dmainardi.pipeer.presentation.ExceptionUtility;
import java.io.Serializable;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Named
@FlowScoped("billMaterialsF")
public class BillMaterialsPresenter implements Serializable {

    @Inject
    BillMaterialsService service;
    @Inject
    CustomerSupplierService customerService;
    
    private CustomerSupplier customer;

    private BillMaterials billMaterials;
    private Long id;

    public String saveBillMaterials() {
        try {
            service.saveBillMaterials(billMaterials);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
            return null;
        }

        return "exitFlow";
    }

    public void detailBillMaterials() {
        if (id != null) {
            customer = null;
            if (id == 0)
                billMaterials = new BillMaterials();
            else {
                billMaterials = service.readBillMaterials(id);
                customer = billMaterials.getPlant().getCustomerSupplier();
            }
            id = null;
        }
    }

    public BillMaterials getBillMaterials() {
        return billMaterials;
    }

    public void setBillMaterials(BillMaterials billMaterials) {
        this.billMaterials = billMaterials;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerSupplier getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerSupplier customer) {
        this.customer = customer;
    }
    
}
