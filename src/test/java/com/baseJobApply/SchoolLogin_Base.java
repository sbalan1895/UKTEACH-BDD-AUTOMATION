package com.baseJobApply;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SchoolLogin_Base {
	
	public static WebDriver driver;
	public static WebDriverWait waits;
	
	public void navigateUKT() {

	/*	WebDriverManager.chromedriver().setup();
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		waits= new WebDriverWait(driver, Duration.ofSeconds(30)); */

		ChromeOptions opts = new ChromeOptions();
		opts.addArguments("--window-size=1920,1080");
		opts.setExperimentalOption("excludeSwitches", java.util.List.of("enable-automation"));
		driver = new ChromeDriver(opts);
		driver.manage().window().maximize();
		
	}

}
