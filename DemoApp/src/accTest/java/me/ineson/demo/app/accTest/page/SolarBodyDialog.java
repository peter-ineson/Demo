package me.ineson.demo.app.accTest.page;

import static org.assertj.core.api.Assertions.assertThat;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.ineson.demo.service.db.domain.SolarBody;


public class SolarBodyDialog extends PageObject {

	private static final Logger log = LoggerFactory.getLogger(SolarBodyDialog.class);
	
    @FindBy(id="solarBody-dialog")
    WebElementFacade dialog;

    @FindBy(id="menuOption_username")
    WebElementFacade menuOptionUsername;

	@FindBy(id="menuOption_login")
    WebElementFacade menuOptionLogin;

    @FindBy(id="login-dialog-button-login")
    WebElementFacade loginButton;

    public void isDialogOpen() {
    	
    	waitFor( ExpectedConditions.visibilityOfElementLocated( By.id("solarBody-dialog")));
        Serenity.takeScreenshot();
    }

    public void validateDialogContent( SolarBody solarBody, boolean editMode) {
        WebElementFacade name = dialog.find(By.id("solarBodyForm-name"));
        assertThat( name).as( "name field exists").isNotNull();
        assertThat( name.getValue()).as( "solar body name").isEqualTo( solarBody.getName());
    }


    public void submitLogin() {
        assertThat( loginButton.isVisible()).as( "Login button visable").isEqualTo(true);
        loginButton.click();
    }


    public String getErrorMessage() {
        WebElement loginErrorMessage = dialog.waitForCondition().until( ExpectedConditions.presenceOfElementLocated(By.className("ui-state-error-text")));
        String errorMessage = loginErrorMessage.getText();
        log.info("error message {}", errorMessage);
        return errorMessage;
    }

}
