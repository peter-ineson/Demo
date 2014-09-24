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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import me.ineson.demo.service.db.domain.SolarBodyImage;
import me.ineson.demo.service.db.repo.jpa.SolarBodyImageRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author peter
 *
 */
@Component
@Path("/solarBodies/{solarBodyId}/image")
public class SolarBodyImageEndpoint {
    
    private final static Logger log = LoggerFactory.getLogger(SolarBodyImageEndpoint.class);
    
    @Autowired
    private SolarBodyImageRepository imageRepository;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Response findById(@PathParam("solarBodyId") Long solarBodyId) {
        log.debug( "Looking up image for solar body id " + solarBodyId);
        Response.ResponseBuilder response;
        SolarBodyImage dbImage = imageRepository.findOne(solarBodyId);
        if(dbImage != null) {
            me.ineson.demo.service.SolarBodyImage dtoImage = new me.ineson.demo.service.SolarBodyImage();
            dtoImage.setSolarBodyId( solarBodyId);
            dtoImage.setFilename( dbImage.getFilename());
            dtoImage.setContentType( dbImage.getContentType());
            dtoImage.setImage( dbImage.getImage());
            response = Response.status(200).entity(dtoImage); 
        } else {
            response = Response.status(204).entity("The SolarBody with the id " + solarBodyId + " does not exist");
        }
        
        return response.build();
    }
    
}
