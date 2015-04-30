package me.ineson.demo.app.accTest.steps;

import static org.assertj.core.api.Assertions.assertThat;
import me.ineson.demo.app.accTest.page.HomePage;
import me.ineson.demo.app.accTest.page.LoginDialog;
import me.ineson.demo.service.db.domain.User;
import me.ineson.demo.service.db.repo.jpa.UserRepository;
import net.thucydides.core.annotations.Step;
import net.thucydides.junit.spring.SpringIntegration;

import org.junit.Assert;
import org.junit.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = "classpath:/spring/accTest-config.xml")
public class LandingPageSteps {
	
	private static final Logger log = LoggerFactory.getLogger(LandingPageSteps.class);

	HomePage homePage;

    LoginDialog loginDialog;

	@Rule
    public SpringIntegration springIntegration = new SpringIntegration();
	
	@Autowired
	private UserRepository userRepository;
	
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
    public void checkForMenuOptions(boolean hasOption, Iterable<String> menuOptions) {
        homePage.checkMenuOptions(hasOption, menuOptions);
    }
	
    @Step
    public void loginAsUser(String username) {
        log.info("Login as user {}", username);
        User user = userRepository.findOne( username);
        assertThat( user).as("Lookup of user " + username).isNotNull();
        loginDialog.openDialog();
        loginDialog.enterCredentials(username, user.getPassword());
        loginDialog.submitLogin();
        
    }
    
    
    
	@Step
	public void searches_for_items_containing(String keyword) {
		log.info("Searching for " + keyword);
		
	}

	@Step
	public void should_see_items_related_to(String keyword) {
		log.info("Should have found " + keyword);
		Assert.assertEquals( "wool", keyword);
		
	}



}
