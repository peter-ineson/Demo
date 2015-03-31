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
package me.ineson.demo.service;

import java.net.URL;

import javax.xml.ws.BindingProvider;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.ws.client.BindingProviderProperties;

public class SolarBodyServiceTest {

    private static final Logger log = LoggerFactory.getLogger( SolarBodyServiceTest.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCountSuggestedUpdates() {
        
        URL wsdlFile = Thread.currentThread().getContextClassLoader().getResource("me/ineson/demo/service/SolarBodyService.wsdl");
        
        log.info( "WSDL file: " + wsdlFile);
        
        SolarBodyService_Service service = new SolarBodyService_Service( wsdlFile);
        SolarBodyService port = service.getSolarBodyService();

        // Use the BindingProvider's context to set the endpoint
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/DemoServices/solarBodyService");

        bp.getRequestContext().put(BindingProviderProperties.CONNECT_TIMEOUT, 10000);
        bp.getRequestContext().put(BindingProviderProperties.REQUEST_TIMEOUT, 10000);
  
        log.info( "Suggested counts: " + port.countSuggestedUpdates());

    }

}
