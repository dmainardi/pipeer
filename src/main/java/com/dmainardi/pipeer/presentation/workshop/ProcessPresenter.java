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
package com.dmainardi.pipeer.presentation.workshop;

import com.dmainardi.pipeer.business.workshop.boundary.ProcessService;
import com.dmainardi.pipeer.business.workshop.entity.Process;
import com.dmainardi.pipeer.presentation.ExceptionUtility;
import java.io.Serializable;
import java.util.ArrayList;
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
public class ProcessPresenter implements Serializable {
    @Inject
    ProcessService service;
    
    private Process process;
    private Long id;
    
    public List<String> listProcessesStr(String query) {
        List<String> result = new ArrayList<>();
        
        List<Process> processes = service.listProcesses(query);
        if (processes != null && !processes.isEmpty())
            for (Process processTemp : processes)
                result.add(processTemp.getName());
        
        return result;
    }
    
    public List<Process> listProcesses() {
        return service.listProcesses(null);
    }
    
    public String saveProcess() {
        try {
            service.saveProcess(process);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
            return null;
        }
        
        return "/secured/workshop/processes?faces-redirect=true";
    }
    
    public void detailProcess() {
        if (id != null) {
            if (id == 0)
                process = new Process();
            else
                process = service.readProcess(id);
            id = null;
        }
    }
    
    public void deleteProcess(Process process) {
        try {
            service.deleteProcess(process.getId());
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
        }
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

