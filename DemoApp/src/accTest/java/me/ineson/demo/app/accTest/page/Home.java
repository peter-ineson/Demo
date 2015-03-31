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

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import me.ineson.demo.service.db.domain.User;
import me.ineson.demo.service.db.repo.jpa.UserRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.jbehave.web.selenium.WebDriverProvider;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author peter
 *
 */
@Named
public class Home extends AbstractPage {
    
    private static final Logger log = LoggerFactory.getLogger( Home.class);
    
    @Inject
    private UserRepository userRepository;
        
    @Inject
    public Home(WebDriverProvider driverProvider) {
        super(driverProvider);
        log.info("############################### home");
    }

    public void open() {
        log.info("############################### userRepository " + userRepository);
        
        get("http://localhost:8080/DemoApp");
        manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        log.info("############################### open 3");
    }

    public void dashBoardLoaded() {
        WebElement element = findElement(By.id("cssmenu"));
        if (element == null) {
            Assert.fail("dashboard not loaded, main menu not loaded.");
        }
    }

    public void loginUserName(String username) {
        List<WebElement> elements = findElements(By.id("securityUserName"));
        if (CollectionUtils.isEmpty(elements)) {
            Assert.fail("User's name not found.");
        } else if (elements.size() > 1) {
            log.error("Too many User's name not found." + elements);
            Assert.fail("Too many User's name not found.");
        }
        WebElement element = elements.get(0);
        
        // Now lookup the user
        User user = userRepository.findOne(username);
        log.info("############################### user: " + user);
        log.info("############################### user: " + user.getUsername());
        log.info("############################### user: " + user.getName());
        Assert.assertNotNull("Failed to find user with username " + username, user);
        
        Assert.assertEquals("User's name mismatch.", user.getName(), element.getText());
    }

    public void find(String step) {
        findElement(By.name("matchingStep")).sendKeys(step);
        findElement(By.name("findButton")).click();
    }
}
