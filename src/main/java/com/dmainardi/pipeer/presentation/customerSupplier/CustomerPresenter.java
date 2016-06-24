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
@FlowScoped("customerF")
public class CustomerPresenter implements Serializable{
    @Inject
    CustomerSupplierService service;
    
    private CustomerSupplier customer;
    private Plant plant;
    private Referee referee;
    private Long id;
    
    public void removePlant(Plant plantBeingRemoved) {
        plantBeingRemoved.setCustomerSupplier(null);
        customer.getPlants().remove(plantBeingRemoved);
    }
    
    public String addPlant() {
        if (!customer.getPlants().contains(plant)) {
            plant.setCustomerSupplier(customer);
            customer.getPlants().add(plant);
        }
        return "customer?faces-redirect=true";
    }
    
    public String detailPlant(Plant plantBeingOpened) {
        if (plantBeingOpened == null)
            plant = new Plant();
        else
            plant = plantBeingOpened;
        return "plant?faces-redirect=true";
    }
    
    public void removeReferee(Referee refereeBeingRemoved) {
        refereeBeingRemoved.setCustomerSupplier(null);
        customer.getReferees().remove(refereeBeingRemoved);
    }
    
    public String addReferee() {
        if (!customer.getReferees().contains(referee)) {
            referee.setCustomerSupplier(customer);
            customer.getReferees().add(referee);
        }
        return "customer?faces-redirect=true";
    }
    
    public String detailReferee(Referee refereeBeingOpened) {
        if (refereeBeingOpened == null)
            referee = new Referee();
        else
            referee = refereeBeingOpened;
        return "referee?faces-redirect=true";
    }
    
    public String saveCustomer() {
        service.saveCustomerSupplier(customer);
        
        return "exitFlow";
    }
    
    public void detailCustomer() {
        if (id != null) {
            if (id == 0)
                customer = new CustomerSupplier();
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
