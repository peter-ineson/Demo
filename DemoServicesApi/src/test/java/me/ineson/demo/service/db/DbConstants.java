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
package me.ineson.demo.service.db;

import java.math.BigDecimal;

/**
 * @author peter
 *
 */
public interface DbConstants {
    
    static final Long SUN_ID = 1L;

    static final Long MERCURY_ID = 2L;

    static final Long EARTH_ID = 4L;
    
    static final Long MARS_ID = 5L;
    static final String MARS_NAME = "Mars";
    static final Long MARS_LINK_WIKI_ID = 10L;
    static final String MARS_LINK_WIKI_URL = "http://en.wikipedia.org/wiki/Mars";
    static final Long MARS_LINK_GOOGLE_ID = 12L;

    static final Long JUPITER_ID = 6L;
    static final String JUPITER_NAME = "Jupiter";

    static final Long NEPTUNE_ID = 9L;
    static final Long NEPTURE_LINK_NASA_ID = 23L;

    static final String NEW_NAME = "testName";
//    static final SolarBodyType NEW_TYPE = SolarBodyType.Planet;
    static final String NEW_DESC = "testDescription";
    static final Long NEW_ORBIT_DISTANCE = 55L;
    static final Long NEW_RADIUS = 66L;
    static final BigDecimal NEW_MASS = new BigDecimal( 666L);
    static final byte NEW_IMAGE[] = "dummy image".getBytes();

    static final String NEW_LINK_NAME = "newlink";
    static final String NEW_LINK_URL = "http://blah";


}
