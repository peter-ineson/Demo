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

import me.ineson.demo.service.db.DbConstants;
import me.ineson.demo.service.db.domain.SolarBody;
import me.ineson.demo.service.db.domain.SolarBodyLink;
import me.ineson.demo.service.db.domain.SolarBodyLinkType;
import me.ineson.demo.service.db.repo.jpa.SolarBodyLinkRepository;
import me.ineson.demo.service.db.repo.jpa.SolarBodyRepository;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SolarBodyLinkRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(SolarBodyLinkRepositoryTest.class);

    @Autowired
    private SolarBodyLinkRepository linkRepository;

    @Autowired
    private SolarBodyRepository solarBodyRepository;

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#count()}.
     */
    @Test
    public void testCount() {
        Assert.assertTrue("image count", linkRepository.count() > 10);
    }

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}.
     */
    @Test
    public void testExists() {
        Assert.assertTrue( "Mars Wiki link exists", linkRepository.exists(DbConstants.MARS_LINK_WIKI_ID));
    }
    
    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}.
     */
    @Test
    public void testNotExists() {
        Assert.assertFalse( "Image not exists", linkRepository.exists(Long.MIN_VALUE));
    }
    

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)}.
     */
    @Test
    public void testFindOne() {
        SolarBodyLink link = linkRepository.findOne(DbConstants.MARS_LINK_WIKI_ID);
        Assert.assertNotNull(link);
        Assert.assertEquals("Link type", SolarBodyLinkType.Wiki, link.getLinkType());
        Assert.assertEquals("Link url", DbConstants.MARS_LINK_WIKI_URL, link.getUrl());
        Assert.assertNull("link name", link.getName());
        SolarBody mars = link.getSolarBody();
        Assert.assertNotNull("link body", mars);
        Assert.assertEquals("Link body id", DbConstants.MARS_ID, mars.getId());
    }

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)}.
     */
    @Test
    public void testFindNone() {
        SolarBodyLink link = linkRepository.findOne(Long.MIN_VALUE);
        Assert.assertNull(link);
    }

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#delete(java.io.Serializable)}.
     */
    @SuppressWarnings("unused")
    @Test
    public void testDelete() {
        Assert.assertTrue( "Link exists to delete", linkRepository.exists(DbConstants.NEPTURE_LINK_NASA_ID));
        Iterable<SolarBodyLink>links = linkRepository.findBySolarBodyId(DbConstants.NEPTUNE_ID);
        Assert.assertNotNull("Delete Links list before - null", links);
        int count = 0;
        for (SolarBodyLink solarBodyLink : links) {
            count++;
        }
        Assert.assertEquals("Delete Links list before - count", 2, count);
        
        linkRepository.delete(DbConstants.NEPTURE_LINK_NASA_ID);
        Assert.assertNull("Should now be deleted", linkRepository.findOne(DbConstants.NEPTURE_LINK_NASA_ID));

        links = linkRepository.findBySolarBodyId(DbConstants.NEPTUNE_ID);
        Assert.assertNotNull("Delete Links list after - null", links);
        count = 0;
        for (SolarBodyLink solarBodyLink : links) {
            count++;
        }
        Assert.assertEquals("Delete Links list after - count", 1, count);
    }

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#delete(java.io.Serializable)}.
     */
    @SuppressWarnings("unused")
    @Test
    public void testInsert() {
        Iterable<SolarBodyLink>links = linkRepository.findBySolarBodyIdAndType(DbConstants.NEPTUNE_ID, SolarBodyLinkType.Other);
        Assert.assertNotNull("Insert Links list before - null", links);
        int count = 0;
        for (SolarBodyLink solarBodyLink : links) {
            count++;
        }
        Assert.assertEquals("Insert Links list before - count", 0, count);
        
        SolarBody neptune = solarBodyRepository.findOne( DbConstants.NEPTUNE_ID);
        Assert.assertNotNull(neptune);
        
        SolarBodyLink newLink = new SolarBodyLink();
        newLink.setLinkType(SolarBodyLinkType.Other);
        newLink.setSolarBody(neptune);
        newLink.setName(DbConstants.NEW_LINK_NAME);
        newLink.setUrl(DbConstants.NEW_LINK_URL);
        
        newLink = linkRepository.save(newLink);
        Assert.assertNotNull("new link", newLink);
        log.info("new id = " + newLink.getId());
        Assert.assertNotNull("new link id", newLink.getId());

        links = linkRepository.findBySolarBodyIdAndType(DbConstants.NEPTUNE_ID, SolarBodyLinkType.Other);
        Assert.assertNotNull("Delete Links list after - null", links);
        count = 0;
        for (SolarBodyLink solarBodyLink : links) {
            count++;
        }
        Assert.assertEquals("Delete Links list after - count", 1, count);
    }

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#delete(java.lang.Object)}.
     */
    @Test
    public void testSave() {
        SolarBodyLink link = linkRepository.findOne(DbConstants.MARS_LINK_GOOGLE_ID);
        Assert.assertNotNull( "Link exists to edit", link);
        
        Assert.assertNotNull( "before name null", link.getName());
        Assert.assertNotEquals( "before name", DbConstants.NEW_LINK_NAME,link.getName());
        Assert.assertNotEquals( "before url", DbConstants.NEW_LINK_URL,link.getUrl());

        link.setName( DbConstants.NEW_LINK_NAME);
        link.setUrl( DbConstants.NEW_LINK_URL);
        linkRepository.save(link);

        SolarBodyLink updatedLink = linkRepository.findOne(DbConstants.MARS_LINK_GOOGLE_ID);
        Assert.assertNotNull( "Updated link", updatedLink);
        
        Assert.assertEquals( "After name", DbConstants.NEW_LINK_NAME,updatedLink.getName());
        Assert.assertEquals( "After url", DbConstants.NEW_LINK_URL,updatedLink.getUrl());
    }


}
