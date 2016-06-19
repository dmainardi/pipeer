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
import com.dmainardi.pipeer.business.user.entity.UserApp;
import com.dmainardi.pipeer.presentation.Authenticator;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Named
@ViewScoped
public class ListUserPresenter implements Serializable{
    private List<UserApp> users;
    private List<UserApp> selectedUsers;
    
    @Inject
    UserService userService;
    
    @Inject
    Authenticator authenticator;
    
    @PostConstruct
    public void init() {
        users = userService.listUserApps();
    }
    
    public void deleteUsers() {
        if (selectedUsers != null && !selectedUsers.isEmpty()) {
            for (UserApp userTemp : selectedUsers)
                if (userTemp.getUserName().equals(authenticator.getLoggedUser().getUserName()))
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error in deleting user", "You cannot delete yourself"));
                else
                    userService.deleteUserApp(userTemp);
            users = userService.listUserApps();
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Missing selection", "Select a user before deleting"));
    }

    public List<UserApp> getUsers() {
        return users;
    }

    public List<UserApp> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<UserApp> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }
}
