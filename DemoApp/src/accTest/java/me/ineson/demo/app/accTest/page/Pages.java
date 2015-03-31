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
package me.ineson.demo.app.accTest.page;

import javax.inject.Inject;
import javax.inject.Named;

import org.jbehave.web.selenium.WebDriverProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author peter
 *
 */
@Named
public class Pages {
    
    private static final Logger log = LoggerFactory.getLogger(Pages.class);
    
    private WebDriverProvider driverProvider;
    private boolean driverInitialised = false;
    
    @Inject
    private Home home;
 
    @Inject
    public Pages(WebDriverProvider driverProvider) {
        log.info( "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDd " + driverProvider);
        this.driverProvider = driverProvider;
    }
 
    public Home home(){
        /*
        if ( home == null ){
            home = new Home(driverProvider);
        }
        if( !driverInitialised) {
            driverProvider.initialize();
            driverInitialised = true;
        }
        */
        return home;
    }
 
}
