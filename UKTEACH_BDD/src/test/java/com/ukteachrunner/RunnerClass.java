package com.ukteachrunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
	    features = "classpath:FeaturesFiles",
	    glue = {
	        "com.ukteachstepdefinition",
	        "com.ukteachbase"
	    },
	    plugin = {
	        "pretty",
	        "html:target/CucumberReports/JobSearchReport.html",
	        "json:target/CucumberReports/JobSearchReport.json"
	    },
	    monochrome = true
	)


public class RunnerClass {	
	
}
