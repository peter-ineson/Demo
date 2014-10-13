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

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * @author peter
 *
 */
public class Config {
    
    private final String application;

    private final AbstractConfiguration config;

    public static final String SERVICE_REST_URL = "serviceRestUrl";
    
    public static final String SERVICE_ENDPOINT_URL = "solarBodyServiceEndpointUrl";

    public static final String SOLAR_SYSTEM_CENTRE_ID = "solarSystem.centreSolarBodyId";

    /**
     * @param basePath
     * @param config
     */
    public Config(String application, AbstractConfiguration config) {
        super();
        Assert.hasText( application);
        Assert.notNull(config, "Commons configuration");
        this.application = application;
        this.config = config;
    }   

    /**
     * @param key
     * @return
     */
    public String getString( String key) {
        key = buildFullKey(key);
        return config.getString(key);
    }

    /**
     * @param key
     * @return
     */
    public String getStringManadtory( String key) {
        String value = getString(key);
        if( StringUtils.isBlank(value)) {
            throw new IllegalStateException( "Configuration error: a key of " + key + " has not returned a value.");
        }
        return value;
    }

    /**
     * @param key
     * @return
     */
    public Long getLong(String key) {
        key = buildFullKey(key);
        return config.getLong(key, null);
    }

    /**
     * @param key
     * @return
     */
    public Long getLongManadtory(String key) {
        Long value = getLong(key);
        if (value == null) {
            throw new IllegalStateException("Configuration error: a key of " + key + " has not returned a value.");
        }
        return value;
    }
    
    /**
     * @param key
     * @return
     */
    private String buildFullKey( String key) {
        Assert.hasText( key, "Key cannot be empty");
        return application + "." + key;
    }
}
