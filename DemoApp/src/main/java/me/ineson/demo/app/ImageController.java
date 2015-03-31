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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import me.ineson.demo.service.SolarBodyImage;
import me.ineson.demo.service.rest.SolarBodyImageRestClient;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author peter
 *
 */
@Controller
@RequestMapping("/solarBodies")
public class ImageController {

    private static Logger log = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private Config config;
    
    @RequestMapping(value="/{solarBodyId}/image", method = RequestMethod.GET)
    public void getSolarBodyImage(@PathVariable Long solarBodyId, HttpServletResponse httpResponse) throws IOException {
        log.debug("Getting image for id {}", solarBodyId);
        SolarBodyImageRestClient client = new SolarBodyImageRestClient();
        SolarBodyImage image = client.findById( config.getStringManadtory( Config.SERVICE_REST_URL), solarBodyId);
        
        if( image != null) {
            httpResponse.setContentType(image.getContentType());
            httpResponse.setContentLength( image.getImage().length);
            try( OutputStream responseOutputStream = httpResponse.getOutputStream()) {
                try( InputStream imageInputStream = new ByteArrayInputStream( image.getImage())) {
                    IOUtils.copy( imageInputStream, responseOutputStream);
                    responseOutputStream.flush();
                }
            }
        } else {
            httpResponse.sendError( 404);
        }
    }

}
