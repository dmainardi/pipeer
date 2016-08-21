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

import com.dmainardi.pipeer.business.base.boundary.PaymentMethodService;
import com.dmainardi.pipeer.business.base.entity.PaymentMethod;
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
public class PaymentMethodPresenter implements Serializable{
    @Inject
    PaymentMethodService service;
    
    private PaymentMethod paymentMethod;
    private Long id;
    
    public List<PaymentMethod> listPaymentMethods() {
        return service.listPaymentMethods();
    }
    
    public String savePaymentMethod() {
        try {
            service.savePaymentMethod(paymentMethod);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
            return null;
        }
        
        return "/secured/base/paymentMethods?faces-redirect=true";
    }
    
    public void detailPaymentMethod() {
        if (id != null) {
            if (id == 0)
                paymentMethod = new PaymentMethod();
            else
                paymentMethod = service.readPaymentMethod(id);
            id = null;
        }
    }
    
    public void deletePaymentMethod(PaymentMethod paymentMethod) {
        service.deletePaymentMethod(paymentMethod.getId());
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
