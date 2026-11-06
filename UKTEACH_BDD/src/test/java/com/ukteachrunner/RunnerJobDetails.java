package com.ukteachrunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Cucumber runs SCENARIOS from feature files, not step-definition classes.
 * Every class in the glue packages is always loaded; "tags" decides which scenarios run.
 *
 *   tags = "@JobDetails"                   -> only the JobDetails scenario (Test_JobDetails)
 *   tags = "@JobSearch"                    -> only the job search scenario (JobSearch_Steps)
 *   tags = "@JobSearch or @JobDetails"     -> both
 *   (remove the tags line)                 -> every scenario in the features folder
 */

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "classpath:FeaturesFiles",          // must match the folder name under src/test/resources exactly
		glue = {
				"com.ukteachstepdefinition",           // JobSearch_Steps and Test_JobDetails
				"com.ukteachbase"                      // Hook_UKTEACH
		},
		tags = "@JobDetails",
		plugin = {
				"pretty",
				"html:target/CucumberReports/JobDetailsReport.html",
				"json:target/CucumberReports/JobDetailsReport.json"
		},
		monochrome = true
		)
public class RunnerJobDetails {

}


