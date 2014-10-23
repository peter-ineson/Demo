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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test case for {@linkplain Config}.
 * 
 * @author peter
 */
public class ConfigTest {

    private static final Long LONG_1 = 1L;

    private static final Long LONG_2 = 2L;

    /**
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConfigConstructorAppNull() {
        new Config(null, null);
    }

    /**
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConfigConstructorConfigNull() {
        new Config("test", null);
    }

    @Test
    public void testGetString() {
        AbstractConfiguration mockAbstractConfiguration = mock(XMLConfiguration.class);
        when(mockAbstractConfiguration.getString("testApp.key1")).thenReturn("testCall");
        Config config = new Config("testApp", mockAbstractConfiguration);

        String value = config.getString("key1");

        verify(mockAbstractConfiguration, times(1)).getString("testApp.key1");
        verifyNoMoreInteractions(mockAbstractConfiguration);

        Assert.assertEquals("testCall", value);
    }

    @Test
    public void testGetStringManadtorySuccess() {
        AbstractConfiguration mockAbstractConfiguration = mock(XMLConfiguration.class);
        when(mockAbstractConfiguration.getString("testApp.key2")).thenReturn("testCall2");
        Config config = new Config("testApp", mockAbstractConfiguration);

        String value = config.getStringManadtory("key2");

        verify(mockAbstractConfiguration, times(1)).getString("testApp.key2");
        verifyNoMoreInteractions(mockAbstractConfiguration);

        Assert.assertEquals("testCall2", value);
    }

    /**
     * 
     */
    @Test
    public void testGetStringManadtoryFail() {
        AbstractConfiguration mockAbstractConfiguration = mock(XMLConfiguration.class);
        when(mockAbstractConfiguration.getString("testAppF.key3")).thenReturn(null);
        Config config = new Config("testAppF", mockAbstractConfiguration);

        try {
            config.getStringManadtory("key3");
            Assert.fail("Mandatory ");
        } catch(IllegalStateException e) {
            // Should fail
        }

        verify(mockAbstractConfiguration, times(1)).getString("testAppF.key3");
        verifyNoMoreInteractions(mockAbstractConfiguration);
    }

    @Test
    public void testGetLong() {
        AbstractConfiguration mockAbstractConfiguration = mock(XMLConfiguration.class);
        when(mockAbstractConfiguration.getLong("testApp.key1", null)).thenReturn(LONG_1);
        Config config = new Config("testApp", mockAbstractConfiguration);

        Long value = config.getLong("key1");

        verify(mockAbstractConfiguration, times(1)).getLong("testApp.key1",null);
        verifyNoMoreInteractions(mockAbstractConfiguration);

        Assert.assertEquals(LONG_1, value);
    }

    @Test
    public void testGetLongManadtorySuccess() {
        AbstractConfiguration mockAbstractConfiguration = mock(XMLConfiguration.class);
        when(mockAbstractConfiguration.getLong("testApp.key2", null)).thenReturn(LONG_2);
        Config config = new Config("testApp", mockAbstractConfiguration);

        Long value = config.getLongManadtory("key2");

        verify(mockAbstractConfiguration, times(1)).getLong("testApp.key2",null);
        verifyNoMoreInteractions(mockAbstractConfiguration);

        Assert.assertEquals(LONG_2, value);
    }

    /**
     * 
     */
    @Test
    public void testGetLongManadtoryFail() {
        AbstractConfiguration mockAbstractConfiguration = mock(XMLConfiguration.class);

        when(mockAbstractConfiguration.getLong("testAppF.key3", null)).thenReturn(null);
        Config config = new Config("testAppF", mockAbstractConfiguration);

        try {
            config.getLongManadtory("key3");
            Assert.fail("Mandatory ");
        } catch (IllegalStateException e) {
            // Should fail
        }

        verify(mockAbstractConfiguration, times(1)).getLong("testAppF.key3", null);
        verifyNoMoreInteractions(mockAbstractConfiguration);
    }

}
