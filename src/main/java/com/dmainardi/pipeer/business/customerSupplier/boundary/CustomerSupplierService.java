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
package com.dmainardi.pipeer.business.customerSupplier.boundary;

import com.dmainardi.pipeer.business.customerSupplier.entity.CustomerSupplier;
import com.dmainardi.pipeer.business.customerSupplier.entity.CustomerSupplier_;
import com.dmainardi.pipeer.business.customerSupplier.entity.Plant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Stateless
public class CustomerSupplierService {

    @PersistenceContext
    EntityManager em;

    public CustomerSupplier saveCustomerSupplier(CustomerSupplier customerSupplier) {
        int headOffice = 0;
        for (Plant plant : customerSupplier.getPlants())
            if (plant.getIsHeadOffice())
                headOffice++;
        if (headOffice != 1)
            throw new IllegalArgumentException("Customer/supplier must have one head office");
            
        if (customerSupplier.getId() == null)
            em.persist(customerSupplier);
        else
            return em.merge(customerSupplier);

        return null;
    }

    public CustomerSupplier readCustomerSupplier(Long id) {
        return em.find(CustomerSupplier.class, id);
    }

    public void deleteCustomerSupplier(Long id) {
        em.remove(readCustomerSupplier(id));
    }
    
    public List<CustomerSupplier> listCustomerSuppliers(Boolean isCustomer, Boolean isSupplier, int first, int pageSize, Map<String, Object> filters, String sortField, Boolean isAscending) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CustomerSupplier> query = cb.createQuery(CustomerSupplier.class);
        Root<CustomerSupplier> root = query.from(CustomerSupplier.class);
        CriteriaQuery<CustomerSupplier> select = query.select(root).distinct(true);

        List<Predicate> conditions = new ArrayList<>();
        //customer
        if (isCustomer != null) {
            conditions.add(cb.equal(root.get(CustomerSupplier_.isCustomer), isCustomer));
        }
        //supplier
        if (isSupplier != null) {
            conditions.add(cb.equal(root.get(CustomerSupplier_.isSupplier), isSupplier));
        }
        
        if (filters != null) {
            for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                String filterProperty = it.next();
                conditions.add(cb.like(cb.lower(root.get(filterProperty)), "%" + String.valueOf(filters.get(filterProperty)).toLowerCase() + "%"));
            }
        }

        if (!conditions.isEmpty()) {
            query.where(conditions.toArray(new Predicate[conditions.size()]));
        }
        
        if (isAscending != null && sortField != null && !sortField.isEmpty()) {
            if (isAscending)
                query.orderBy(cb.asc(root.get(sortField)));
            else
                query.orderBy(cb.desc(root.get(sortField)));
        }
        
        TypedQuery<CustomerSupplier> typedQuery = em.createQuery(select);
        typedQuery.setMaxResults(pageSize);
        typedQuery.setFirstResult(first);

        return typedQuery.getResultList();
    }
    
    public Long getCustomerSuppliersCount(Boolean isCustomer, Boolean isSupplier, Map<String, Object> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<CustomerSupplier> root = query.from(CustomerSupplier.class);
        CriteriaQuery<Long> select = query.select(cb.count(root));

        List<Predicate> conditions = new ArrayList<>();
        //customer
        if (isCustomer != null) {
            conditions.add(cb.equal(root.get(CustomerSupplier_.isCustomer), isCustomer));
        }
        //supplier
        if (isSupplier != null) {
            conditions.add(cb.equal(root.get(CustomerSupplier_.isSupplier), isSupplier));
        }
        
        if (filters != null) {
            for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                String filterProperty = it.next();
                conditions.add(cb.like(cb.lower(root.get(filterProperty)), "%" + String.valueOf(filters.get(filterProperty)).toLowerCase() + "%"));
            }
        }

        if (!conditions.isEmpty()) {
            query.where(conditions.toArray(new Predicate[conditions.size()]));
        }

        return em.createQuery(select).getSingleResult();
    }
}
