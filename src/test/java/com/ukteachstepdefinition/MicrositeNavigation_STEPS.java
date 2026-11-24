package com.ukteachstepdefinition;

import java.util.List;

import org.junit.Assert;

import com.pomjobsearch.POM_JOBSEARCH;
import com.pomjobsearch.POM_MicroHomePage;
import com.pomjobsearch.POM_MicrositeNavig;
import com.pomjobsearch.POM_MicrositeNavig.locInfo;
import com.ukteachbase.Base_JobSearch;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MicrositeNavigation_STEPS extends Base_JobSearch{

	POM_MicrositeNavig microN;
	POM_JOBSEARCH jobSearch;
	POM_MicroHomePage mcHomePage;


	@Given("the user navigates to the UK-Teach website")
	public void the_user_navigates_to_the_uk_teach_website() {

		jobSearch = new POM_JOBSEARCH(driver);
		jobSearch.navigateToUKTeach();
		jobSearch.enterPassCode("Tal-UKTLive@2026!");
		jobSearch.handleCookiesPopup();

		microN = new POM_MicrositeNavig(driver);
		//	microN.getHeaderMainNavi();

		List<String> options = microN.getHeaderMainNavi();
		Assert.assertTrue(options.contains("ABOUT US"));

	}

	@When("the user selects a county from the Location section")
	public void the_user_selects_a_county_from_the_location_section() {

		List<String> locations = microN.getLocations();
		List<locInfo> navigateAllLocation = microN.navigateAllLocation();

		Assert.assertFalse("No locations found", navigateAllLocation.isEmpty());

		for (POM_MicrositeNavig.locInfo p : navigateAllLocation) {
			
			Assert.assertFalse("Title empty for " + p.getName(), p.getTitle().isBlank());
			Assert.assertFalse("URL empty for " + p.getName(), p.getURL().isBlank());
		}

	}

	@Then("the respective County Microsite page should open successfully")
	public void the_respective_county_microsite_page_should_open_successfully() {
		mcHomePage = new POM_MicroHomePage(driver);
		mcHomePage.findYourJobsPage();
		
	}

	@When("the user navigates to the Blogs page")
	public void the_user_navigates_to_the_blogs_page() {
		
		mcHomePage.blogsPage();

	}

	@Then("the Blogs page should be displayed successfully")
	public void the_blogs_page_should_be_displayed_successfully() {

		logPageDetails("Blogs");
	    Assert.assertFalse("Blogs title is empty", getPageTitle().isBlank());
	}

	@When("the user navigates to the About Us page")
	public void the_user_navigates_to_the_about_us_page() {
		
		mcHomePage.aboutUsPage();

	}

	@Then("the About Us page should be displayed successfully")
	public void the_about_us_page_should_be_displayed_successfully() {
		logPageDetails("AboutUs");
	    Assert.assertFalse("About Us title is empty", getPageTitle().isBlank());
	}

	@Then("the user should be able to navigate successfully across all Microsite pages")
	public void the_user_should_be_able_to_navigate_successfully_across_all_microsite_pages() {


	}



}
