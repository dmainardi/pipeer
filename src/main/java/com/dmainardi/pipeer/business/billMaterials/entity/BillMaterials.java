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

import com.dmainardi.pipeer.business.customerSupplier.entity.Plant;
import com.dmainardi.pipeer.business.entity.BaseEntity;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Entity
public class BillMaterials extends BaseEntity<Long>{
    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    @DecimalMin(value = "0")
    @Column(nullable = false)
    private Integer number;
    
    @NotNull
    @DecimalMin(value = "0")
    @Column(nullable = false)
    private Integer revision;
    
    @NotNull
    @Column(nullable = false)
    private String name;
    
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    
    @NotNull
    @ManyToOne(optional = false)
    private Plant plant;
    
    private String notes;
    
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Node root;
    
    @Version
    private int version;
    
    public BillMaterials duplicate(boolean isRevision) {
        BillMaterials result = new BillMaterials();
        
        result.setCreationDate(new Date());
        result.setName(name);
        result.setNotes(notes);
        result.setPlant(plant);
        result.setRoot(root.duplicate());
        result.getRoot().setBillMaterials(result);
        if (isRevision) {
            result.setNumber(number);
            result.setRevision(-1);
        }
        
        return result;
    }

    public BillMaterials() {
        number = 0;
        revision = 0;
        creationDate = new Date();
        root = new GroupNode();
        ((GroupNode)root).setDescription("Root node");
        root.setBillMaterials(this);
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
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

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
    
}
