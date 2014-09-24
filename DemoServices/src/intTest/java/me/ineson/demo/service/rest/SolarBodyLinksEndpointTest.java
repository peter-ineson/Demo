package me.ineson.demo.service.rest;

import java.util.List;

import javax.ws.rs.core.MediaType;

import me.ineson.demo.service.SolarBodyLink;
import me.ineson.demo.service.db.DbConstants;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Theories.class)
public class SolarBodyLinksEndpointTest {
    @DataPoint public static MediaType FORMAT_JSON = MediaType.APPLICATION_JSON_TYPE;
    @DataPoint public static MediaType FORMAT_APPLICATION_XML = MediaType.APPLICATION_XML_TYPE;
    @DataPoint public static MediaType FORMAT_TEXT_XML = MediaType.TEXT_XML_TYPE;

    private static Logger log = LoggerFactory.getLogger(SolarBodyLinksEndpointTest.class);
    
    private final static String HOST_URL = "http://localhost:8080/DemoServices/rest";

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}.
     */
    @Theory
    public void testFindNone(MediaType mediaType) {
        log.info( "SolarBodyLinkRestClient( {} )", mediaType);
        SolarBodyLinkRestClient client = new SolarBodyLinkRestClient(mediaType);
        List<SolarBodyLink> links = client.findBySolarBody(HOST_URL,Long.MIN_VALUE);

        Assert.assertNotNull("does not exist", links);
        Assert.assertEquals("does not exist count", 0, links.size());
    }    

    /**
     * Test method for {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}.
     */
    @Theory
    public void testFindSun(MediaType mediaType) {
        SolarBodyLinkRestClient client = new SolarBodyLinkRestClient(mediaType);
        List<SolarBodyLink> links = client.findBySolarBody(HOST_URL,DbConstants.SUN_ID);

        Assert.assertNotNull("Link for Sun", links);
        Assert.assertEquals("does not exist count", 2, links.size());
    }    

}
