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
import java.io.Serializable;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Named
@FlowScoped("supplierF")
public class SupplierPresenter implements Serializable{
    @Inject
    CustomerSupplierService service;
    
    private CustomerSupplier supplier;
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
    
    public void removeReferee(Referee refereeBeingRemoved) {
        refereeBeingRemoved.setCustomerSupplier(null);
        supplier.getReferees().remove(refereeBeingRemoved);
    }
    
    public String addReferee() {
        if (!supplier.getReferees().contains(referee)) {
            referee.setCustomerSupplier(supplier);
            supplier.getReferees().add(referee);
        }
        return "supplier?faces-redirect=true";
    }
    
    public String detailReferee(Referee refereeBeingOpened) {
        if (refereeBeingOpened == null)
            referee = new Referee();
        else
            referee = refereeBeingOpened;
        return "referee?faces-redirect=true";
    }
    
    public String saveSupplier() {
        service.saveCustomerSupplier(supplier);
        
        return "exitFlow";
    }
    
    public void detailSupplier() {
        if (id != null) {
            if (id == 0)
                supplier = new CustomerSupplier();
            else
                supplier = service.readCustomerSupplier(id);
            id = null;
        }
    }

    public CustomerSupplier getSupplier() {
        return supplier;
    }

    public void setSupplier(CustomerSupplier supplier) {
        this.supplier = supplier;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
