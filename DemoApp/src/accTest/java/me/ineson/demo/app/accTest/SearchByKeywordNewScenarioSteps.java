package me.ineson.demo.app.accTest;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thucydides.core.annotations.Steps;


public class SearchByKeywordNewScenarioSteps {
    
    private static final Logger log = LoggerFactory.getLogger(SearchByKeywordNewScenarioSteps.class);

	@Steps
    BuyerSteps buyer;

    @Given("open browser")
    public void buyerWantsToBuy() {
        buyer.opens_etsy_home_page();
    }

    @When("browser dashboard displayed")
    public void searchByKeyword() {
        //buyer.searches_for_items_containing(keyword);
    }

    @Then("user is $username")
    public void checkLoginUser(String username) {
        log.info("checing that the user is " + username);
        //pages.home().loginUserName(username);
    }

    @Then("menu does have $menuItem option")
    public void checkMenuOptionExists(String menuItem) {
//        pages.home().hasloginUserName(usersName);
    }

    @Then("menu does not have $menuItem option")
    public void checkMenuOptionNotExists(String menuItem) {
//        pages.home().hasloginUserName(usersName);
    }
}

