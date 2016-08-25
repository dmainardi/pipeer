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
package com.dmainardi.pipeer.business.billMaterials.boundary;

import com.dmainardi.pipeer.business.billMaterials.entity.BillMaterials;
import com.dmainardi.pipeer.business.billMaterials.entity.BillMaterials_;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
public class BillMaterialsService {

    @PersistenceContext
    EntityManager em;

    public BillMaterials saveBillMaterials(BillMaterials billMaterials) {
        if (billMaterials.getId() == null) {
            billMaterials.setNumber(getNextNumber());
            em.persist(billMaterials);
        }
        else
            return em.merge(billMaterials);

        return null;
    }
    
    private Integer getNextNumber() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
        Root<BillMaterials> myBill = query.from(BillMaterials.class);
        query.select(cb.greatest(myBill.get(BillMaterials_.number)));
        
        List<Predicate> conditions = new ArrayList<>();
        
        GregorianCalendar dateStart = new GregorianCalendar(new GregorianCalendar().get(Calendar.YEAR), 0, 01);
        GregorianCalendar dateEnd = new GregorianCalendar(new GregorianCalendar().get(Calendar.YEAR), 11, 31);
        
        conditions.add(cb.between(myBill.get(BillMaterials_.creationDate), dateStart.getTime(), dateEnd.getTime()));

        if (!conditions.isEmpty()) {
            query.where(conditions.toArray(new Predicate[conditions.size()]));
        }
        
        Integer result;
        try {
            result = em.createQuery(query).getSingleResult();
            if (result != null)
                result++;
            else
                result = 1;
        } catch (NoResultException e) {
            result = 1;
        }
        
	return result;
    }

    public BillMaterials readBillMaterials(Long id) {
        return em.find(BillMaterials.class, id);
    }

    public void deleteBillMaterials(Long id) {
        em.remove(readBillMaterials(id));
    }
    
    public List<BillMaterials> listBillsMaterials(int first, int pageSize, Map<String, Object> filters, String sortField, Boolean isAscending) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BillMaterials> query = cb.createQuery(BillMaterials.class);
        Root<BillMaterials> root = query.from(BillMaterials.class);
        CriteriaQuery<BillMaterials> select = query.select(root).distinct(true);

        List<Predicate> conditions = new ArrayList<>();        
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
        
        TypedQuery<BillMaterials> typedQuery = em.createQuery(select);
        typedQuery.setMaxResults(pageSize);
        typedQuery.setFirstResult(first);

        return typedQuery.getResultList();
    }
    
    public Long getBillsMaterialsCount(Map<String, Object> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<BillMaterials> root = query.from(BillMaterials.class);
        CriteriaQuery<Long> select = query.select(cb.count(root));

        List<Predicate> conditions = new ArrayList<>();
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
