package me.ineson.demo.app.accTest.page;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DefaultUrl("http://localhost:8080/DemoApp")
public class HomePage extends PageObject {

	private static final Logger log = LoggerFactory.getLogger(HomePage.class);
	
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

    public void checkMenuOptions( boolean hasOption, Iterable<String>checkMenuOptions) {
        List<WebElement> menuOptions = null;
        StringBuffer menuNavigation = new StringBuffer("Root->");

        Iterator<String> menuIterator = checkMenuOptions.iterator();
        while(menuIterator.hasNext()) {
            String optionToCheck = menuIterator.next();
            if( menuOptions == null) {
                if("Login".equals(optionToCheck)) {
                    assertThat( menuOptionLogin.isPresent()).as( "Login menu option, should exist " + hasOption).isEqualTo(hasOption);
                    return;
                } else if ("Logout".equals(optionToCheck)) {
                    assertThat( menuOptionLogout.isPresent()).as( "Logout menu option, should exist " + hasOption).isEqualTo(hasOption);
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
            

            if (!menuIterator.hasNext()) {
                if (hasOption) {
                    assertThat(optionToCheck).as("Failed to menu option '{}' find under: {}",
                            optionToCheck, menuNavigation).isIn(foundOptions.keySet());
                } else {
                    assertThat(optionToCheck).as("Should have not found menu option '{}' find under: {}",
                                    optionToCheck, menuNavigation).isNotIn(foundOptions.keySet());
                }
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

}
