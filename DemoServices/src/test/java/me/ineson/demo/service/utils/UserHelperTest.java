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

import me.ineson.demo.service.db.domain.User;
import me.ineson.demo.service.db.domain.UserRole;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author peter
 *
 */
public class UserHelperTest {

    /**
     * Test method for .
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @Test(expected = IllegalAccessException.class)
    public void testCannotInstantiate() throws InstantiationException, IllegalAccessException {
        UserHelper.class.newInstance();
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.UserHelper#convert(me.ineson.demo.service.db.domain.User)}.
     */
    @Test
    public void testConvertNull() {
        Assert.assertNull(UserHelper.convert((User) null));
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.UserHelper#convert(me.ineson.demo.service.db.domain.User)}.
     */
    @Test
    public void testEmptyConvert() {
        User entity = new User();
        me.ineson.demo.service.User dto = UserHelper.convert(entity);
        Assert.assertNotNull(dto);
        Assert.assertNull(dto.getUsername());
        Assert.assertNull(dto.getPassword());
        Assert.assertNull(dto.getRole());
        Assert.assertNull(dto.getName());
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.UserHelper#convert(me.ineson.demo.service.db.domain.User)}.
     */
    @Test
    public void testConvert() {
        User entity = new User();
        entity.setUsername("un");
        entity.setPassword("p1");
        entity.setName( "t1");
        entity.setRole( UserRole.Guest);

        me.ineson.demo.service.User dto = UserHelper.convert(entity);
        Assert.assertEquals( "username check", "un", dto.getUsername());
        Assert.assertNull( "password check", dto.getPassword());
        Assert.assertEquals( "role check", me.ineson.demo.service.UserRole.GUEST, dto.getRole());
        Assert.assertEquals( "name check", "t1", dto.getName());
    }

}
