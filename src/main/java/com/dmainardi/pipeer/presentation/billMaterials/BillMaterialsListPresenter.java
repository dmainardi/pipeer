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
import com.dmainardi.pipeer.presentation.ExceptionUtility;
import java.io.Serializable;
import java.util.List;
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
public class BillMaterialsListPresenter implements Serializable{
    @Inject
    BillMaterialsService billMaterialsService;
    
    private BillMaterialsLazyDataModel lazyBillMaterials;
    private List<BillMaterials> selectedBillsMaterials;
    
    @PostConstruct
    public void init() {
        lazyBillMaterials = new BillMaterialsLazyDataModel(billMaterialsService);
    }
    
    public void deleteBillsMaterials() {
        if (selectedBillsMaterials != null && !selectedBillsMaterials.isEmpty()) {
            for (BillMaterials billMaterialsTemp : selectedBillsMaterials) {
                try {
                    billMaterialsService.deleteBillMaterials(billMaterialsTemp.getId());
                } catch (EJBException e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
                }
            }
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Missing selection", "Select a row before deleting"));
    }
    
    public void createRevision() {
        if (selectedBillsMaterials != null && !selectedBillsMaterials.isEmpty()) {
            for (BillMaterials billMaterialsTemp : selectedBillsMaterials) {
                try {
                    billMaterialsService.duplicateBillMaterials(billMaterialsTemp.getId(), true);
                } catch (EJBException e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
                }
            }
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Missing selection", "Select a row before creating a new revision"));
    }
    
    public void duplicate() {
        if (selectedBillsMaterials != null && !selectedBillsMaterials.isEmpty()) {
            for (BillMaterials billMaterialsTemp : selectedBillsMaterials) {
                try {
                    billMaterialsService.duplicateBillMaterials(billMaterialsTemp.getId(), false);
                } catch (EJBException e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
                }
            }
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Missing selection", "Select a row before duplicating"));
    }

    public BillMaterialsLazyDataModel getLazyBillMaterials() {
        return lazyBillMaterials;
    }

    public List<BillMaterials> getSelectedBillsMaterials() {
        return selectedBillsMaterials;
    }

    public void setSelectedBillsMaterials(List<BillMaterials> selectedBillsMaterials) {
        this.selectedBillsMaterials = selectedBillsMaterials;
    }
}
