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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import me.ineson.demo.service.db.domain.SolarBody;
import me.ineson.demo.service.db.repo.jpa.SolarBodyRepository;
import me.ineson.demo.service.utils.RestUtils;
import me.ineson.demo.service.utils.SolarBodyHelper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * @author peter
 *
 */
@Component
@Path("/solarBodies")
public class SolarBodyEndpoint {
    
    private final static Logger log = LoggerFactory.getLogger(SolarBodyEndpoint.class);
    
    private static final Map<String, String>TRANSLATIONS;
    
    static {
        Map<String, String>populate = new HashMap<String, String>();
        populate.put( "orbitBodyId", "orbitBody.id");
        TRANSLATIONS = Collections.unmodifiableMap( populate);
    }
    
    @Autowired
    private SolarBodyRepository solarBodyRepository;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Iterable<me.ineson.demo.service.SolarBody> find(
            @QueryParam("where") String where,
            @QueryParam("orderBy") String orderBy
            ) {
        log.debug("find all: where " + where + ", orderBy " + orderBy);
        
        Specification<SolarBody> predicate = null;
        if (StringUtils.isNotEmpty(where)) {
            predicate = new Specification<SolarBody>() {
                public Predicate toPredicate(Root<SolarBody> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                    return RestUtils.parseWhereClause( where, root, query, builder, TRANSLATIONS);
                }
            };
        }

        Sort sort = RestUtils.parseSortOrder(orderBy);

        if( log.isTraceEnabled()) {
            log.trace("predicate " + predicate + ", sort " + sort);
        }

        Iterable<SolarBody> results;

        if (predicate != null) {
            if (sort != null) {
                results = solarBodyRepository.findAll(predicate, sort);
            } else {
                results = solarBodyRepository.findAll(predicate);
            }
        } else {
            if (sort != null) {
                results = solarBodyRepository.findAll(sort);
            } else {
                results = solarBodyRepository.findAll();
            }
        }

        return SolarBodyHelper.convertList(results);
    }
    
    @GET @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Response findById(@PathParam("id") Long id) {
        log.info("findById " + id);
        Response.ResponseBuilder response;
        SolarBody body = solarBodyRepository.findOne(id);
        if (body != null) {
            response = Response.status(200).entity(SolarBodyHelper.convert(body));
        } else {
            response = Response.status(204);
        }

        return response.build();
    }

}
