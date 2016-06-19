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
package com.dmainardi.pipeer.business.base.boundary;

import com.dmainardi.pipeer.business.base.entity.DeliveryMethod;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Stateless
public class DeliveryMethodService {
    @PersistenceContext
    EntityManager em;
    
    public DeliveryMethod saveDeliveryMethod(DeliveryMethod deliveryMethod) {
        if (deliveryMethod.getId() == null)
            em.persist(deliveryMethod);
        else
            return em.merge(deliveryMethod);
        
        return null;
    }
    
    public DeliveryMethod readDeliveryMethod(Long id) {
        return em.find(DeliveryMethod.class, id);
    }
    
    public void deleteDeliveryMethod(Long id) {
        em.remove(readDeliveryMethod(id));
    }

    public List<DeliveryMethod> listDeliveryMethods() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DeliveryMethod> query = cb.createQuery(DeliveryMethod.class);
        Root<DeliveryMethod> root = query.from(DeliveryMethod.class);
        CriteriaQuery<DeliveryMethod> select = query.select(root).distinct(true);

        return em.createQuery(select).getResultList();
    }
}
