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
package me.ineson.demo.service.db.repo.jpa;

import java.math.BigDecimal;
import java.util.List;

import me.ineson.demo.service.db.DbConstants;
import me.ineson.demo.service.db.domain.SolarBody;
import me.ineson.demo.service.db.domain.SolarBodyType;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author peter
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/test-mongoDb-datasource.xml","classpath:/spring/test-jdbc-datasource.xml","classpath:/spring/application-db.xml"})
public class SolarBodyRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(SolarBodyRepositoryTest.class);

    @Autowired
    private SolarBodyRepository solarBodyRepository;

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}.
     */
    @Test
    public void testExists() {
        Assert.assertTrue( "Sun exists", solarBodyRepository.exists(DbConstants.SUN_ID));
        Assert.assertFalse( "Body not exists", solarBodyRepository.exists(Long.MIN_VALUE));
    }
    

    /**
     * Test case for method {@link SolarBodyRepository#findOne(Long)}.
     */
    @Test
    public void testFindOne() {
        SolarBody rec = solarBodyRepository.findOne( DbConstants.SUN_ID);
        log.debug( "Search for Sun " + rec);
        Assert.assertNotNull( "Find Sun", rec);
        Assert.assertEquals( "check id", DbConstants.SUN_ID, rec.getId());
        Assert.assertEquals( "check name", "Sun", rec.getName());
        Assert.assertNull( "check orbit around", rec.getOrbitBody());
        Assert.assertEquals( "check body type", SolarBodyType.Sun, rec.getBodyType());
        Assert.assertNotNull( "check desc not null", rec.getDescription());
        Assert.assertTrue("check desc", rec.getDescription().contains("Sun is the star"));
        Assert.assertNull( "check orbit dist", rec.getOrbitDistance());
        Assert.assertEquals( "check raduis", Long.valueOf(696342L), rec.getRadius());
        Assert.assertEquals( "check mass", new BigDecimal("1.989E30"), rec.getMass());
    }


    /**
     * Test case for method {@link SolarBodyRepository#findAll()}.
     */
    @Test
    public void testFindAll() {
        Iterable<SolarBody> recs = solarBodyRepository.findAll();

        Assert.assertNotNull( "Find all", recs);
        int count = 0;
        for (@SuppressWarnings("unused") SolarBody solarBody : recs) {
            count++;
        }
        //recs.forEach(body -> { count++; });
        //count = recs.stream().filter( body -> {}).count();
        
        log.warn( "Find all count(*) " + count);

        Assert.assertTrue( "Find all count", count > 5);
    }

    /**
     * Test case for methods {@link SolarBodyRepository#save(SolarBody)} and
     * {@link SolarBodyRepository#delete(Long)}
     */
    @Test
    public void testInsertAndDelete() {
        SolarBody mars = solarBodyRepository.findOne( DbConstants.MARS_ID);
        Assert.assertNotNull( "Find mars", mars);
        
        SolarBody newRec = new SolarBody();
        newRec.setName( DbConstants.NEW_NAME);
        newRec.setOrbitBody( mars);
        newRec.setBodyType( DbConstants.NEW_TYPE);
        newRec.setDescription(DbConstants.NEW_DESC);
        newRec.setOrbitDistance(DbConstants.NEW_ORBIT_DISTANCE);
        newRec.setRadius(DbConstants.NEW_RADIUS);
        newRec.setMass(DbConstants.NEW_MASS);

        newRec = solarBodyRepository.save(newRec);
        Assert.assertNotNull( "New body", newRec);
        log.info( "New solar body id " + newRec.getId());
        
        SolarBody newBody = solarBodyRepository.findOne( newRec.getId());
        
        Assert.assertNotNull( "Find new body", newBody);
        Assert.assertEquals( "check id", newRec.getId(), newBody.getId());
        Assert.assertEquals( "check name", DbConstants.NEW_NAME, newBody.getName());
        Assert.assertNotNull( "check orbit around", newBody.getOrbitBody());
        Assert.assertEquals( "check orbit around id", DbConstants.MARS_ID, newBody.getOrbitBody().getId());
        Assert.assertEquals( "check body type", SolarBodyType.Planet, newBody.getBodyType());
        Assert.assertEquals( "check desc", DbConstants.NEW_DESC, newBody.getDescription());
        Assert.assertEquals( "check orbit dist", DbConstants.NEW_ORBIT_DISTANCE, newBody.getOrbitDistance());
        Assert.assertEquals( "check raduis", DbConstants.NEW_RADIUS, newBody.getRadius());
        Assert.assertEquals( "check mass", DbConstants.NEW_MASS, newBody.getMass());

        solarBodyRepository.delete( newBody.getId());
    
        Assert.assertFalse( "new deleted", solarBodyRepository.exists( newBody.getId()));
        
    }

    /**
     * Test case for method {@link SolarBodyRepository#save(SolarBody)}.
     */
    @Test
    public void testUpdate() {
        
        SolarBody jupiter = solarBodyRepository.findOne( DbConstants.JUPITER_ID);

        Assert.assertNotNull( "Find Jupiter", jupiter);
        log.info("Jupiter: " + jupiter);
        Assert.assertEquals("before check", DbConstants.JUPITER_NAME, jupiter.getName());
        jupiter.setName( DbConstants.NEW_NAME);
        solarBodyRepository.save(jupiter);
        
        SolarBody updatedMars = solarBodyRepository.findOne( DbConstants.JUPITER_ID);
        Assert.assertNotNull( "Updated Jupiter", updatedMars);
        Assert.assertEquals("after check", DbConstants.NEW_NAME, updatedMars.getName());
        
    }

    
    /**
     * Test case for method {@link SolarBodyRepository#findByNameIgnoreCase(String)}.
     */
    @Test
    public void testByNameSuccess() {
        List<SolarBody> recs = solarBodyRepository.findByNameIgnoreCase( DbConstants.MARS_NAME);
        log.debug( "Search for Mars " + recs);
        Assert.assertNotNull( "Find Sun", recs);
        Assert.assertEquals( "Find Mars count", 1, recs.size());
        
        SolarBody rec = recs.get(0);
        Assert.assertEquals( "check id", DbConstants.MARS_ID, rec.getId());
    }

    /**
     * Test case for method {@link SolarBodyRepository#findByNameIgnoreCase(String)}.
     */
    @Test
    public void testByNameNotFound() {
        List<SolarBody> recs = solarBodyRepository.findByNameIgnoreCase( "#@$@#$@#$#@$");
        log.debug( "Not found name Search " + recs);
        Assert.assertNotNull( "Not found name search", recs);
        Assert.assertEquals( "Find Mars count", 0, recs.size());
    }
}
