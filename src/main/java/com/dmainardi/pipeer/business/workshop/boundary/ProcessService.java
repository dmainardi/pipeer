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
package com.dmainardi.pipeer.business.workshop.boundary;

import com.dmainardi.pipeer.business.workshop.entity.Process;
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
public class ProcessService implements Serializable {
    @PersistenceContext
    EntityManager em;
    
    public Process saveProcess(Process process) {
        if (process.getId() == null)
            em.persist(process);
        else
            return em.merge(process);
        
        return null;
    }
    
    public Process readProcess(Long id) {
        return em.find(Process.class, id);
    }
    
    public void deleteProcess(Long id) {
        em.remove(readProcess(id));
    }

    public List<Process> listProcesses() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Process> query = cb.createQuery(Process.class);
        Root<Process> root = query.from(Process.class);
        CriteriaQuery<Process> select = query.select(root).distinct(true);

        return em.createQuery(select).getResultList();
    }
}
