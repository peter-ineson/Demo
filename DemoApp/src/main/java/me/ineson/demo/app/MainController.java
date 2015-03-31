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


import java.util.List;

import javax.servlet.http.HttpSession;

import me.ineson.demo.service.SolarBody;
import me.ineson.demo.service.SolarBodyEndpointClient;
import me.ineson.demo.service.User;
import me.ineson.demo.service.rest.SolarBodyRestClient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author peter
 *
 */
@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private Config config;
 
    private SolarBodyEndpointClient SERVICE_ENDPOINT_CLIENT = new SolarBodyEndpointClient();
    
    /**
     * @param model
     * @return
     */
    @RequestMapping(value="/index", method = RequestMethod.GET)
    public String mainPage(Model model) {
        log.info("########################### Index");
 
        SolarBodyRestClient client = new SolarBodyRestClient();
        String serviceUrl = config.getStringManadtory( Config.SERVICE_REST_URL);
        Long theSunId = config.getLongManadtory( Config.SOLAR_SYSTEM_CENTRE_ID);
 
        SolarBody theSun = client.findById( serviceUrl, theSunId);
        Assert.notNull(theSun, "Lookup of Sun id " + theSunId);
        List<SolarBody>planets = client.findOrbitingBody( serviceUrl, theSunId);

        model.addAttribute("sun", theSun);
        model.addAttribute("planets", planets);
 
        log.info("########################### Index - end");
        return "index";
    }

    /**
     * @param form
     * @param session
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody String login(@ModelAttribute("login") LoginForm form, HttpSession session) {
        log.info( "User " + form.getUsername() + " attempting to login");
        String response = StringUtils.EMPTY;
        
        String serviceUrl = config.getStringManadtory( Config.SERVICE_ENDPOINT_URL);
        User user = SERVICE_ENDPOINT_CLIENT.login(serviceUrl, form.getUsername(), form.getPassword());

        if( user != null) {
            session.setAttribute( SecurityContext.ATTRIBUTE_NAME, new SecurityContext(user));
        } else {
            response = "Login failed, username or password was incorrect.";
        } 

        return response;
    }
    
    /**
     * @param model
     * @return
     */
    @RequestMapping(value="/logout")
    public @ResponseBody void logout(HttpSession session) {
        log.info( "User " + session.getAttribute(SecurityContext.ATTRIBUTE_NAME) + " is logging out");
        session.invalidate();
    }

}
