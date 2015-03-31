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
import java.util.List;

import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.ws.client.BindingProviderProperties;

/**
 * @author peter
 *
 */
public class SolarBodyEndpointClient {
    
    private static final Logger log = LoggerFactory.getLogger(SolarBodyEndpointClient.class);
    
    private final static SolarBodyService_Service ENDPOINT_SERVICE;
    
    static {
        URL wsdlFile = Thread.currentThread().getContextClassLoader().getResource("me/ineson/demo/service/SolarBodyService.wsdl");
        log.debug( "WSDL file: " + wsdlFile);
        ENDPOINT_SERVICE = new SolarBodyService_Service( wsdlFile);
    }

    public void submitSuggestedUpdate(String endpointUrl, Suggestion suggestion) {
        getPort(endpointUrl).submitSuggestedUpdate(suggestion);;
    }

    public List<Suggestion> listSuggestedUpdates(String endpointUrl, Long arg0) {
        return getPort(endpointUrl).listSuggestedUpdates(arg0);
    }

    public Long countSuggestedUpdates(String endpointUrl) {
        return getPort(endpointUrl).countSuggestedUpdates();
    }

    public void acceptSuggestedUpdate(String endpointUrl, String arg0, SolarBody solarBody) {
        getPort(endpointUrl).acceptSuggestedUpdate(arg0, solarBody);
    }

    public void rejectSuggestedUpdate(String endpointUrl, String arg0) {
        getPort(endpointUrl).rejectSuggestedUpdate(arg0);
    }

    public User login(String endpointUrl, String username, String password) {
        return getPort(endpointUrl).login(username, password);
    }

    public User getGuestUser(String endpointUrl) {
        return getPort(endpointUrl).getGuestUser();
    }

    private SolarBodyService getPort( String endpointUrl) {
        SolarBodyService port = ENDPOINT_SERVICE.getSolarBodyService();
    
        // Use the BindingProvider's context to set the endpoint
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
    
        bp.getRequestContext().put(BindingProviderProperties.CONNECT_TIMEOUT, 10000);
        bp.getRequestContext().put(BindingProviderProperties.REQUEST_TIMEOUT, 10000);
    
        return port;
    }

}
