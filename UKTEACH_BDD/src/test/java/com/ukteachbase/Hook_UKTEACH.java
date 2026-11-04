package com.ukteachbase;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.pomjobsearch.POM_JOBSEARCH;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hook_UKTEACH extends Base_JobSearch{


	@Before
	public void setUKT() {

		jobSearch();


	}

	@After
	public void takeScreenshots() throws IOException {

		String screenshotName= "Screenshot_" + System.currentTimeMillis() + ".png";

		TakesScreenshot ts = (TakesScreenshot) driver;

		File source = ts.getScreenshotAs(OutputType.FILE);

		File destination= new File(System.getProperty("user.dir")+ "\\Screenshots\\" +screenshotName);

		FileUtils.copyFile(source, destination);

	}



}
