package me.ineson.demo.app.accTest.features;

import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.thucydides.core.annotations.Managed;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import cucumber.api.CucumberOptions;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features={
//        "src/accTest/features/landingPage/authentication.feature"
        "src/accTest/features/singleFeature.feature"
})
public class FeatureRunner {
    
    @Managed(uniqueSession=true)                                                               
    WebDriver driver;

}
