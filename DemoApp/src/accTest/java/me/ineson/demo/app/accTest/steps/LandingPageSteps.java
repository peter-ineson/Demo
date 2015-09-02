package me.ineson.demo.app.accTest.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import me.ineson.demo.app.accTest.page.HomePage;
import me.ineson.demo.app.accTest.page.LoginDialog;
import me.ineson.demo.service.db.domain.SolarBody;
import me.ineson.demo.service.db.domain.User;
import me.ineson.demo.service.db.repo.jpa.SolarBodyRepository;
import me.ineson.demo.service.db.repo.jpa.UserRepository;
import net.thucydides.core.annotations.Step;
import net.thucydides.junit.spring.SpringIntegration;

@ContextConfiguration(locations = "classpath:/spring/accTest-config.xml")
public class LandingPageSteps {
	
	private static final Logger log = LoggerFactory.getLogger(LandingPageSteps.class);

	HomePage homePage;

    LoginDialog loginDialog;

	@Rule
    public SpringIntegration springIntegration = new SpringIntegration();
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SolarBodyRepository solarBodyRepository;

	@Step
	public void openHomePage() {
		log.debug("Display home page");
		homePage.open();
	}

    @Step
    public void checkCurrentUser( String username) {
        log.info("Check the current user is {}", username);
        User user = userRepository.findOne( username);
        assertThat( user).as("Lookup of user " + username).isNotNull();

        assertThat( homePage.getNameOfTheCurrentUser()).isEqualTo( user.getName());
    }

    @Step
    public void checkForMenuOptions( Iterable<String> menuOptions, boolean hasOption) {
        homePage.checkMenuOptions( menuOptions, hasOption);
    }

    @Step
    public void selectMenuOption( Iterable<String> menuOptions) {
        homePage.navigateMenuOptions( menuOptions, true, true);
    }

    @Step
    public void loginAsUser(String username) {
        log.info("Login as user {} with correct password", username);
        User user = userRepository.findOne( username);
        assertThat( user).as("Lookup of user " + username).isNotNull();
        loginDialog.openDialog();
        loginDialog.enterCredentials(username, user.getPassword());
        loginDialog.submitLogin();
    }

    @Step
    public void loginWithNoPassword(String username) {
        log.info("Login as user {} no password", username);
        User user = userRepository.findOne( username);
        assertThat( user).as("Lookup of user " + username).isNotNull();
        loginDialog.openDialog();
        loginDialog.enterCredentials(username, StringUtils.EMPTY);
        loginDialog.submitLogin();
    }

    @Step
    public void loginWithIncorrectPassword(String username) {
        log.info("Login as user {} no password", username);
        User user = userRepository.findOne( username);
        assertThat( user).as("Lookup of user " + username).isNotNull();
        final String password = "XXXX";
        assertThat( password.equals( user.getPassword())).as("User passwords of " + username + " matches default").isFalse();
        loginDialog.openDialog();
        loginDialog.enterCredentials(username, password);
        loginDialog.submitLogin();
    }

    @Step
    public void logout() {
        homePage.logout();
    }

    @Step
    public void loginFailed() {
        loginDialog.getErrorMessage();
    }

    @Step
	public void selectIconForSolarBody(String solarBodyName) {
		log.debug("select icon for {}", solarBodyName);
		List<SolarBody>solarBodies = solarBodyRepository.findByNameIgnoreCase(solarBodyName);
		assertThat(solarBodies).isNotEmpty().hasSize(1);
		SolarBody solarBody = solarBodies.get(0);

        homePage.selectIconForSolarBody( solarBody);
	}
}
