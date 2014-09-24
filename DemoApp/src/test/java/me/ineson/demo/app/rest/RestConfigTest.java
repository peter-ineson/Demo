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
package me.ineson.demo.app.rest;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author peter
 *
 */
public class RestConfigTest {

    /**
     * Test method for {@link me.ineson.demo.app.rest.RestConfig#RestConfig()}.
     */
    @Test
    public void testRestConfig() {
        new RestConfig();
    }

    @Test
    public void testsupportedMediaTypeNull() {
        Assert.assertFalse("null check", RestConfig.supportedMediaType( null));
    }
    
    @Test
    public void testsupportedMediaTypeEmpty() {
        Assert.assertFalse(RestConfig.supportedMediaType( StringUtils.EMPTY));
    }

    @Test
    public void testsupportedMediaTypeNotSupported() {
        Assert.assertFalse(RestConfig.supportedMediaType( MediaType.TEXT_HTML));
    }

    @Test
    public void testsupportedMediaTypeSupported() {
        Assert.assertTrue(RestConfig.supportedMediaType( MediaType.APPLICATION_JSON));
        Assert.assertTrue(RestConfig.supportedMediaType( MediaType.APPLICATION_XML));
        Assert.assertTrue(RestConfig.supportedMediaType( MediaType.TEXT_XML));
        Assert.assertTrue(RestConfig.supportedMediaType( MediaType.APPLICATION_OCTET_STREAM + ";" + MediaType.APPLICATION_JSON));
    }

}
