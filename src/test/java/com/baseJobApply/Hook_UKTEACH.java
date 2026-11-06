package com.baseJobApply;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hook_UKTEACH extends SchoolLogin_Base{


	@Before
	public void setUKT() {

		navigateUKT();

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
