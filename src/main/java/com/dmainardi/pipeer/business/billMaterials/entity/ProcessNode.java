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
package com.dmainardi.pipeer.business.billMaterials.entity;

import com.dmainardi.pipeer.business.workshop.entity.Process;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Entity
@DiscriminatorValue(value = "prc")
public class ProcessNode extends Node {
    
    @NotNull
    @ManyToOne(optional = false)
    private Process process;

    @Override
    public String getDescription() {
        if (process != null)
            return process.getName();
        else
            return "Select a process";
    }

    @Override
    public String getUnitMeasure() {
        return "h";
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    @Override
    Node duplicate() {
        ProcessNode result = new ProcessNode();
        
        duplicateCommonFields(result);
        result.setProcess(process);
        
        return result;
    }
    
}
