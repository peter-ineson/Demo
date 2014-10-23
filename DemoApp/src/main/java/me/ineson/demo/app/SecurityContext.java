/*
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
package me.ineson.demo.app;

import lombok.Getter;
import lombok.Setter;
import me.ineson.demo.service.User;
import me.ineson.demo.service.UserRole;

/**
 * @author peter
 *
 */
public class SecurityContext {
    
    public static final String ATTRIBUTE_NAME = "security"; 
    
    @Getter @Setter private User user;
    
    public SecurityContext() {
        super();
    }

    public SecurityContext(User user) {
        super();
        this.user = user;
    }

    /**
     * Returns true if the user has a role of Guest or no user object.
     * @return true if a guest user.
     */
    public boolean isGuest() {
        return !isAdmin();
    }

    /**
     * Returns true if the user has a role of Admin.
     * @return true if an admin user.
     */
    public boolean isAdmin() {
        return (user != null && user.getRole() == UserRole.ADMIN);
    }

    public String getUsername() {
        return (user == null) ? null : user.getUsername();
    }
}
