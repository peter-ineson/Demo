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

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import me.ineson.demo.app.Config;
import me.ineson.demo.service.SolarBodyImage;
import me.ineson.demo.service.rest.SolarBodyImageRestClient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author peter
 *
 */
@Component
@Path("/solarBodies")
public class SolarBodyEndpoint {
    
    private final static Logger log = LoggerFactory.getLogger(SolarBodyEndpoint.class);

    final static String PATH = "solarBodies";

    private final static Client CLIENT = ClientBuilder.newClient();

    @Autowired
    private Config config;

    @GET @Path("{solarBodyId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Response findById(
            @HeaderParam("accept") String accept,
            @PathParam("solarBodyId") Long solarBodyId) {
        log.debug("Find solar body id {}, accept {}", solarBodyId, accept);

        WebTarget target = CLIENT.target(config.getStringManadtory(Config.SERVICE_REST_URL))
                .path(PATH).path(solarBodyId.toString());

        if (!RestConfig.supportedMediaType(accept)) {
            accept = MediaType.APPLICATION_JSON;
        }

        Response response = target.request(accept).get();

        String result;
        if (response.getStatus() == Status.OK.getStatusCode()) {
            result = response.readEntity(String.class);
        } else if (response.getStatus() == Status.NO_CONTENT.getStatusCode()) {
            // 204 - response
            return Response.status( 204).build();
        } else {
            log.error("Unknown response findOne lookup url {}, response {}\n{}", response,
                    response.readEntity(String.class));
            throw new IllegalStateException("Service returned an error");
        }

        return Response.ok( result, response.getMediaType()).build();
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML })
    public Response findAll(
            @HeaderParam("accept") String accept,
            @QueryParam("where") String where,
            @QueryParam("orderBy") String orderBy
            ) {
        log.debug("FindAll for mt {}, where={}, orderBy={}", accept, where, orderBy);

        WebTarget target = CLIENT.target(config.getStringManadtory(Config.SERVICE_REST_URL)).path(PATH);

        if (StringUtils.isNotBlank(where)) {
            target = target.queryParam("where", where);
        }

        if (StringUtils.isNotBlank(orderBy)) {
            target = target.queryParam("orderBy", orderBy);
        }

        if (!RestConfig.supportedMediaType(accept)) {
            accept = MediaType.APPLICATION_JSON;
        }

        Response response = target.request(accept).get();

        String result;
        if (response.getStatus() == Status.OK.getStatusCode()) {
            result = response.readEntity(String.class);
        } else if (response.getStatus() == Status.NO_CONTENT.getStatusCode()) {
            // 204 - response
            return Response.status( 204).build();
        } else {
            log.error("Unknown response findAll lookup url {}, response {}\n{}", response,
                    response.readEntity(String.class));
            throw new IllegalStateException("Service returned an error");
        }

        return Response.ok( result, response.getMediaType()).build();
    }    

    @GET @Path("{solarBodyId}/image")
    public Response findImageById(
            @PathParam("solarBodyId") Long solarBodyId) {
        log.debug("Find solar body image by id {}", solarBodyId);

        SolarBodyImageRestClient client = new SolarBodyImageRestClient();
        SolarBodyImage image = client.findById( config.getStringManadtory( Config.SERVICE_REST_URL), solarBodyId);

        if( image == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        
        return Response.ok().entity(new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                output.write(image.getImage());
                output.flush();
            }
          })
          .type( image.getContentType())
          .header("content-attachment" , "filename=" + image.getFilename())
          .header("Content-Length" , image.getImage().length)
          .build();
            
            //return 
        //Response.ok(image, MediaType.APPLICATION_OCTET_STREAM).header("content-attachment; filename=image_from_server.png") .build();
    }

}
