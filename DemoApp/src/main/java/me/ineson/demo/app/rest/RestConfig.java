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

import me.ineson.demo.service.rest.ExceptionLogger;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * @author peter
 *
 */
public class RestConfig extends ResourceConfig {

    /**
     * Register JAX-RS application components. 
     */    
    public RestConfig() {
        register(ExceptionLogger.class);
        register(RequestContextFilter.class);
        register(JacksonFeature.class);
        register(SolarBodyEndpoint.class);
        register(SolarBodyLinkEndpoint.class);
    }
    
    /**
     * @param mediaTypes
     * @return
     */
    public static boolean supportedMediaType(String mediaTypes) {
        if (StringUtils.isBlank(mediaTypes)) {
            return false;
        }

        return (mediaTypes.contains(MediaType.APPLICATION_JSON)
                || mediaTypes.contains(MediaType.APPLICATION_XML)
                || mediaTypes.contains(MediaType.TEXT_XML));
    }
}
