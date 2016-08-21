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
package com.dmainardi.pipeer.presentation.base;

import com.dmainardi.pipeer.business.base.boundary.BankService;
import com.dmainardi.pipeer.business.base.entity.Bank;
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
public class BankPresenter implements Serializable{
    @Inject
    BankService service;
    
    private Bank bank;
    private Long id;
    
    public List<Bank> listBanks() {
        return service.listBanks();
    }
    
    public String saveBank() {
        try {
            service.saveBank(bank);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
            return null;
        }
        
        return "/secured/base/banks?faces-redirect=true";
    }
    
    public void detailBank() {
        if (id != null) {
            if (id == 0)
                bank = new Bank();
            else
                bank = service.readBank(id);
            id = null;
        }
    }
    
    public void deleteBank(Bank bank) {
        service.deleteBank(bank.getId());
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
