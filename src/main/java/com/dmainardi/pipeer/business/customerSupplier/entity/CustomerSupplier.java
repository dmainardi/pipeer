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
package com.dmainardi.pipeer.business.customerSupplier.entity;

import com.dmainardi.pipeer.business.base.entity.Bank;
import com.dmainardi.pipeer.business.base.entity.DeliveryMethod;
import com.dmainardi.pipeer.business.base.entity.PaymentMethod;
import com.dmainardi.pipeer.business.entity.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Entity
public class CustomerSupplier extends BaseEntity<Long>{
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(nullable = false)
    @NotNull
    private String businessName;
    
    @Column(nullable = false)
    @NotNull
    private String name;
    
    private Integer code;
    
    private String vatRegistrationNumber;
    private String taxCode;
    
    @Column(nullable = false)
    @NotNull
    private Boolean isCustomer;
    
    @Column(nullable = false)
    @NotNull
    private Boolean isSupplier;
    
    @ManyToOne
    private Bank bank;
    
    @ManyToOne
    private DeliveryMethod deliveryMethod;  //only for suppliers
    
    @ManyToOne
    private PaymentMethod paymentMethod;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerSupplier", orphanRemoval = true)
    private List<Plant> plants;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerSupplier", orphanRemoval = true)
    private List<Referee> referees;
    
    private String notes;
    
    @Version
    private int version;

    public CustomerSupplier() {
        isCustomer = false;
        isSupplier = false;
        plants = new ArrayList<>();
        referees = new ArrayList<>();
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVatRegistrationNumber() {
        return vatRegistrationNumber;
    }

    public void setVatRegistrationNumber(String vatRegistrationNumber) {
        this.vatRegistrationNumber = vatRegistrationNumber;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public Boolean getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(Boolean isCustomer) {
        this.isCustomer = isCustomer;
    }

    public Boolean getIsSupplier() {
        return isSupplier;
    }

    public void setIsSupplier(Boolean isSupplier) {
        this.isSupplier = isSupplier;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public Long getId() {
        return id;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public List<Referee> getReferees() {
        return referees;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
}
