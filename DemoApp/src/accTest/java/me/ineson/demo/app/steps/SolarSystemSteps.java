package me.ineson.demo.app.steps;

import me.ineson.demo.app.page.Pages;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class SolarSystemSteps {

    private final Pages pages;

    public SolarSystemSteps(Pages pages) {
        this.pages = pages;
    }

    @Given("open browser")
    public void userIsOnHomePage() {
        pages.home().open();
    }

    @When("browser dashboard displayed")
    public void homePageLoaded() {
        pages.home().dashBoardLoaded();
    }

    @Then("user is $usersName")
    public void checkLoginUser(String usersName) {
        pages.home().loginUserName(usersName);
    } 
}
