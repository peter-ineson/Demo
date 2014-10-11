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

import static org.junit.Assert.*;
import me.ineson.demo.service.User;
import me.ineson.demo.service.UserRole;

import org.junit.Test;

/**
 * @author peter
 *
 */
public class UserContextTest {

    /**
     * Test method for {@link me.ineson.demo.app.UserContext#setUser(me.ineson.demo.service.User)}.
     */
    @Test
    public void testSetGetUser() {
        UserContext userContext = new UserContext();

        assertNull(userContext.getUser());

        User user = new User();
        userContext.setUser(user);
        assertEquals(user, userContext.getUser());

        userContext.setUser(null);
        assertNull(userContext.getUser());
    }

    /**
     * Test method for {@link me.ineson.demo.app.UserContext#isGuest()}.
     */
    @Test
    public void testNullUser() {
        UserContext userContext = new UserContext();
        assertTrue(userContext.isGuest());
        assertFalse(userContext.isAdmin());
    }

    /**
     * Test method for {@link me.ineson.demo.app.UserContext#isGuest()}.
     */
    @Test
    public void testNullUserRole() {
        UserContext userContext = new UserContext();
        User user = new User();
        userContext.setUser(user);

        assertTrue(userContext.isGuest());
        assertFalse(userContext.isAdmin());
    }
    
    /**
     * Test method for {@link me.ineson.demo.app.UserContext#isAdmin()}.
     */
    @Test
    public void testUserRoleGuest() {
        UserContext userContext = new UserContext();
        User user = new User();
        user.setRole(UserRole.GUEST);
        userContext.setUser(user);

        assertTrue(userContext.isGuest());
        assertFalse(userContext.isAdmin());
    }

    /**
     * Test method for {@link me.ineson.demo.app.UserContext#isAdmin()}.
     */
    @Test
    public void testUserRoleAdmin() {
        UserContext userContext = new UserContext();
        User user = new User();
        user.setRole(UserRole.ADMIN);
        userContext.setUser(user);

        assertFalse(userContext.isGuest());
        assertTrue(userContext.isAdmin());
    }
    
}
