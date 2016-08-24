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

import com.dmainardi.pipeer.business.base.boundary.UnitMeasureService;
import com.dmainardi.pipeer.business.base.entity.UnitMeasure;
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
public class UnitMeasurePresenter implements Serializable{
    @Inject
    UnitMeasureService service;
    
    private UnitMeasure unitMeasure;
    private Long id;
    
    public List<UnitMeasure> listUnitMeasures() {
        return service.listUnitMeasures();
    }
    
    public String saveUnitMeasure() {
        try {
            service.saveUnitMeasure(unitMeasure);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
            return null;
        }
        
        return "/secured/base/unitMeasures?faces-redirect=true";
    }
    
    public void detailUnitMeasure() {
        if (id != null) {
            if (id == 0)
                unitMeasure = new UnitMeasure();
            else
                unitMeasure = service.readUnitMeasure(id);
            id = null;
        }
    }
    
    public void deleteUnitMeasure(UnitMeasure unitMeasure) {
        try {
            service.deleteUnitMeasure(unitMeasure.getId());
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
        }
    }

    public UnitMeasure getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(UnitMeasure unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
