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
package me.ineson.demo.service.rest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRestService<T> {

    private static final Logger log = LoggerFactory.getLogger(AbstractRestService.class);

    protected final static Client CLIENT = ClientBuilder.newClient();

    private final Class<T> dtoClass;

    private final MediaType mediaType;
    

    private GenericType<?> listType;

    protected AbstractRestService(Class<T> dtoClass, GenericType<?> listType) {
        this.dtoClass = dtoClass;
        this.listType = listType;
        this.mediaType = MediaType.APPLICATION_XML_TYPE;
    }
    
    protected AbstractRestService(Class<T> dtoClass, GenericType<?> listType, MediaType mediaType) {
        this.dtoClass = dtoClass;
        this.listType = listType;
        this.mediaType = mediaType;
    }
    
    protected T findOne( String url) {
        log.debug("Calling url {}", url);
        WebTarget target = CLIENT.target( url);
        Response response = target.request(mediaType).get();
        
        T result;
        if( response.getStatus() == Status.OK.getStatusCode()) {
            result = response.readEntity( dtoClass);
        } else if( response.getStatus() == Status.NO_CONTENT.getStatusCode()) {
            // 204 - response
            result = null;
        } else {
            log.error( "Unknown response findOne lookup url {}, response {}\n{}",url, response, response.readEntity(String.class));
            throw new IllegalStateException( "Service returned an error " + response + ", url " + url );
        }
        
        return result;
    }

    @SuppressWarnings("unchecked")
    protected List<T> findMany(String url, Map<String, Object> criteria, Collection<String> orderBy) {
        log.debug("Calling url {}", url);
        WebTarget target = CLIENT.target(url);

        if (MapUtils.isNotEmpty(criteria)) {
            StringBuilder criteriaStr = new StringBuilder();
            for (Map.Entry<String, Object> singleCriteria : criteria.entrySet()) {
                if (criteriaStr.length() > 0) {
                    criteriaStr.append(",");
                }
                criteriaStr.append(singleCriteria.getKey()).append("=").append(singleCriteria.getValue());
            }
            target = target.queryParam("where", criteriaStr.toString());
        }

        if (CollectionUtils.isNotEmpty(orderBy)) {
            target = target.queryParam("orderBy", StringUtils.join(orderBy, ","));
        }

        Response response = target.request(mediaType).get();

        List<T> result;
        if (response.getStatus() == Status.OK.getStatusCode()) {
            result = (List<T>) response.readEntity(listType);
        } else if (response.getStatus() == Status.NO_CONTENT.getStatusCode()) {
            // 204 - response
            result = null;
        } else {
            log.error("Unknown response findMany lookup url {}, response {}\n{}", url, response, response.readEntity(String.class));
            throw new IllegalStateException("Service returned an error");
        }

        return result;
    }    
    

}
