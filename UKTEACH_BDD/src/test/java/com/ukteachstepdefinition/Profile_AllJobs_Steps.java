package com.ukteachstepdefinition;

import org.jspecify.annotations.Nullable;
import org.junit.Assert;

import com.pomjobsearch.POM_JOBSEARCH;
import com.pomjobsearch.POM_JobDetails;
import com.pomjobsearch.POM_Profile_AllJobs;
import com.ukteachbase.Base_JobSearch;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Profile_AllJobs_Steps extends Base_JobSearch{

	POM_JOBSEARCH pomsearch;
	POM_JobDetails pomjobdetails;
	POM_Profile_AllJobs pomProfileJ;

	/** Created on first use, so a step never runs against a null page object. */
	private POM_Profile_AllJobs profile() {
		if (pomProfileJ == null) {
			pomProfileJ = new POM_Profile_AllJobs(driver);
		}
		return pomProfileJ;
	}

	private void clickLink(String linkName) {

		if (linkName.equalsIgnoreCase("Profile")) {
			profile().clkProfl();
		} else if (linkName.equalsIgnoreCase("View All Jobs")) {
			profile().clkViewAllJobs();
		} else {
			throw new IllegalArgumentException("Unknown link name in the feature file: " + linkName);
		}
	}

	@When("Validate user into JobDetails page")
	public void validate_user_into_job_details_page() {
		// Write code here that turns the phrase above into concrete actions

		pomjobdetails = new POM_JobDetails(driver);

		pomjobdetails.getJDJobSrch();
		pomjobdetails.clkJobCard();
		System.out.println("=== JobSearch page fetched job details ===");

		pomjobdetails.getJDjobDetails();
		System.out.println("=== JobDetails fetched job details ===");

		pomjobdetails.printJobDetails();
		pomjobdetails.validateJobDetails();
		System.out.println("=== JobDetails inside Job Summary fetched ===");

		profile().rememberJobDetailsPage();

	}

	@When("the user clicks on the {string} link")
	public void the_user_clicks_on_the_link(String linkName) {

		pomProfileJ.clkProfl();
	}

	@Then("the user should be navigated to the respective School Profile page")
	public void the_user_should_be_navigated_to_the_respective_school_profile_page() {

		String url = driver.getCurrentUrl();
		Assert.assertNotEquals("Still on the Job Details page after clicking Profile",
				profile().getJobDetailsUrl(), url);
		Assert.assertFalse("School Profile page has no title", driver.getTitle().trim().isEmpty());

	}

	@When("the user navigates back to the Job Details page")
	public void the_user_navigates_back_to_the_job_details_page() {

		profile().backToJobDetails();
		String urlb = driver.getCurrentUrl();
		@Nullable
		String titleb = driver.getTitle();

		System.out.println("Curret page URL is: " + urlb);
		System.out.println("Curret page title is: " + titleb);

	}

	@When("clicks on the {string} link")
	public void clicks_on_the_link(String linkName) {
		// Write code here that turns the phrase above into concrete actions
		clickLink(linkName);
		System.out.println("===== View All Jobs clicked ======");
	}

	@Then("the user should be navigated to the respective school's All Jobs page")
	public void the_user_should_be_navigated_to_the_respective_school_s_all_jobs_page() {
		// Write code here that turns the phrase above into concrete actions

		String urlJ = driver.getCurrentUrl();
		@Nullable
		String titleJ = driver.getTitle();

		System.out.println("Curret page URL is: " + urlJ);
		System.out.println("Curret page title is: " + titleJ);

		Assert.assertNotEquals("Still on the Job Details page after clicking View All Jobs",
				profile().getJobDetailsUrl(), driver.getCurrentUrl());
	}

	@Then("the user should be able to view all jobs posted by that school")
	public void the_user_should_be_able_to_view_all_jobs_posted_by_that_school() {
		// Write code here that turns the phrase above into concrete actions
		String heading = profile().getAllJobsHeading();
		System.out.println("All Jobs page heading: " + heading);
		Assert.assertFalse("The All Jobs page shows no job count heading", heading.isEmpty());

	}



}
