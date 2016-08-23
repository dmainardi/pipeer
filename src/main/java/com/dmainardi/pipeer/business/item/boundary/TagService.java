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

import com.dmainardi.pipeer.business.item.entity.Tag;
import java.io.Serializable;
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
public class TagService implements Serializable {
    @PersistenceContext
    EntityManager em;
    
    public Tag saveTag(Tag tag) {
        if (tag.getId() == null)
            em.persist(tag);
        else
            return em.merge(tag);
        
        return null;
    }
    
    public Tag readTag(Long id) {
        return em.find(Tag.class, id);
    }
    
    public void deleteTag(Long id) {
        em.remove(readTag(id));
    }

    public List<Tag> listTags() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tag> query = cb.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        CriteriaQuery<Tag> select = query.select(root).distinct(true);

        return em.createQuery(select).getResultList();
    }
}
