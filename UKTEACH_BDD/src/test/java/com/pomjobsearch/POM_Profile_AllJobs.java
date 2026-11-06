package com.pomjobsearch;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ukteachbase.Base_JobSearch;

public class POM_Profile_AllJobs extends Base_JobSearch {
	
	WebDriverWait waits;
	Actions action;
	JavascriptExecutor js;
	
	private String jobDetailsURL;
	private String jobDetailsWindow;
	
	public POM_Profile_AllJobs (WebDriver driver) {

		this.driver = driver;
		this.waits = new WebDriverWait(driver, Duration.ofSeconds(30));
		this.action = new Actions(driver);
		this.js = (JavascriptExecutor) driver;
		
	}

	private static final String LOWER = "translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')";
	private By ProfBtn =  By.xpath("//a[@class='btn btn-outline'][1]");
	private By VwJbBtn =  By.xpath("//a[@class='btn btn-outline'][2]");
	
	// assumes the school's All Jobs page reuses the job search results heading; adjust if it does not
		private By allJobsHeading = By.xpath("//h2[@class='job-count-heading']");
	
		//======== Helper ===========
		
		/** Clicks a link, then follows it whether it opens in the same tab or in a new one. */
		private void clickAndFollow(By locator, String name) {

			Set<String> windowsBefore = new HashSet<>(driver.getWindowHandles());
			String urlBefore = driver.getCurrentUrl();

			WebElement link = waits.until(ExpectedConditions.elementToBeClickable(locator));
			js.executeScript("arguments[0].scrollIntoView({block:'center', inline:'nearest', behavior:'instant'});", link);
			action.moveToElement(link).click().build().perform();

			// either a new tab opened, or this tab moved to a new URL
			waits.until(d -> d.getWindowHandles().size() > windowsBefore.size()
					|| !d.getCurrentUrl().equals(urlBefore));

			for (String handle : driver.getWindowHandles()) {
				if (!windowsBefore.contains(handle)) {
					driver.switchTo().window(handle);
					break;
				}
			}
			waits.until(d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));

			System.out.println("======= " + name + " opened =======");
			System.out.println("Current page title: " + driver.getTitle());
			System.out.println("Current page URL  : " + driver.getCurrentUrl());
		}


		// ---------- Page actions ----------

		/** Call while still on the Job Details page, so "navigate back" knows where to return. */
		public void rememberJobDetailsPage() {
			jobDetailsURL = driver.getCurrentUrl();
			jobDetailsWindow = driver.getWindowHandle();
		}

		public String getJobDetailsUrl() {
			return jobDetailsURL;
		}

		public void clkProfl() {
			clickAndFollow(ProfBtn, "School Profile");
		}

		public void clkViewAllJobs() {
			clickAndFollow(VwJbBtn, "School All Jobs");
		}

		/** Returns to the Job Details page: closes the extra tab if one was opened, otherwise uses Back. */
		public void backToJobDetails() {

			if (driver.getWindowHandles().size() > 1 && !driver.getWindowHandle().equals(jobDetailsWindow)) {
				driver.close();
				driver.switchTo().window(jobDetailsWindow);
			} else {
				driver.navigate().back();
			}
			waits.until(ExpectedConditions.urlToBe(jobDetailsURL));

			System.out.println("======= Back on the Job Details page: " + driver.getCurrentUrl() + " =======");
		}

		/** Text of the job count heading on the school's All Jobs page. */
		public String getAllJobsHeading() {
			return waits.until(ExpectedConditions.visibilityOfElementLocated(allJobsHeading)).getText().trim();
		}	

}
