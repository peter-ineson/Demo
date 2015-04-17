package me.ineson.demo.app.accTest;

import me.ineson.demo.app.accTest.page.HomePage;
import net.thucydides.core.annotations.Step;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuyerSteps {
	
	private static final Logger log = LoggerFactory.getLogger(BuyerSteps.class);

	HomePage homePage;
	
	@Step
	public void opens_etsy_home_page() {
		log.info("Display home page");
		homePage.open();
		homePage.loginApplication();
		
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
