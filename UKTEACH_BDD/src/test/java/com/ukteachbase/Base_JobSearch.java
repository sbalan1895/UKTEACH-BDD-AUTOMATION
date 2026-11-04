package com.ukteachbase;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Base_JobSearch {

	public static WebDriver driver;
	public static WebDriverWait waits;

	public void jobSearch() {

		WebDriverManager.chromedriver().setup();
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		waits= new WebDriverWait(driver, Duration.ofSeconds(30));



	}



}
