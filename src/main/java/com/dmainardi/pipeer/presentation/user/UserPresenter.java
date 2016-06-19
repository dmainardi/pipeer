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
package com.dmainardi.pipeer.presentation.user;

import com.dmainardi.pipeer.business.user.boundary.UserService;
import com.dmainardi.pipeer.business.user.entity.GroupApp;
import com.dmainardi.pipeer.business.user.entity.UserApp;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Named
@ViewScoped
public class UserPresenter implements Serializable {
    @Inject
    UserService userService;
    
    private UserApp user;
    
    private DualListModel<GroupApp> groups = new DualListModel<>();
    
    public String saveUserApp() {
        user.getGroups().clear();
        user.getGroups().addAll(groups.getTarget());
        userService.saveUserApp(user);
        
        return "/secured/manageUser/users?faces-redirect=true";
    }

    public UserApp getUserApp() {
        return user;
    }

    public void setUserApp(UserApp userApp) {
        this.user = userApp;
    }
    
    public String addGroup(GroupApp group) {
        this.user.getGroups().add(group);
        return "/secured/manageUser/user?faces-redirect=true";
    }
    
    public void removeGroup(GroupApp group) {
        this.user.getGroups().remove(group);
    }
    
    public List<GroupApp> avaibleGroups(UserApp user) {
        return userService.avaibleGroups(user);
    }

    public DualListModel<GroupApp> getGroups() {
        groups.setSource(userService.avaibleGroups(user));
        groups.setTarget(user.getGroups());
        return groups;
    }

    public void setGroups(DualListModel<GroupApp> groups) {
        this.groups = groups;
    }
    
}
