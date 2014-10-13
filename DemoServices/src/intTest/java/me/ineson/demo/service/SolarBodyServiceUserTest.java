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
package me.ineson.demo.service;

import me.ineson.testing.utils.GradleConfig;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolarBodyServiceUserTest {

    private static final Logger log = LoggerFactory.getLogger( SolarBodyServiceUserTest.class);

    private final static String ENDPOINT_URL = GradleConfig.getProperty("DemoService.intTest.solarBodyServiceEndpointUrl");

    private SolarBodyEndpointClient client = new SolarBodyEndpointClient();

    @Test
    public void testgetGuestUser() {

        User user = client.getGuestUser(ENDPOINT_URL);
        log.info("Guest user: {}", user);

        Assert.assertNotNull("Guest user", user);
        Assert.assertEquals("username", me.ineson.demo.service.db.domain.User.GUEST_USERNAME, user.getUsername());
        Assert.assertNull("password", user.getPassword());
        Assert.assertEquals("role", UserRole.GUEST, user.getRole());
        Assert.assertTrue("name not empty", StringUtils.isNotEmpty(user.getName()));
    }

    @Test
    public void testLoginInvalidParameters() {
        User user = client.login(ENDPOINT_URL, null, null);
        Assert.assertNull("all null", user);

        user = client.login(ENDPOINT_URL, "blah", null);
        Assert.assertNull("null password", user);

        user = client.login(ENDPOINT_URL, null, "blah");
        Assert.assertNull("null username", user);
    }

    @Test
    public void testLoginInvalidPassaword() {
        User user = client.login(ENDPOINT_URL, "admin", "blah");
        Assert.assertNull("invalid password", user);
    }
    
    @Test
    public void testLoginSuccess() {
        User user = client.login(ENDPOINT_URL, "admin", "admin");
        log.info("Admin user: {}", user);
        Assert.assertNotNull("admin user", user);
        Assert.assertEquals("username", "admin", user.getUsername());
        Assert.assertNull("password", user.getPassword());
        Assert.assertEquals("role", UserRole.ADMIN, user.getRole());
        Assert.assertTrue("name not empty", StringUtils.isNotEmpty(user.getName()));
    }
}
