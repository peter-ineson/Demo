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
package me.ineson.demo.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import me.ineson.testing.utils.GradleConfig;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

/**
 * @author peter
 *
 */
public class ImageControllerTest {

    private static Logger log = LoggerFactory.getLogger(ImageControllerTest.class);

    private final static String HOST_URL = GradleConfig.getProperty("DemoApp.intTest.appUrl");

    @Test
    public void testGetSunImage() throws IOException {
        
        String url = HOST_URL + "/solarBodies/" + DbConstants.SUN_ID + "/image";

        log.info("Getting sun image from url: " + url);
        URL imageUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();

        Assert.assertEquals( "found response", HttpURLConnection.HTTP_OK, connection.getResponseCode());
        try( InputStream ip = connection.getInputStream()) {
            int imageSize = connection.getContentLength();
            Assert.assertTrue( "found size", (imageSize > 1024));
            byte content[] = IOUtils.toByteArray( ip);
            Assert.assertNotNull( "content", content);
            Assert.assertEquals( "content size", imageSize, content.length);
            Assert.assertEquals( "content type", MediaType.IMAGE_JPEG_VALUE, connection.getContentType());
        }
    }

    @Test
    public void testGetImageDoesNotExist() throws IOException {
        
        String url = HOST_URL + "/solarBodies/" + Long.MIN_VALUE + "/image";

        log.info("Failed to find image image from url: " + url);
        URL imageUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();

        Assert.assertEquals( "Not found response", HttpURLConnection.HTTP_NOT_FOUND, connection.getResponseCode());
    }
}
