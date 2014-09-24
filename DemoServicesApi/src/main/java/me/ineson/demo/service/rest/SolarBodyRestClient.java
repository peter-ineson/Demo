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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import me.ineson.demo.service.SolarBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author peter
 *
 */
public class SolarBodyRestClient extends AbstractRestService<SolarBody>{
    
    private static final Logger log = LoggerFactory.getLogger(SolarBodyRestClient.class);
    
    private final static String ENDPOINT_PATH = "solarBodies";
    
    public SolarBodyRestClient() {
        super( SolarBody.class, new GenericType<List<SolarBody>>() {});
    }
    
    public SolarBodyRestClient(MediaType mediaType) {
        super( SolarBody.class, new GenericType<List<SolarBody>>() {}, mediaType);
    }

    public SolarBody findById(String baseUrl, Long id) {
        Assert.notNull(baseUrl, "baseUrl was NULL or empty");
        Assert.notNull(id, "id was NULL");
        log.debug("Calling {} for solar body id {}", baseUrl, id);

        String url = baseUrl + "/" + ENDPOINT_PATH + "/" + id.toString();

        return findOne(url);
    }

    /**
     * @param baseUrl
     * @return
     */
    public List<SolarBody> findAll(String baseUrl) {
        Assert.notNull(baseUrl, "baseUrl was NULL or empty");
        log.debug("Calling {} for solar body id ");

        String url = baseUrl + "/" + ENDPOINT_PATH;
        return findMany(url, null, null);
    }

    /**
     * @param baseUrl
     * @param criteria
     * @param orderBy
     * @return
     */
    public List<SolarBody> findAll(String baseUrl, Map<String, Object> criteria, Collection<String> orderBy) {
        Assert.notNull(baseUrl, "baseUrl was NULL or empty");
        log.debug("Calling {} for solar body id ");

        String url = baseUrl + "/" + ENDPOINT_PATH;
        return findMany(url, criteria, orderBy);
    }

    public List<SolarBody> findOrbitingBody(String baseUrl, Long centerBodyId) {
        Assert.notNull(baseUrl, "baseUrl was NULL or empty");
        log.debug("Calling {} for solar body id ");

        String url = baseUrl + "/" + ENDPOINT_PATH;

        Map<String, Object> criteria = new HashMap<String, Object>();
        criteria.put("orbitBodyId", centerBodyId);

        Collection<String> orderBy = new ArrayList<String>();
        orderBy.add("+orbitDistance");

        return findMany(url, criteria, orderBy);
    }

}
