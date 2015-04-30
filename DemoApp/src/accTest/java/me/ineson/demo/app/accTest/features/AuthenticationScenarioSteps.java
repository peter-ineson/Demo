package me.ineson.demo.app.accTest.features;

import static org.assertj.core.api.Assertions.assertThat;
import me.ineson.demo.app.accTest.steps.LandingPageSteps;
import net.thucydides.core.annotations.Steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;

import cucumber.api.Pending;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AuthenticationScenarioSteps {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationScenarioSteps.class);

	@Steps
    LandingPageSteps landingPageSteps;

    @Given("^user accesses the web site$")
    public void openLandingPage() {
        landingPageSteps.openHomePage();
    }

    @When("^does not login$")
    public void nopStop() {
    }

    @When("^login as (.*) user with correct password$")
    public void loginAsUser(String username) {
        landingPageSteps.loginAsUser(username);
    }

    @Then("^should be (.*) user$")
    public void checkTheCurrentUser(String user) {
        landingPageSteps.checkCurrentUser(user);
    }


    private final static Splitter MENU_OPTION_SPLITTER = Splitter.on("->");
 
    //the main menu does not have a Edit option
    @Then("^the main menu (.*) have a (.*) option$")
    public void checkMenuForOptions(String condition, String options) {
        assertThat( condition).isIn("does", "does not");
        boolean hasOption = "does".equals(condition);
        
        assertThat( options).isNotEmpty();

        Iterable<String>menuOptions = MENU_OPTION_SPLITTER.split(options);
        landingPageSteps.checkForMenuOptions(hasOption, menuOptions);
    }

    
    
    @When("^I search for items containing '(.*)'$")
    public void searchByKeyword(String keyword) {
        landingPageSteps.searches_for_items_containing(keyword);
    }

    
    
    @Then("^I should only see items related to '(.*)'$")
    public void resultsForACategoryAndKeywordInARegion(String keyword) {
        landingPageSteps.should_see_items_related_to(keyword);
    }
}
