package me.ineson.demo.app.accTest.page;

import static org.assertj.core.api.StrictAssertions.assertThat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;

import me.ineson.demo.service.db.domain.SolarBody;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("http://localhost:8080/DemoApp")
public class HomePage extends PageObject {

	private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    private final static Splitter MENU_OPTION_SPLITTER = Splitter.on("->");

    @FindBy(id="securityUserName")
    WebElementFacade nameOfUserElement;

    @FindBy(id="cssmenu")
    WebElementFacade mainMenu;
    
    @FindBy(id="menuOption_username")
    WebElementFacade menuOptionUsername;

	@FindBy(id="menuOption_login")
    WebElementFacade menuOptionLogin;

	@FindBy(id="menuOption_logout")
    WebElementFacade menuOptionLogout;

    public String getNameOfTheCurrentUser() {
        return nameOfUserElement.getText();
    }

    public void checkMenuOptions( Iterable<String>checkMenuOptions, boolean hasOption) {
        navigateMenuOptions( checkMenuOptions, hasOption, false);
    }

    public void navigateMenuOptions( Iterable<String>checkMenuOptions, boolean hasOption, boolean selectIfFound) {
        List<WebElement> menuOptions = null;
        StringBuffer menuNavigation = new StringBuffer("Root->");

        Iterator<String> menuIterator = checkMenuOptions.iterator();
        while(menuIterator.hasNext()) {
            String optionToCheck = menuIterator.next();
            if( menuOptions == null) {
                if("Login".equals(optionToCheck)) {
                    assertThat( menuOptionLogin.isPresent()).as( "Login menu option, should exist " + hasOption).isEqualTo(hasOption);
                    if( hasOption && selectIfFound) {
                        menuOptionLogin.click();
                    }
                    return;
                } else if ("Logout".equals(optionToCheck)) {
                    assertThat( menuOptionLogout.isPresent()).as( "Logout menu option, should exist " + hasOption).isEqualTo(hasOption);
                    if( hasOption && selectIfFound) {
                    	menuOptionLogout.click();
                    }
                    return;
                } else {
                    menuOptions = mainMenu.findElements(By.cssSelector("ul > li"));
                }
            }
            
            log.info( "optionToCheck: {}", optionToCheck);
            log.info( "menuOptions: {}", menuOptions);

            Map<String, WebElement> foundOptions = new HashMap<String, WebElement>();
            if (menuOptions != null) {
                for (WebElement menuOption : menuOptions) {
                    log.info( "menuOption: {}", menuOption);
                    log.info( "menuOption text: {}", menuOption.getText());
                    try {
                        WebElement menuOptionSpan = menuOption.findElement(By.cssSelector("span"));
                        log.info( "menuOptionSpan: {}", menuOptionSpan);
                        log.info( "menuOptionSpan text: {}", menuOptionSpan.getText());
                        assertThat( menuOptionSpan).as( "Look up menu text from " + menuOption).isNotNull();
                        foundOptions.put( menuOptionSpan.getText(), menuOption);
                    } catch( NoSuchElementException e) {
                    }
                }
            }
            
            WebElement foundMenuOption = foundOptions.get(optionToCheck);
            
            log.info( "Lookup optionToCheck: {} found {}", optionToCheck, foundMenuOption);

            if (!menuIterator.hasNext()) {
                if (hasOption) {
                    assertThat(optionToCheck).as("Failed to menu option '{}' find under: {}",
                            optionToCheck, menuNavigation).isIn(foundOptions.keySet());
                    if( selectIfFound) {
                    	WebElement link = foundMenuOption.findElement(By.tagName( "a"));
                    	if( link != null) {
                        	link.click();
                    	} else {
                    		foundMenuOption.click();
                    	}
                    }
                } else {
                    assertThat(optionToCheck).as("Should have not found menu option '{}' find under: {}",
                                    optionToCheck, menuNavigation).isNotIn(foundOptions.keySet());
                }
                return;
            }
            
            if( foundMenuOption == null) {
                if( hasOption) {
                    assertThat(optionToCheck).as("Failed to menu option '{}' find under: {}",
                            optionToCheck, menuNavigation).isIn(foundOptions.keySet());
                }
 
                return;
            }

            menuNavigation.append(optionToCheck).append("->");
            foundMenuOption.click();
            menuOptions = foundMenuOption.findElements(By.cssSelector("ul > li"));
        }
        
    }

    public void logout() {
        assertThat( menuOptionLogout.isPresent()).as( "Logout menu option exists").isEqualTo(true);
        menuOptionUsername.click();
        menuOptionLogout.click();
    }

    
    public static Iterable<String> parseMenuOptions( String options) {
    	if( StringUtils.isBlank( options)) {
    		return Collections.emptyList();
    	}
        return MENU_OPTION_SPLITTER.split(options);
    }

	public void selectIconForSolarBody(SolarBody solarBody) {
		String iconId = "icon_solarBody_" + solarBody.getId();
		WebElementFacade icon = element(By.id(iconId));
        assertThat( icon).as( "Icon for " + solarBody.getName() + " (id" + iconId + ") exists").isNotNull();
        assertThat( icon.isVisible())
            .as( "Icon for " + solarBody.getName() + " (id" + iconId + ") exists")
            .isEqualTo(true);

        icon.click();
	}
 

}
