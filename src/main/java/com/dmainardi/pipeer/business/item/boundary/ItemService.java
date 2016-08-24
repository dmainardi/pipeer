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
package com.dmainardi.pipeer.business.item.boundary;

import com.dmainardi.pipeer.business.item.entity.Item;
import com.dmainardi.pipeer.business.item.entity.Tag;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
public class ItemService {

    @PersistenceContext
    EntityManager em;
    
    @Inject
    TagService tagService;

    public Item saveItem(Item item) {
        //convert tags from csv to list (creating new ones)
        item.getTags().clear();
        if (item.getTagsStrCSV() != null ) {
            for (String tagToBeAdded : item.getTagsStrCSV().split(",")) {
                Tag tag = tagService.findTag(tagToBeAdded.trim(), false);
                if (tag == null)
                    tag = new Tag(tagToBeAdded.trim());
                item.getTags().add(tag);
            }
        }
        
        if (item.getId() == null)
            em.persist(item);
        else
            return em.merge(item);

        return null;
    }

    public Item readItem(Long id) {
        return em.find(Item.class, id);
    }

    public void deleteItem(Long id) {
        em.remove(readItem(id));
    }
    
    public List<Item> listItems(int first, int pageSize, Map<String, Object> filters, String sortField, Boolean isAscending) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> root = query.from(Item.class);
        CriteriaQuery<Item> select = query.select(root).distinct(true);

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
        
        TypedQuery<Item> typedQuery = em.createQuery(select);
        typedQuery.setMaxResults(pageSize);
        typedQuery.setFirstResult(first);

        return typedQuery.getResultList();
    }
    
    public Long getItemsCount(Map<String, Object> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Item> root = query.from(Item.class);
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
