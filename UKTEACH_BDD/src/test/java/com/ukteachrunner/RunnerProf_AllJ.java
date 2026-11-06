package com.ukteachrunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
		features = "classpath:FeaturesFiles",          // must match the folder name under src/test/resources exactly
		glue = {
				"com.ukteachstepdefinition",           // JobSearch_Steps and Test_JobDetails
				"com.ukteachbase"                      // Hook_UKTEACH
		},
		tags = "@ProfileAndViewAllJobs",
		plugin = {
				"pretty",
				"html:target/CucumberReports/JobDetailsReport.html",
				"json:target/CucumberReports/JobDetailsReport.json"
		},
		monochrome = true
		)
public class RunnerProf_AllJ {

}


