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

public class LoginDialog extends PageObject {

	private static final Logger log = LoggerFactory.getLogger(LoginDialog.class);
	
    @FindBy(id="login-dialog")
    WebElementFacade loginDialog;

    @FindBy(id="menuOption_username")
    WebElementFacade menuOptionUsername;

	@FindBy(id="menuOption_login")
    WebElementFacade menuOptionLogin;

    @FindBy(id="login-dialog-button-login")
    WebElementFacade loginButton;
		
    public void openDialog() {
		log.info("frontpage application username "
		        + menuOptionUsername.isPresent() + ":" + menuOptionUsername.isCurrentlyEnabled() + ":" + menuOptionUsername.isCurrentlyVisible()
		        + " login " + menuOptionLogin.isPresent() + ":" + menuOptionLogin.isCurrentlyEnabled() + ":" + menuOptionLogin.isCurrentlyVisible());
        assertThat( menuOptionLogin.isPresent()).as( "Login menu option exists").isEqualTo(true);

        //Serenity.takeScreenshot();
        menuOptionUsername.click();

        //Serenity.takeScreenshot();
        log.info("Login application username "
                + menuOptionUsername.isPresent() + ":" + menuOptionUsername.isCurrentlyEnabled() + ":" + menuOptionUsername.isCurrentlyVisible()
                + " login " + menuOptionLogin.isPresent() + ":" + menuOptionLogin.isCurrentlyEnabled() + ":" + menuOptionLogin.isCurrentlyVisible());

    	menuOptionLogin.click();
    	
    	waitFor( ExpectedConditions.visibilityOfElementLocated( By.id("login-dialog")));
        Serenity.takeScreenshot();
    }


    public void enterCredentials( String username, String password) {
        assertThat( loginDialog.isVisible()).as( "Login form visable").isEqualTo(true);

        WebElementFacade usernameInput = loginDialog.find(By.id("username"));
        assertThat( usernameInput).isNotNull();
        usernameInput.sendKeys(username);

        WebElementFacade passwordInput = loginDialog.find(By.id("password"));
        assertThat( passwordInput).isNotNull();
        passwordInput.sendKeys(password);
    }

    public void submitLogin() {
        assertThat( loginButton.isVisible()).as( "Login button visable").isEqualTo(true);
        loginButton.click();
    }


    public String getErrorMessage() {
        WebElement loginErrorMessage = loginDialog.waitForCondition().until( ExpectedConditions.presenceOfElementLocated(By.className("ui-state-error-text")));
        String errorMessage = loginErrorMessage.getText();
        log.info("error message {}", errorMessage);
        return errorMessage;
    }

}
