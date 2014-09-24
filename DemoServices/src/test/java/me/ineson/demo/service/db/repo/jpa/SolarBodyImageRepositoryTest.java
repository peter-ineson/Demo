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

import static org.junit.Assert.assertNull;
import me.ineson.demo.service.db.DbConstants;
import me.ineson.demo.service.db.domain.SolarBody;
import me.ineson.demo.service.db.domain.SolarBodyImage;
import me.ineson.demo.service.db.repo.jpa.SolarBodyImageRepository;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author peter
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/test-mongoDb-datasource.xml","classpath:/spring/test-jdbc-datasource.xml","classpath:/spring/application-db.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SolarBodyImageRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(SolarBodyImageRepositoryTest.class);

    @Autowired
    private SolarBodyImageRepository imageRepository;

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#count()}.
     */
    @Test
    public void testCount() {
        Assert.assertTrue("image count", imageRepository.count() > 5);
    }

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}.
     */
    @Test
    public void testExists() {
        Assert.assertTrue( "Sun image exists", imageRepository.exists(DbConstants.SUN_ID));
    }
    
    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}.
     */
    @Test
    public void testNotExists() {
        Assert.assertFalse( "Image not exists", imageRepository.exists(Long.MIN_VALUE));
    }
    

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)}.
     */
    @Test
    public void testFindOne() {
        SolarBodyImage image = imageRepository.findOne(DbConstants.SUN_ID);
        Assert.assertNotNull(image);
        Assert.assertNotNull("not null image", image.getImage());
        Assert.assertTrue("image length", image.getImage().length > 1024);
        Assert.assertEquals( "image filename", "sun.jpg", image.getFilename());
        Assert.assertEquals( "image contentType", MediaType.IMAGE_JPEG_VALUE, image.getContentType());
        log.info("Sun: " + image);
        log.info("Sun: " + image.getSolarBody());
        SolarBody sun = image.getSolarBody();
        Assert.assertNotNull(sun);
        Assert.assertEquals(DbConstants.SUN_ID, sun.getId());

        assertNull("No image return null", imageRepository.findOne(Long.MIN_VALUE));
    }

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)}.
     */
    @Test
    public void testFindNone() {
        SolarBodyImage image = imageRepository.findOne(Long.MIN_VALUE);
        Assert.assertNull(image);
    }

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#delete(java.io.Serializable)}.
     */
    @Test
    public void testDeleteAndInsert() {
        SolarBodyImage image = imageRepository.findOne(DbConstants.MARS_ID);
        Assert.assertNotNull(image);
        Assert.assertNotEquals(DbConstants.NEW_IMAGE,image.getImage());

        imageRepository.delete(DbConstants.MARS_ID);
        image = imageRepository.findOne(DbConstants.MARS_ID);
        Assert.assertNull(image);
        
        SolarBodyImage newImage = new SolarBodyImage();
        newImage.setSolarBodyId(DbConstants.MARS_ID);
        newImage.setImage(DbConstants.NEW_IMAGE);
        newImage.setFilename("newImage.png");
        newImage.setContentType(MediaType.IMAGE_PNG_VALUE);

        imageRepository.save(newImage);
        
        image = imageRepository.findOne(DbConstants.MARS_ID);
        Assert.assertNotNull(image);
        Assert.assertEquals(DbConstants.NEW_IMAGE,image.getImage());
        Assert.assertEquals( "image filename", "newImage.png", image.getFilename());
        Assert.assertEquals( "image contentType", MediaType.IMAGE_PNG_VALUE, image.getContentType());
    }

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#delete(java.lang.Object)}.
     */
    @Test
    public void testSave() {
        SolarBodyImage image = imageRepository.findOne(DbConstants.NEPTUNE_ID);
        Assert.assertNotNull(image);
        Assert.assertNotEquals(DbConstants.NEW_IMAGE,image.getImage());

        image.setImage(DbConstants.NEW_IMAGE);
        image.setFilename("newImage2.gif");
        image.setContentType(MediaType.IMAGE_GIF_VALUE);
        imageRepository.save(image);
        
        SolarBodyImage savedImage = imageRepository.findOne(DbConstants.NEPTUNE_ID);
        Assert.assertNotNull(savedImage);
        Assert.assertEquals(DbConstants.NEW_IMAGE,savedImage.getImage());
        Assert.assertEquals( "image filename", "newImage2.gif", image.getFilename());
        Assert.assertEquals( "image contentType", MediaType.IMAGE_GIF_VALUE, image.getContentType());
    }


}
