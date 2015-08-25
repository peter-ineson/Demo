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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.ineson.demo.service.db.domain.SolarBody;
import me.ineson.demo.service.db.domain.SolarBodyImage;
import me.ineson.demo.service.db.domain.SolarBodyType;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author peter
 *
 */
public class SolarBodyHelperTest {

    private static final BigDecimal MASS_55 = BigDecimal.valueOf(55L);

    /**
     * Test method for .
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @Test( expected=IllegalAccessException.class)
    public void testCannotInstantiate() throws InstantiationException, IllegalAccessException {
        SolarBodyHelper.class.newInstance();
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyHelper#convert(me.ineson.demo.service.db.domain.SolarBody)}.
     */
    @Test
    public void testConvertNull() {
        Assert.assertNull( SolarBodyHelper.convert( (SolarBody) null));
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyHelper#convert(me.ineson.demo.service.db.domain.SolarBody)}.
     */
    @Test
    public void testEmptyConvert() {
        SolarBody dbSolarBody = new SolarBody();
        me.ineson.demo.service.SolarBody dtoSolarBody = SolarBodyHelper.convert(dbSolarBody);
        Assert.assertNotNull( dtoSolarBody);
        Assert.assertNull( dtoSolarBody.getId());
        Assert.assertNull( dtoSolarBody.getBodyType());
        Assert.assertNull( dtoSolarBody.getName());
        Assert.assertNull( dtoSolarBody.getDescription());
        Assert.assertNull( dtoSolarBody.getMass());
        Assert.assertNull( dtoSolarBody.getRadius());
        Assert.assertNull( dtoSolarBody.getOrbitBodyId());
        Assert.assertNull( dtoSolarBody.getOrbitDistance());
        Assert.assertNull( dtoSolarBody.getImageWidth());
        Assert.assertNull( dtoSolarBody.getImageHeight());
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyHelper#convert(me.ineson.demo.service.db.domain.SolarBody)}.
     */
    @Test
    public void testConvertNoImage() {
        SolarBody dbSolarBody = new SolarBody();
        dbSolarBody.setId( 6L);
        dbSolarBody.setBodyType( SolarBodyType.Planet);
        dbSolarBody.setName( "t1");
        dbSolarBody.setDescription( "t2");
        dbSolarBody.setMass( MASS_55);
        dbSolarBody.setRadius( 400L);
        dbSolarBody.setOrbitDistance(777L);

        SolarBody dbOrbitsBody = new SolarBody();
        dbOrbitsBody.setId( 2L);
        dbSolarBody.setOrbitBody(dbOrbitsBody);

        me.ineson.demo.service.SolarBody dtoSolarBody = SolarBodyHelper.convert(dbSolarBody);
        
        Assert.assertNotNull( dtoSolarBody);
        Assert.assertEquals( "id check", Long.valueOf(6L), dtoSolarBody.getId());
        Assert.assertEquals( "body type check", me.ineson.demo.service.SolarBodyType.PLANET, dtoSolarBody.getBodyType());
        Assert.assertEquals( "name check", "t1", dtoSolarBody.getName());
        Assert.assertEquals( "desc check", "t2", dtoSolarBody.getDescription());
        Assert.assertEquals( "mass check", MASS_55, dtoSolarBody.getMass());
        Assert.assertEquals( "radius check", Long.valueOf(400L), dtoSolarBody.getRadius());
        Assert.assertEquals( "orbit body id check", Long.valueOf(2L), dtoSolarBody.getOrbitBodyId());
        Assert.assertEquals( "orbit distance check", Long.valueOf(777L), dtoSolarBody.getOrbitDistance());
        Assert.assertNull( "image width", dtoSolarBody.getImageWidth());
        Assert.assertNull( "image height", dtoSolarBody.getImageHeight());
    }
    
    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyHelper#convert(me.ineson.demo.service.db.domain.SolarBody)}.
     */
    @Test
    public void testConvert() {
        SolarBody dbSolarBody = new SolarBody();
        dbSolarBody.setId( 6L);
        dbSolarBody.setBodyType( SolarBodyType.Planet);
        dbSolarBody.setName( "t1");
        dbSolarBody.setDescription( "t2");
        dbSolarBody.setMass( MASS_55);
        dbSolarBody.setRadius( 400L);
        dbSolarBody.setOrbitDistance(777L);

        final BigInteger imageWidth = BigInteger.valueOf( 223L);
        final BigInteger imageHeight = BigInteger.valueOf( 324L);
        SolarBodyImage dbSolarBodyImage = new SolarBodyImage();
        dbSolarBodyImage.setSolarBodyId( 6L);
        dbSolarBodyImage.setImageWidth(imageWidth);
        dbSolarBodyImage.setImageHeight(imageHeight);
        dbSolarBody.setImage(dbSolarBodyImage);

        SolarBody dbOrbitsBody = new SolarBody();
        dbOrbitsBody.setId( 2L);
        dbSolarBody.setOrbitBody(dbOrbitsBody);

        me.ineson.demo.service.SolarBody dtoSolarBody = SolarBodyHelper.convert(dbSolarBody);
        
        Assert.assertNotNull( dtoSolarBody);
        Assert.assertEquals( "id check", Long.valueOf(6L), dtoSolarBody.getId());
        Assert.assertEquals( "body type check", me.ineson.demo.service.SolarBodyType.PLANET, dtoSolarBody.getBodyType());
        Assert.assertEquals( "name check", "t1", dtoSolarBody.getName());
        Assert.assertEquals( "desc check", "t2", dtoSolarBody.getDescription());
        Assert.assertEquals( "mass check", MASS_55, dtoSolarBody.getMass());
        Assert.assertEquals( "radius check", Long.valueOf(400L), dtoSolarBody.getRadius());
        Assert.assertEquals( "orbit body id check", Long.valueOf(2L), dtoSolarBody.getOrbitBodyId());
        Assert.assertEquals( "orbit distance check", Long.valueOf(777L), dtoSolarBody.getOrbitDistance());
        Assert.assertEquals( "image width", imageWidth, dtoSolarBody.getImageWidth());
        Assert.assertEquals( "image height", imageHeight, dtoSolarBody.getImageHeight());
    }


    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyHelper#convert(me.ineson.demo.service.db.domain.SolarBody)}.
     */
    @Test
    public void testConvertListNull() {
        List<me.ineson.demo.service.SolarBody>result = SolarBodyHelper.convertList( null);
        Assert.assertNotNull( result);
        Assert.assertTrue( result.isEmpty());
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyHelper#convert(me.ineson.demo.service.db.domain.SolarBody)}.
     */
    @Test
    public void testConvertListEmpty() {
        List<me.ineson.demo.service.SolarBody>result = SolarBodyHelper.convertList( Collections.emptyList());
        Assert.assertNotNull( result);
        Assert.assertTrue( result.isEmpty());
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyHelper#convertList(java.lang.Iterable)}.
     */
    @Test
    public void testConvertList() {
        List<SolarBody> dbBodies = new ArrayList<SolarBody>();

        SolarBody dbSolarBody = new SolarBody();
        dbBodies.add( dbSolarBody);
        dbSolarBody.setId( 6L);
        dbSolarBody.setBodyType( SolarBodyType.Planet);
        dbSolarBody.setName( "t1");
        
        SolarBody dbOrbitsBody = new SolarBody();
        dbOrbitsBody.setId( 2L);
        dbSolarBody.setOrbitBody(dbOrbitsBody);
        
        dbSolarBody = new SolarBody();
        dbBodies.add( dbSolarBody);
        dbSolarBody.setId( 7L);
        dbSolarBody.setBodyType( SolarBodyType.Sun);
        dbSolarBody.setName( "t2");

        List<me.ineson.demo.service.SolarBody>results = SolarBodyHelper.convertList( dbBodies);
        
        Assert.assertNotNull( results);
        Assert.assertEquals("List count", 2, results.size());

        me.ineson.demo.service.SolarBody dtoSolarBody = results.get(0);
        Assert.assertNotNull( dtoSolarBody);
        Assert.assertEquals( "id 1 check", Long.valueOf(6L), dtoSolarBody.getId());
        Assert.assertEquals( "body type 1 check", me.ineson.demo.service.SolarBodyType.PLANET, dtoSolarBody.getBodyType());
        Assert.assertEquals( "name 1 check", "t1", dtoSolarBody.getName());
        Assert.assertEquals( "orbit body id 1 check", Long.valueOf(2L), dtoSolarBody.getOrbitBodyId());
        
    }


    @Test
    public void testConvertDtoNull() {
        Assert.assertNull( SolarBodyHelper.convert( (me.ineson.demo.service.SolarBody) null));
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyHelper#convert(me.ineson.demo.service.db.domain.SolarBody)}.
     */
    @Test
    public void testEmptyDtoConvert() {
        me.ineson.demo.service.SolarBody dtoSolarBody = new me.ineson.demo.service.SolarBody();
        SolarBody dbSolarBody = SolarBodyHelper.convert(dtoSolarBody);
        Assert.assertNotNull( dbSolarBody);
        Assert.assertNull( dbSolarBody.getId());
        Assert.assertNull( dbSolarBody.getBodyType());
        Assert.assertNull( dbSolarBody.getName());
        Assert.assertNull( dbSolarBody.getDescription());
        Assert.assertNull( dbSolarBody.getMass());
        Assert.assertNull( dbSolarBody.getRadius());
        Assert.assertNull( dbSolarBody.getOrbitBody());
        Assert.assertNull( dbSolarBody.getOrbitDistance());
    }

    /**
     * Test method for {@link me.ineson.demo.service.utils.SolarBodyHelper#convert(me.ineson.demo.service.db.domain.SolarBody)}.
     */
    @Test
    public void testDtoConvert() {
        me.ineson.demo.service.SolarBody dtoSolarBody = new me.ineson.demo.service.SolarBody();
        dtoSolarBody.setId( 6L);
        dtoSolarBody.setBodyType( me.ineson.demo.service.SolarBodyType.PLANET);
        dtoSolarBody.setName( "t1");
        dtoSolarBody.setDescription( "t2");
        dtoSolarBody.setMass( MASS_55);
        dtoSolarBody.setRadius( 400L);
        dtoSolarBody.setOrbitDistance(777L);
        dtoSolarBody.setOrbitBodyId(2L);
        
        SolarBody dbSolarBody = SolarBodyHelper.convert(dtoSolarBody);
        
        Assert.assertNotNull( dbSolarBody);
        Assert.assertEquals( "id check", Long.valueOf(6L), dbSolarBody.getId());
        Assert.assertEquals( "body type check", SolarBodyType.Planet, dbSolarBody.getBodyType());
        Assert.assertEquals( "name check", "t1", dbSolarBody.getName());
        Assert.assertEquals( "desc check", "t2", dbSolarBody.getDescription());
        Assert.assertEquals( "mass check", MASS_55, dbSolarBody.getMass());
        Assert.assertEquals( "radius check", Long.valueOf(400L), dbSolarBody.getRadius());
        Assert.assertEquals( "orbit distance check", Long.valueOf(777L), dbSolarBody.getOrbitDistance());
        Assert.assertNull( "orbit body check", dbSolarBody.getOrbitBody());
    }

}
