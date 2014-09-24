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

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import me.ineson.demo.service.db.domain.SolarBodyLink;
import me.ineson.demo.service.db.repo.jpa.SolarBodyLinkRepository;
import me.ineson.demo.service.utils.SolarBodyLinkHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author peter
 *
 */
@Component
@Path("/solarBodies/{solarBodyid}/links")
public class SolarBodyLinkEndpoint {
    
    private final static Logger log = LoggerFactory.getLogger(SolarBodyLinkEndpoint.class);
    
    @Autowired
    private SolarBodyLinkRepository solarBodyLinkRepository;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Iterable<me.ineson.demo.service.SolarBodyLink> findBySolarBody(
            @PathParam("solarBodyid") Long solarBodyid,
            @QueryParam("where") String where,
            @QueryParam("orderBy") String orderBy
            ) {
        log.info( "Looking up links for solarBodyId {}", solarBodyid);
        Iterable<SolarBodyLink> links = solarBodyLinkRepository.findBySolarBodyId( solarBodyid);
        log.debug( "Found links {}", links);
        return SolarBodyLinkHelper.convertList(links); 
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Iterable<me.ineson.demo.service.SolarBodyLink> findPost(
            @PathParam("solarBodyid") Long solarBodyid
            ) {
        log.info( "Looking up links for solarBodyId {}", solarBodyid);
        //Iterable<SolarBodyLink> links = solarBodyLinkRepository.findBySolarBodyId( solarBodyid);
        //log.debug( "Found links {}", links);
        throw new IllegalStateException( "bugger");
        //return SolarBodyLinkHelper.convertList(links); 
    }
    
}
