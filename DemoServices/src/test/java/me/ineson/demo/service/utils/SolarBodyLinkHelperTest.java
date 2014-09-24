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
package me.ineson.demo.service.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.ineson.demo.service.db.domain.SolarBody;
import me.ineson.demo.service.db.domain.SolarBodyLink;
import me.ineson.demo.service.db.domain.SolarBodyLinkType;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author peter
 *
 */
public class SolarBodyLinkHelperTest {

    /**
     * Test method for .
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @Test( expected=IllegalAccessException.class)
    public void testCannotInstantiate() throws InstantiationException, IllegalAccessException {
        SolarBodyLinkHelper.class.newInstance();
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyLinkHelper#convert(me.ineson.demo.service.db.domain.SolarBodyLink)}.
     */
    @Test
    public void testConvertNull() {
        Assert.assertNull( SolarBodyLinkHelper.convert( (SolarBodyLink) null));
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyLinkHelper#convert(me.ineson.demo.service.db.domain.SolarBodyLink)}.
     */
    @Test
    public void testEmptyConvert() {
        SolarBodyLink dbLink = new SolarBodyLink();
        me.ineson.demo.service.SolarBodyLink dtoLink = SolarBodyLinkHelper.convert(dbLink);
        Assert.assertNotNull( dtoLink);
        Assert.assertNull( dtoLink.getId());
        Assert.assertNull( dtoLink.getSolarBodyId());
        Assert.assertNull( dtoLink.getLinkType());
        Assert.assertNull( dtoLink.getName());
        Assert.assertNull( dtoLink.getUrl());
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyLinkHelper#convert(me.ineson.demo.service.db.domain.SolarBodyLink)}.
     */
    @Test
    public void testConvert() {
        SolarBodyLink dbLink = new SolarBodyLink();
        dbLink.setId( 6L);
        dbLink.setLinkType( SolarBodyLinkType.NASA);
        dbLink.setName( "t1");
        dbLink.setUrl( "t2");
        
        SolarBody dbSolarBody = new SolarBody();
        dbSolarBody.setId( 2L);
        dbLink.setSolarBody(dbSolarBody);

        me.ineson.demo.service.SolarBodyLink dtoLink = SolarBodyLinkHelper.convert(dbLink);
        
        Assert.assertNotNull( dtoLink);
        Assert.assertEquals( "id check", Long.valueOf(6L), dtoLink.getId());
        Assert.assertEquals( "link type check", me.ineson.demo.service.SolarBodyLinkType.NASA, dtoLink.getLinkType());
        Assert.assertEquals( "name check", "t1", dtoLink.getName());
        Assert.assertEquals( "desc check", "t2", dtoLink.getUrl());
    }


    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyLinkHelper#convert(me.ineson.demo.service.db.domain.SolarBodyLink)}.
     */
    @Test
    public void testConvertListNull() {
        List<me.ineson.demo.service.SolarBodyLink>result = SolarBodyLinkHelper.convertList( null);
        Assert.assertNotNull( result);
        Assert.assertTrue( result.isEmpty());
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyLinkHelper#convert(me.ineson.demo.service.db.domain.SolarBodyLink)}.
     */
    @Test
    public void testConvertListEmpty() {
        List<me.ineson.demo.service.SolarBodyLink>result = SolarBodyLinkHelper.convertList( Collections.emptyList());
        Assert.assertNotNull( result);
        Assert.assertTrue( result.isEmpty());
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyLinkHelper#convertList(java.lang.Iterable)}.
     */
    @Test
    public void testConvertList() {
        List<SolarBodyLink> dbLinks = new ArrayList<SolarBodyLink>();

        SolarBodyLink dbLink = new SolarBodyLink();
        dbLinks.add( dbLink);
        dbLink.setId( 6L);
        dbLink.setLinkType( SolarBodyLinkType.Wiki);
        dbLink.setName( "t1");
        
        SolarBody dbSolarBody = new SolarBody();
        dbSolarBody.setId( 2L);
        dbLink.setSolarBody(dbSolarBody);
        
        dbLink = new SolarBodyLink();
        dbLinks.add( dbLink);
        dbLink.setId( 7L);
        dbLink.setLinkType( SolarBodyLinkType.Other);
        dbLink.setName( "t2");

        List<me.ineson.demo.service.SolarBodyLink>results = SolarBodyLinkHelper.convertList( dbLinks);
        
        Assert.assertNotNull( results);
        Assert.assertEquals("List count", 2, results.size());

        me.ineson.demo.service.SolarBodyLink dtoLink = results.get(0);
        Assert.assertNotNull( dtoLink);
        Assert.assertEquals( "id 1 check", Long.valueOf(6L), dtoLink.getId());
        Assert.assertEquals( "body type 1 check", me.ineson.demo.service.SolarBodyLinkType.WIKI, dtoLink.getLinkType());
        Assert.assertEquals( "name 1 check", "t1", dtoLink.getName());
        Assert.assertEquals( "orbit body id 1 check", Long.valueOf(2L), dtoLink.getSolarBodyId());
    }


    @Test
    public void testConvertDtoNull() {
        Assert.assertNull( SolarBodyLinkHelper.convert( (me.ineson.demo.service.SolarBodyLink) null));
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyLinkHelper#convert(me.ineson.demo.service.db.domain.SolarBodyLink)}.
     */
    @Test
    public void testEmptyDtoConvert() {
        me.ineson.demo.service.SolarBodyLink dtoLink = new me.ineson.demo.service.SolarBodyLink();
        SolarBodyLink dbLink = SolarBodyLinkHelper.convert(dtoLink);
        Assert.assertNotNull( dbLink);
        Assert.assertNull( dbLink.getId());
        Assert.assertNull( dbLink.getLinkType());
        Assert.assertNull( dbLink.getName());
        Assert.assertNull( dbLink.getUrl());
        Assert.assertNull( dbLink.getSolarBody());
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyLinkHelper#convert(me.ineson.demo.service.db.domain.SolarBodyLink)}.
     */
    @Test
    public void testDtoConvert() {
        me.ineson.demo.service.SolarBodyLink dtoLink = new me.ineson.demo.service.SolarBodyLink();
        dtoLink.setId( 6L);
        dtoLink.setLinkType( me.ineson.demo.service.SolarBodyLinkType.OTHER);
        dtoLink.setName( "t1");
        dtoLink.setUrl( "t2");
        dtoLink.setSolarBodyId(2L);
        
        SolarBodyLink dbLink = SolarBodyLinkHelper.convert(dtoLink);
        
        Assert.assertNotNull( dbLink);
        Assert.assertEquals( "id check", Long.valueOf(6L), dbLink.getId());
        Assert.assertEquals( "link type check", SolarBodyLinkType.Other, dbLink.getLinkType());
        Assert.assertEquals( "name check", "t1", dbLink.getName());
        Assert.assertEquals( "desc check", "t2", dbLink.getUrl());
    }

}
