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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import me.ineson.demo.service.SolarBody;
import me.ineson.demo.service.SolarBodyType;
import me.ineson.demo.service.db.DbConstants;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author peter
 *
 */
@RunWith(Theories.class)
public class SolarBodyRestTest {
    @DataPoint public static MediaType FORMAT_JSON = MediaType.APPLICATION_JSON_TYPE;
    @DataPoint public static MediaType FORMAT_APPLICATION_XML = MediaType.APPLICATION_XML_TYPE;
    @DataPoint public static MediaType FORMAT_TEXT_XML = MediaType.TEXT_XML_TYPE;

    private static Logger log = LoggerFactory.getLogger(SolarBodyRestTest.class);

    private final static String HOST_URL = "http://localhost:8080/DemoServices/rest";

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}.
     */
    @Theory
    public void testFindAll(MediaType mediaType) {
        SolarBodyRestClient client = new SolarBodyRestClient(mediaType);
        List<SolarBody> bodies = client.findAll(HOST_URL);
        log.debug("Solar body response: " + bodies);

        Assert.assertNotNull("find all results", bodies);
        Assert.assertEquals("find all record count", 9, bodies.size());
    }

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}.
     */
    @Theory
    public void testFindWhere(MediaType mediaType) {
        SolarBodyRestClient client = new SolarBodyRestClient(mediaType);

        Map<String, Object> criteria = new HashMap<String, Object>();
        criteria.put("id", DbConstants.MARS_ID);

        List<SolarBody> bodies = client.findAll(HOST_URL, criteria, null);

        Assert.assertNotNull("find all results", bodies);
        Assert.assertEquals("find all record count", 1, bodies.size());

        SolarBody mars = bodies.get(0);
        Assert.assertNotNull("Mars", mars);
        Assert.assertEquals("Mars? id", DbConstants.MARS_ID, mars.getId());
        Assert.assertEquals("Mars? name", DbConstants.MARS_NAME, mars.getName());
    }

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}.
     */
    @Theory
    public void testFindWhereCmr(MediaType mediaType) {
        SolarBodyRestClient client = new SolarBodyRestClient(mediaType);

        Map<String, Object> criteria = new HashMap<String, Object>();
        criteria.put("orbitBody.id", DbConstants.SUN_ID);

        List<SolarBody> bodies = client.findAll(HOST_URL, criteria, null);

        Assert.assertNotNull("find orbit sun results", bodies);
        Assert.assertEquals("find sun record count", 8, bodies.size());
    }

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}.
     */
    @Theory
    public void testFindWhereCmrNone(MediaType mediaType) {
        SolarBodyRestClient client = new SolarBodyRestClient(mediaType);
        Map<String, Object> criteria = new HashMap<String, Object>();
        criteria.put("orbitBodyId", DbConstants.NEPTUNE_ID);

        Collection<String> orderBy = new ArrayList<String>();
        orderBy.add("+orbitDistance");

        List<SolarBody> bodies = client.findAll(HOST_URL, criteria, null);

        Assert.assertNotNull("find orbit nepture results", bodies);
        Assert.assertEquals("find orbit nettune record count", 0, bodies.size());
    }

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}.
     */
    @Theory
    public void testFindOrderBy(MediaType mediaType) {
        SolarBodyRestClient client = new SolarBodyRestClient(mediaType);

        Collection<String> orderBy = new ArrayList<String>();
        orderBy.add("radius");

        List<SolarBody> bodies = client.findAll(HOST_URL, null, orderBy);

        Assert.assertNotNull("find smallest results", bodies);
        Assert.assertEquals("find smallest record count", 9, bodies.size());

        SolarBody body = bodies.get(0);
        Assert.assertNotNull("smallest", body);
        Assert.assertEquals("smallest id", DbConstants.MERCURY_ID, body.getId());
        body = bodies.get(8);
        Assert.assertNotNull("bigest", body);
        Assert.assertEquals("bigest id", DbConstants.SUN_ID, body.getId());

        // Test by prefixing with '+', still should be ascending.
        orderBy = new ArrayList<String>();
        orderBy.add("+radius");

        bodies = client.findAll(HOST_URL, null, orderBy);

        Assert.assertNotNull("find smallest results", bodies);
        Assert.assertEquals("find smallest record count", 9, bodies.size());

        body = bodies.get(0);
        Assert.assertNotNull("smallest", body);
        Assert.assertEquals("smallest id", DbConstants.MERCURY_ID, body.getId());
        body = bodies.get(8);
        Assert.assertNotNull("bigest", body);
        Assert.assertEquals("bigest id", DbConstants.SUN_ID, body.getId());

        // Test by descending.
        orderBy = new ArrayList<String>();
        orderBy.add("-mass");

        bodies = client.findAll(HOST_URL, null, orderBy);

        Assert.assertNotNull("find heavyest results", bodies);
        Assert.assertEquals("find heavyest record count", 9, bodies.size());

        body = bodies.get(0);
        Assert.assertNotNull("heavyest", body);
        Assert.assertEquals("heavyest id", DbConstants.SUN_ID, body.getId());
        body = bodies.get(8);
        Assert.assertNotNull("lightest", body);
        Assert.assertEquals("lightest id", DbConstants.MERCURY_ID, body.getId());
    }    
    
    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}.
     */
    @Theory
    public void testFindWhereCmrOrder(MediaType mediaType) {
        SolarBodyRestClient client = new SolarBodyRestClient(mediaType);
        List<SolarBody> bodies = client.findOrbitingBody(HOST_URL, DbConstants.SUN_ID);

        Assert.assertNotNull("find orbit sun results", bodies);
        Assert.assertEquals("find sun record count", 8, bodies.size());

        SolarBody body = bodies.get(0);
        Assert.assertNotNull("inner orbit", body);
        Assert.assertEquals("inner orbit id", DbConstants.MERCURY_ID, body.getId());
        Assert.assertEquals("inner orbit body id", DbConstants.SUN_ID, body.getOrbitBodyId());
        body = bodies.get(2);
        Assert.assertNotNull("3rd rock orbit", body);
        Assert.assertEquals("3rd rock orbit id", DbConstants.EARTH_ID, body.getId());
        Assert.assertEquals("3rd rock orbit body id", DbConstants.SUN_ID, body.getOrbitBodyId());
        Assert.assertEquals("3rd rock type", SolarBodyType.PLANET, body.getBodyType());
        Assert.assertEquals("3rd rock type", Long.valueOf(149600000L), body.getOrbitDistance());

        body = bodies.get(7);
        Assert.assertNotNull("outer orbit", body);
        Assert.assertEquals("outer orbit id", DbConstants.NEPTUNE_ID, body.getId());
        Assert.assertEquals("outer orbit body id", DbConstants.SUN_ID, body.getOrbitBodyId());
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}
     * .
     */
    @Theory
    public void testFindOne(MediaType mediaType) {
        SolarBodyRestClient client = new SolarBodyRestClient(mediaType);
        log.info("test find one: with new client");
        SolarBody body = client.findById(HOST_URL, DbConstants.SUN_ID);
        log.info("Solar body response: " + body);

        Assert.assertNotNull("Sun exists", body);
        Assert.assertEquals("sun id", DbConstants.SUN_ID, body.getId());
        Assert.assertEquals("sun name", "Sun", body.getName());
        Assert.assertEquals("sun type", SolarBodyType.SUN, body.getBodyType());
        Assert.assertNull("sun orbits", body.getOrbitBodyId());
        Assert.assertNull("sun orbit distance", body.getOrbitDistance());
        Assert.assertEquals("sun radius", Long.valueOf(696342L), body.getRadius());
        Assert.assertEquals("sun mass", 0, new BigDecimal("1.989E30").compareTo(body.getMass()));
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}
     * .
     */
    @Theory
    public void testFindNone(MediaType mediaType) {
        SolarBodyRestClient client = new SolarBodyRestClient(mediaType);
        SolarBody body = client.findById(HOST_URL, Long.MIN_VALUE);
        Assert.assertNull("does not exist", body);
    }

}
