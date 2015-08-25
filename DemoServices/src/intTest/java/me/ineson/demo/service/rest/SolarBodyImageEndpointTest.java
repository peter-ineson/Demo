package me.ineson.demo.service.rest;

import javax.ws.rs.core.MediaType;

import me.ineson.demo.service.SolarBodyImage;
import me.ineson.demo.service.db.DbConstants;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Theories.class)
public class SolarBodyImageEndpointTest {
    @DataPoint public static MediaType FORMAT_JSON = MediaType.APPLICATION_JSON_TYPE;
    @DataPoint public static MediaType FORMAT_APPLICATION_XML = MediaType.APPLICATION_XML_TYPE;
    @DataPoint public static MediaType FORMAT_TEXT_XML = MediaType.TEXT_XML_TYPE;

    private static Logger log = LoggerFactory.getLogger(SolarBodyImageEndpointTest.class);
    
    private final static String HOST_URL = "http://localhost:8080/DemoServices/rest";

    @Theory
    public void testFindById(MediaType mediaType) {
        SolarBodyImageRestClient client = new SolarBodyImageRestClient(mediaType);
        SolarBodyImage image = client.findById(HOST_URL,DbConstants.SUN_ID);

        log.info("Solar body Image: " + image);
        log.info("Solar body Image filename: " + image.getFilename());
        Assert.assertNotNull("sun image", image);
        Assert.assertEquals("image filename", "sun.png", image.getFilename());
        Assert.assertEquals("image contentType", org.springframework.http.MediaType.IMAGE_PNG_VALUE, image.getContentType());
    }

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}.
     */
    @Theory
    public void testFindNone(MediaType mediaType) {
        SolarBodyImageRestClient client = new SolarBodyImageRestClient(mediaType);
        SolarBodyImage image = client.findById(HOST_URL,Long.MIN_VALUE);

        Assert.assertNull("does not exist", image);
    }    

}
