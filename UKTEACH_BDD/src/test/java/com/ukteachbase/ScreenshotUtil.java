package com.ukteachbase;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {
	
	private static final Path FOLDER = Paths.get(System.getProperty("user.dir"), "Screenshots");

	public ScreenshotUtil() {
	}

	/** Saves a PNG as Screenshots/<name>_<time>.png and returns its path, or null if it could not be taken. */
	public static Path save(WebDriver driver, String name) {

		if (driver == null) {
			return null;
		}
		try {
			File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String safeName = name.replaceAll("[^a-zA-Z0-9._-]", "_");
			Path target = FOLDER.resolve(safeName + "_" + System.currentTimeMillis() + ".png");
			FileUtils.copyFile(source, target.toFile());      // creates the Screenshots folder if needed
			return target;
		} catch (Exception e) {
			// a screenshot problem must never fail the test
			System.out.println("Screenshot not taken: " + e.getMessage());
			return null;
		}
	}

	/** PNG bytes, for attaching the image to the Cucumber report. Empty array if it could not be taken. */
	public static byte[] bytes(WebDriver driver) {

		if (driver == null) {
			return new byte[0];
		}
		try {
			return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		} catch (Exception e) {
			return new byte[0];
		}
	}

	

}
