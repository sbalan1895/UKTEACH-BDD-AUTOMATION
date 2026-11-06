package com.ukteachstepdefinition;

import com.pomjobsearch.POM_JOBSEARCH;
import com.ukteachbase.Base_JobSearch;
import com.ukteachbase.ScreenshotUtil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class JobSearch_Steps extends Base_JobSearch {

	POM_JOBSEARCH jobSearch;
	//	ScreenshotUtil screenShot = new ScreenshotUtil();

	@Given("User navigate to UK-Teach site")
	public void user_navigate_to_uk_teach_site() {


		jobSearch = new POM_JOBSEARCH(driver);

		jobSearch.navigateToUKTeach();
		jobSearch.enterPassCode("Tal-UKTLive@2026!");
		jobSearch.handleCookiesPopup();
	}

	@When("User enters a keyWord in the job searchbox")
	public void user_enters_a_key_word_in_the_job_searchbox() {

		jobSearch.enterKeyword("SB Educational Institute");
		jobSearch.clkSearchBtn();
		jobSearch.getJobCount();

	}

	@When("User retives the search results")
	public void user_retives_the_search_results() {

		jobSearch.getTopJob();

		//    throw new io.cucumber.java.PendingException();
	}

	@Then("User should see jobs relevant to the searched keyword")
	public void user_should_see_jobs_relevant_to_the_searched_keyword() {

		jobSearch.getJobList();
	}


}
