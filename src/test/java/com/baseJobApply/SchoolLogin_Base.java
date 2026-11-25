package com.baseJobApply;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SchoolLogin_Base {
	
	public static WebDriver driver;
	public static WebDriverWait waits;
	
	public void navigateUKT() {

		WebDriverManager.chromedriver().setup();

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("profile.password_manager_leak_detection", false);

		ChromeOptions opts = new ChromeOptions();
		opts.addArguments("--incognito");
		opts.setExperimentalOption("prefs", prefs);                                   // "prefs"
		opts.setExperimentalOption("excludeSwitches", List.of("enable-automation"));  // "excludeSwitches"
		opts.addArguments("--disable-save-password-bubble");
		opts.addArguments("--disable-notifications");
		opts.addArguments("--window-size=1920,1080");

		driver = new ChromeDriver(opts);
		driver.manage().window().maximize();
		waits = new WebDriverWait(driver, Duration.ofSeconds(30));
		
	}

}
