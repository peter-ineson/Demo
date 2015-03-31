package me.ineson.demo.app.accTest.steps;

import javax.inject.Inject;
import javax.inject.Named;

import me.ineson.demo.app.accTest.page.Pages;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

@Named
public class SolarSystemSteps {

    @Inject
    private Pages pages;
    
    @Given("open browser")
    public void userIsOnHomePage() {
        pages.home().open();
    }

    @Given("open dashboard")
    public void openDashboard() {
        // TODO: pages.home().open();
    }

    @Given("logged in as user $username")
    public void openDashboardAsUser( String username) {
        // TODO: pages.home().open();
    }

    @When("browser dashboard displayed")
    public void homePageLoaded() {
        pages.home().dashBoardLoaded();
    }

    @When("login as user $username")
    public void homePageLoaded( String username) {
        // TODO:  pages.home().dashBoardLoaded();
    }

    @When("select logout")
    public void performLogout() {
        // TODO:  pages.home().dashBoardLoaded();
    }

    @Then("user is $username")
    public void checkLoginUser(String username) {
        pages.home().loginUserName(username);
    }
    
    @Then("menu does have $menuItem option")
    public void checkMenuOptionExists(String menuItem) {
//        pages.home().hasloginUserName(usersName);
    }

    @Then("menu does not have $menuItem option")
    public void checkMenuOptionNotExists(String menuItem) {
//        pages.home().hasloginUserName(usersName);
    }
/*
    Then menu does not have Edit option
    Then menu does not have Logout option
    Then menu does have Login Option

    Given open dashboard
    Then menu does have Edit Option

    Given logged in as user admin
    When logout
    Then menu does not have Edit Option
  */  
}
