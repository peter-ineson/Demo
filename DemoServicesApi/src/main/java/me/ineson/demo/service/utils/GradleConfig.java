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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import me.ineson.demo.service.rest.SolarBodyRestClient;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author peter
 *
 */
public class GradleConfig {

    private static final Logger log = LoggerFactory.getLogger(SolarBodyRestClient.class);

    private static Properties GRADLE_PROPERTIES = null;
    
    public static synchronized String getProperty(String key) {
        log.trace( "key {}", key );    
        String value = System.getProperty(key);
        
        if( value == null) {
            value = readGradleConfig(key);
            log.trace( "From gradle.properties key {}, value {}", key,value);    
        }

        log.trace( "key {}, value {}", key,value);    
        return value;
    }
    
    private static synchronized String readGradleConfig( String key) {
        if( GRADLE_PROPERTIES == null) {
            File filename = new File( SystemUtils.getUserHome(), ".gradle/gradle.properties");
            if( ! filename.exists() || ! filename.isFile() || ! filename.canRead()) {
                throw new IllegalStateException( "Failed to access gradle configuration: " + filename.getAbsolutePath()
                        + ", exists " + filename.exists() + ", isFile " + filename.isFile() + ", canRead " + filename.canRead());
            }
            Properties properties = new Properties();
            try {
                properties.load( new FileInputStream( filename));
            } catch (FileNotFoundException e) {
                throw new IllegalStateException( "Failed to access gradle configuration: " + filename.getAbsolutePath(), e);
            } catch (IOException e) {
                throw new IllegalStateException( "Failed to access gradle configuration: " + filename.getAbsolutePath(), e);
            }
            GRADLE_PROPERTIES = properties;
        }
        
        String fullKey = "systemProp." + key;
        String value = GRADLE_PROPERTIES.getProperty(fullKey);
        
        if( value != null) {
            System.setProperty( key, value);
        }
        
        return value;
    }
}
