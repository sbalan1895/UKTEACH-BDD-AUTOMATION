package com.ukteachstepdefinition;

import com.pomjobsearch.POM_JOBSEARCH;
import com.pomjobsearch.POM_JobDetails;
import com.ukteachbase.Base_JobSearch;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Test_JobDetails extends Base_JobSearch {

	POM_JOBSEARCH jobSearch;
	POM_JobDetails pomJobdetails;


	@When("User enters a JobName in the job searchbox")
	public void user_enters_a_job_name_in_the_job_searchbox() {

		jobSearch = new POM_JOBSEARCH(driver);

		jobSearch.enterKeyword("Moral Science");
		jobSearch.clkSearchBtn();

	}

	@Then("User should see jobs details of the searched job")
	public void user_should_see_jobs_details_of_the_searched_job() {

		pomJobdetails = new POM_JobDetails(driver);

		pomJobdetails.getJDJobSrch();

		pomJobdetails.clkJobCard();

		System.out.println("=== JobSearch page fetched job details ===");

		pomJobdetails.getJDjobDetails();
		
		System.out.println("=== JobDetails fetched job details ===");
		
		pomJobdetails.printJobDetails();
		pomJobdetails.validateJobDetails();

		System.out.println("=== JobDetails inside Job Summary fetched ===");
		
		
	}


}
