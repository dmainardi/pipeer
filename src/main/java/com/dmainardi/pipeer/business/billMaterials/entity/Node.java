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

import com.dmainardi.pipeer.business.entity.BaseEntity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Node extends BaseEntity<Long>{
    @Id
    private Long id;
    
    @Transient
    private final int scaleQty = 5;
    @Transient
    private final int scalePrice = 2;
    
    @ManyToOne
    private Node father;
    
    @OneToOne(mappedBy = "root")
    private BillMaterials billMaterials;
    
    @OneToMany(mappedBy = "father", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Node> children;
    
    @NotNull
    @DecimalMin(value = "0")
    @Column(nullable = false, scale = scaleQty)
    private BigDecimal qty;
    
    @NotNull
    @DecimalMin(value = "0")
    @Column(nullable = false, scale = scalePrice)
    private BigDecimal price;
    
    private String notes;
    
    @Version
    private int version;

    public Node() {
        qty = new BigDecimal(BigInteger.ONE);
        price = new BigDecimal(BigInteger.ZERO);
        children = new ArrayList<>();
    }
    
    abstract String getDescription();
    
    abstract String getUnitMeasure();
    
    public BigDecimal getTotal() {
        return qty.multiply(price);
    }

    public Node getFather() {
        return father;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public BillMaterials getBillMaterials() {
        return billMaterials;
    }

    public void setBillMaterials(BillMaterials billMaterials) {
        this.billMaterials = billMaterials;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public int getScaleQty() {
        return scaleQty;
    }

    public int getScalePrice() {
        return scalePrice;
    }
    
}
