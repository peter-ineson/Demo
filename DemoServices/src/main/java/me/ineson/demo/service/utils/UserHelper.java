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
package me.ineson.demo.service.utils;

import me.ineson.demo.service.UserRole;
import me.ineson.demo.service.db.domain.User;

/**
 * Set of utilities for converting between DTO and DB object for the user data.
 * @author peter
 * @version 1.0
 */
public class UserHelper {

    /**
     * Stop instantiation.
     */
    private UserHelper() {
    }


    /**
     * Moves data from a {@linkplain User} entity to {@linkplain me.ineson.demo.service.User}
     * DTO. This function is null pointer safe, it just moves what data it can and does perform and
     * no sort of validation.
     * 
     * @param dbUser DB Entity.
     * @return User DTO.
     */
    public static me.ineson.demo.service.User convert(User dbUser) {
        me.ineson.demo.service.User userDto = null;
        if (dbUser != null) {
            userDto = new me.ineson.demo.service.User();
            userDto.setUsername(dbUser.getUsername());
            if( dbUser.getRole() != null) {
                userDto.setRole(UserRole.fromValue(dbUser.getRole().name()));
            }
            userDto.setName(dbUser.getName());
        }

        return userDto;
    }
}
