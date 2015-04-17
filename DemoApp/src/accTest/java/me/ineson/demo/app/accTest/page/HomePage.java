package me.ineson.demo.app.accTest.page;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DefaultUrl("http://localhost:8080/DemoApp")
public class HomePage extends PageObject {

	private static final Logger log = LoggerFactory.getLogger(HomePage.class);
	
    @FindBy(id="menuOption_username")
    WebElementFacade menuOptionUsername;

	@FindBy(id="menuOption_login")
    WebElementFacade menuOptionLogin;

	@FindBy(id="menuOption_logout")
    WebElementFacade menuOptionLogout;

	
    public void loginApplication() {
		log.info("frontpage application username "
		        + menuOptionUsername.isPresent() + ":" + menuOptionUsername.isCurrentlyEnabled() + ":" + menuOptionUsername.isCurrentlyVisible()
		        + " login " + menuOptionLogin.isPresent() + ":" + menuOptionLogin.isCurrentlyEnabled() + ":" + menuOptionLogin.isCurrentlyVisible());

        Serenity.takeScreenshot();
        menuOptionUsername.click();
        waitABit(500);

        Serenity.takeScreenshot();
        log.info("Login application username "
                + menuOptionUsername.isPresent() + ":" + menuOptionUsername.isCurrentlyEnabled() + ":" + menuOptionUsername.isCurrentlyVisible()
                + " login " + menuOptionLogin.isPresent() + ":" + menuOptionLogin.isCurrentlyEnabled() + ":" + menuOptionLogin.isCurrentlyVisible());

    	menuOptionLogin.click();
    	waitABit(500);
    }

}
