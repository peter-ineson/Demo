package me.ineson.demo.app.accTest.stories;

import static org.assertj.core.api.StrictAssertions.assertThat;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.runner.RunWith;

import me.ineson.demo.app.accTest.page.HomePage;
import me.ineson.demo.app.accTest.steps.LandingPageSteps;
import me.ineson.demo.app.accTest.steps.SolarBodyDialogSteps;
import net.serenitybdd.jbehave.SerenityStory;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Steps;

@RunWith(SerenityRunner.class)
public class ViewPlanetDetails extends SerenityStory {

	@Steps
    private LandingPageSteps landingPageSteps;

	@Steps
    private SolarBodyDialogSteps solarBodyDialogSteps;

    @Given("open landing page as $username")
    public void buyerWantsToBuy( String username) {
    	landingPageSteps.openHomePage();
    	landingPageSteps.checkCurrentUser( username);
    }
    
    @When("select $menuOption from the menu")
    public void selectMenuOption( String menuOption) {
    	landingPageSteps.selectMenuOption( HomePage.parseMenuOptions(menuOption));
    }

    @When("clicking on $solarBodyName icon on the home page")
    public void clickSolarBodyIcon( String solarBodyName) {
    	landingPageSteps.selectIconForSolarBody( solarBodyName);
    }

    @Then("planet $mode page for $solarBody is displayed")
    public void viewSolarBody( String mode, String solarBodyName) {

        assertThat( mode).isIn("edit", "view");
        boolean editMode = "edit".equals(mode);

    	solarBodyDialogSteps.checkDialogContent(solarBodyName, editMode);;
    }
    
     
}
