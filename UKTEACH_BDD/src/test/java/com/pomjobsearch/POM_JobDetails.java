package com.pomjobsearch;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ukteachbase.Base_JobSearch;

public class POM_JobDetails extends Base_JobSearch {
	
	public static final List<String> FIELDS = Arrays.asList(
			"Posted", "Close Date", "Phase", "Hours",
			"Job Type", "Ref", "Vacancy Type", "Specialisms");

	WebDriverWait waits;
	Actions action;
	JavascriptExecutor js;

	public POM_JobDetails(WebDriver driver) {

		this.driver = driver;
		this.waits = new WebDriverWait(driver, Duration.ofSeconds(30));
		this.action = new Actions(driver);
		this.js = (JavascriptExecutor) driver;
	}

	//========= locators ==========

	private By jobCard = By.xpath("//div[@class='job-card featured']");
	private By jobTlt = By.xpath(".//h4[@class='jobtitle']//a");
	private By jobSchlN = By.xpath(".//div[@class='searchdeatilsinfo']//p");
	private By jobLoct = By.xpath(".//div[@class='info-item']//p[@data-bs-toggle='tooltip']");
	private By clkJC = By.xpath("(//div[@class='desc'])[2]");

	private By jdCard = By.xpath("//div[@class='desc']");
	private By jnJD = By.xpath("//div[@class='desc']//div[@class='h1']");
	private By jschJD= By.xpath("(//div[@class='desc']//div[@class='info-item']//p)[1]");
	private By jlctJD = By.xpath("(//div[@class='desc']//div[@class='info-item']//p)[2]");
	
	// all eight <dl> rows of the summary block
	private final By detailRows = By.xpath("//div[contains(@class,'j-desc-left')]//dl");
		
	//div[@class='j-desc-left col-8 justify-content-between']
	//(.//div[@class='j-desc-left col-8 justify-content-between']//div)//dl
	
	//===================== Helper ==============

	private String readText(WebElement el) {
		String t = el.getText().trim();
		if (t.isEmpty()) {   // hidden / animating element: fall back to raw DOM text
			String raw = el.getAttribute("textContent");
			t = raw == null ? "" : raw.trim().replaceAll("\\s+", " ");
		}
		return t;
	}

	//========= Execution ==============

	public void getJDJobSrch() {
		
		System.out.println("======= Successfully into navigated to JobSearch page =====");
		System.out.println("==== Current page title is: ====" + driver.getTitle());
		System.out.println("===== Current page URL is =====" + driver.getCurrentUrl());

		@NonNull
		@Nullable
		WebElement jobCardRlt = waits.until(ExpectedConditions.presenceOfElementLocated(clkJC));

		List<JobInfo> jobs = new ArrayList<>();

		js.executeScript("arguments[0].scrollIntoView({block:'center'});", jobCardRlt);

		String title = "N/A", school = "N/A", location = "N/A";

		try {
			WebElement jbT = waits.until(ExpectedConditions.elementToBeClickable(jobTlt));
			title = readText(jbT);

		}
		catch(Exception exe) {
			/* keep N/A */ 
		}

		WebElement jbSn = waits.until(ExpectedConditions.presenceOfElementLocated(jobSchlN));
		school = readText(jbSn);

		WebElement jbLct = waits.until(ExpectedConditions.presenceOfElementLocated(jobLoct));
		location = readText(jbLct);

		JobInfo job = new JobInfo(title, school, location);
		jobs.add(job);

		System.out.println("Job -> " + job + title + school + location);

	}
	
	public void clkJobCard() {
		
		WebElement jbCLk = waits.until(ExpectedConditions.elementToBeClickable(clkJC));
		action.moveToElement(jbCLk).click().build().perform();	
		
	}
	
	public void getJDjobDetails() {
		
		System.out.println("======= Successfully into navigated to JobDetails page =====");
		System.out.println("==== Current page title is: ====" + driver.getTitle());
		System.out.println("===== Current page URL is =====" + driver.getCurrentUrl());
		
		WebElement jdCrd = waits.until(ExpectedConditions.presenceOfElementLocated(jdCard));

		List<JobInfo> jobs = new ArrayList<>();

		js.executeScript("arguments[0].scrollIntoView({block:'center'});", jdCrd);

		String title = "N/A", school = "N/A", location = "N/A";

		try {
			WebElement jdJT = waits.until(ExpectedConditions.elementToBeClickable(jnJD));
			title = readText(jdJT);

		}
		catch(Exception exe) {
			/* keep N/A */ 
		}

		WebElement jdScl = waits.until(ExpectedConditions.presenceOfElementLocated(jschJD));
		school = readText(jdScl);

		WebElement jdLtn = waits.until(ExpectedConditions.presenceOfElementLocated(jlctJD));
		location = readText(jdLtn);

		JobInfo job = new JobInfo(title, school, location);
		jobs.add(job);

		System.out.println("Job -> " + job + title + school + location);

		
	}
	

	/** Returns label -> value for every field found, for example {"Posted"="24 Sep 2026 12:52 (GMT)", "Ref"="MS 2409", ...}. */
	public Map<String, String> getJobDetails() {
 
		waits.until(ExpectedConditions.numberOfElementsToBeMoreThan(detailRows, 0));
 
		for (int attempt = 1; attempt <= 3; attempt++) {
			try {
				Map<String, String> details = new LinkedHashMap<>();
 
				for (WebElement row : driver.findElements(detailRows)) {
					List<WebElement> labelCell = row.findElements(By.xpath("./dd"));
					if (labelCell.isEmpty()) {
						continue;
					}
					String label = labelCell.get(0).getText().replaceAll("\\s*:\\s*$", "").trim();
 
					List<WebElement> valueCell = row.findElements(By.xpath("./dt"));
					String value = valueCell.isEmpty() ? "" : valueCell.get(0).getText().trim();   // Ref can be blank
 
					details.put(label, value);
				}
				return details;
 
			} catch (StaleElementReferenceException e) {
				// the page re-drew the block while it was being read: read it again
			}
		}
		throw new AssertionError("The job details block kept changing while it was being read");
	}
	

	/** One value by its label, for example getJobDetail("Close Date"). */
	public String getJobDetail(String label) {
 
		Map<String, String> details = getJobDetails();
		Assert.assertTrue("Field not found on the page: " + label + " (found: " + details.keySet() + ")",
				details.containsKey(label));
		return details.get(label);
	}
 
	/** Prints every field in the standard order. */
	public void printJobDetails() {
 
		Map<String, String> details = getJobDetails();
 
		System.out.println("===== Job details =====");
		for (String field : FIELDS) {
			System.out.println(String.format("%-14s: %s", field, details.getOrDefault(field, "<not on page>")));
		}
	}
 
	/** Checks all eight fields are on the page, and that Posted and Close Date have a value. Returns the details. */
	public Map<String, String> validateJobDetails() {
 
		Map<String, String> details = getJobDetails();
 
		List<String> missing = new ArrayList<>();
		for (String field : FIELDS) {
			if (!details.containsKey(field)) {
				missing.add(field);
			}
		}
		Assert.assertTrue("Job details missing on the page: " + missing, missing.isEmpty());
		Assert.assertFalse("Posted date is empty", details.get("Posted").isEmpty());
		Assert.assertFalse("Close Date is empty", details.get("Close Date").isEmpty());
 
		return details;
	}

}
