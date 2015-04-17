package me.ineson.demo.app.accTest;

import net.serenitybdd.cucumber.CucumberWithSerenity;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features="src/accTest/features/search/search_by_keyword.feature")
public class SearchByKeyword {

}
