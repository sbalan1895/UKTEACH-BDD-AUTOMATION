package com.pomjobapply;

import java.time.Duration;
import java.util.Map;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.TimeoutException;
import com.baseJobApply.ExcelResource;
import com.baseJobApply.SchoolLogin_Base;

public class POM_JobApply extends SchoolLogin_Base{

	WebDriverWait waits;
	Actions action;
	JavascriptExecutor jsE;

	public static final String UKT_URL = "https://staging-www.ukteach.com/Recruiter/Signin";

	public POM_JobApply(WebDriver driver) {

		this.driver = driver;
		this.waits = new WebDriverWait(driver, Duration.ofSeconds(30));
		this.action = new Actions(driver);
		this.jsE = (JavascriptExecutor)driver;

	}
	private static final int STICKY_OFFSET = 320;

	private By psCode= By.xpath("//h1[contains(text(),'Enter site passcode')]");
	private By entPsC = By.xpath("//input[@name='passcode']");
	private By clkPsC = By.xpath("//button[@type='submit']");

	private By cookRj = By.xpath("//button[contains(text(),'Reject All')]");
	private By rctE = By.xpath("//input[@id='Input_Email']");
	private By rctP = By.xpath("//input[@id='passwordField']");
	private By rctlBtn = By.xpath("//button[@id='login-submit']");

	private By rctlUser = By.xpath("//div//span[@class='name']");
	private By tlJbCnt = By.xpath("//div[@class='stat-body']//a[.//div[contains(text(),'Total jobs')]]//div[1]");
	private By actJbCnt = By.xpath("//div[@class='stat-body']//a[.//div[contains(text(),'Active Jobs')]]//div[1]");
	private By aplCnt = By.xpath("//div[@class='stat-body']//a[.//div[contains(text(),'Applications')]]//div[1]");
	private By creatVcBtn = By.xpath("//a[contains(text(),'Create Vacancy')]");

	private By jobAdvertTab = By.xpath("//a[contains(normalize-space(.),'Job Advert Detail')]");
	private By jobTitF = By.xpath("//input[@id='JobTitle']");
	//	private By jobRefF      = By.xpath("//input[@id='YourRefernce']");
	private By locationF    = By.xpath("//input[@id='CurrentLocation_0']");
	private By clkAddL = By.xpath("//input[@id='btnadd_0']"); // or input/autocomplete
	private By advertFrame = By.xpath("//iframe[contains(@class,'cke_wysiwyg_frame')"
			+ " and contains(@title,'Description')]");
	private By advertBody  = By.xpath("//body[@contenteditable='true']");

	private By jobSmry = By.xpath("//input[@id='chkJobSummary']");     // rich-text editor?
	
	private By contractType = By.xpath("//input[@id='ContractTypes13']"); //input[@id='ContractTypes13']
	private By salaryFromF  = By.xpath("//input[@id='SalaryFrom']");
	private By salaryToF    = By.xpath("//input[@id='SalaryTo']");
	private By saveCnt = By.xpath("(//input[@value='Save & Continue'])[1]");
	private By locSuggestions = By.xpath("//ul[contains(@class,'ui-autocomplete') or contains(@class,'suggest') or contains(@class,'typeahead')]//li");

	//====================

	private void scrollTo(WebElement el) {
		jsE.executeScript(
				"var r = arguments[0].getBoundingClientRect();"
						+ "window.scrollBy({top: r.top - arguments[1], left: 0, behavior: 'instant'});",
						el, STICKY_OFFSET);
	}

	private void typeInto(By locator, String value) {

		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("No Excel value for field: " + locator);
		}

		WebElement el = waits.until(ExpectedConditions.presenceOfElementLocated(locator));
		scrollTo(el);
		waits.until(ExpectedConditions.elementToBeClickable(el));

		try {
			el.clear();
			el.sendKeys(value);
		} catch (Exception e) {
			// fallback for fields that ignore sendKeys or are covered by a sticky header
			jsE.executeScript(
					"arguments[0].value = arguments[1];"
							+ "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
							+ "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", el, value);
		}

		System.out.println("Entered [" + value + "] in " + locator);
	}

	private void safeClick(By locator) {
		WebElement el = waits.until(ExpectedConditions.presenceOfElementLocated(locator));
		scrollTo(el);
		try {
			waits.until(ExpectedConditions.elementToBeClickable(el)).click();
		} catch (ElementClickInterceptedException | StaleElementReferenceException e) {
			el = driver.findElement(locator);                       // fresh reference
			jsE.executeScript("arguments[0].click();", el);
		}
	}

	//=====================

	public void navigateToUKTeach() {
		driver.get(UKT_URL);
	}

	public void enterPassCode(String value) {

		try {

			WebElement passCodeHeading = waits.until(ExpectedConditions.visibilityOfElementLocated(psCode));

			if(passCodeHeading.getText().contains("Enter site passcode")) {
				WebElement enterCode = waits.until(ExpectedConditions.elementToBeClickable(entPsC));
				enterCode.sendKeys(value);
				WebElement clkSubmit = waits.until(ExpectedConditions.elementToBeClickable(clkPsC));
				clkSubmit.click();
			}
		}
		catch(Exception exc) {
			System.out.println("Passcode page is not displayed, Continuing...,");
		}

	}

	public void handleCookiesPopup() {

		WebElement chPopup = waits.until(ExpectedConditions.visibilityOfElementLocated(cookRj));

		try {
			if(chPopup.isDisplayed()) {
				action.moveToElement(chPopup).click().build().perform();
				System.out.println("Cookies constent banner dismissed using RejectAll");
			}
		}
		catch(StaleElementReferenceException | ElementClickInterceptedException e ) {

			System.out.println("Cookie banner interaction was interrupted: " + e.getClass().getSimpleName());
			throw e;
		} 
	}		

	public void enterEmailID(String value) {

		@NonNull
		WebElement entE = waits.until(ExpectedConditions.elementToBeClickable(rctE));
		entE.sendKeys(value);

	}

	public void enterPassword(String value) {
		@NonNull
		WebElement entP = waits.until(ExpectedConditions.elementToBeClickable(rctP));
		entP.sendKeys(value);
	}

	public void clkRLog() {

		waits.until(ExpectedConditions.elementToBeClickable(rctlBtn)).click();
	}

	public void getUserNameDashBoard() {
		WebElement userR = waits.until(ExpectedConditions.visibilityOfElementLocated(rctlUser));

		String recutUd = userR.getText();
		System.out.println("Recruiter login userName is: " + recutUd);

	}

	public void getTotalJobCount() {
		WebElement totalJobC = waits.until(ExpectedConditions.elementToBeClickable(tlJbCnt));
		String tJobv= totalJobC.getText();
		System.out.println("----Total job count is " +tJobv +"----");

	}

	public void getActiveJobCount() {
		WebElement actJobC = waits.until(ExpectedConditions.elementToBeClickable(actJbCnt));
		String aJobv= actJobC.getText();
		System.out.println("----Active job count is " +aJobv +"----");

	}

	public void getAppilcationsCount() {

		WebElement applC = waits.until(ExpectedConditions.elementToBeClickable(aplCnt));
		String applicC= applC.getText();
		System.out.println("----Aplications count is " +applicC +"----");

	}

	public void clkCreateVcny() {
//		waits.until(ExpectedConditions.elementToBeClickable(creatVcBtn)).click();

		safeClick(creatVcBtn);
	}


	public void openJobAdvertTab() {
		WebElement tab = waits.until(ExpectedConditions.elementToBeClickable(jobAdvertTab));

		System.out.println("====== Job Advert Details session displaying =====");

	}

	public void enterJobTitle(String v)     { 
		typeInto(jobTitF, v); 
	}

	public void selectLocation(String v) {

		if (v == null || v.isBlank()) {
			throw new IllegalArgumentException("No Excel value for Location");
		}

		By option = By.xpath("//ul[contains(@class,'ui-autocomplete')]"
				+ "//li[normalize-space(.)='" + v.trim() + "']");

		WebElement input = waits.until(ExpectedConditions.presenceOfElementLocated(locationF));

		for (int attempt = 1; attempt <= 3; attempt++) {
			try {
				input = driver.findElement(locationF);
				scrollTo(input);
				waits.until(ExpectedConditions.elementToBeClickable(input));

				input.click();                 // give it focus
				input.clear();
				input.sendKeys(v);

				// the list only needs to exist, it does not need to be visible
				WebElement suggestion = new WebDriverWait(driver, Duration.ofSeconds(10))
						.until(ExpectedConditions.presenceOfElementLocated(option));

				// keyboard selection works even if the list is off-screen
				input.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

				System.out.println("Location selected: " + v + " (attempt " + attempt + ")");
				return;

			} catch (StaleElementReferenceException | TimeoutException
					| ElementClickInterceptedException e) {
				System.out.println("Location attempt " + attempt + " failed ("
						+ e.getClass().getSimpleName() + "), retrying");
			}
		}
		throw new RuntimeException("Could not select location: " + v);
	}

	public void clkAddBtn() {
		//		waits.until(ExpectedConditions.elementToBeClickable(clkAddL)).click();

		safeClick(clkAddL);
	}

	// Job Advert Text: plain textarea / contenteditable div
	public void enterJobAdvertText(String v) {

		if (v == null || v.isBlank()) {
			throw new IllegalArgumentException("No Excel value for Job Advert Text");
		}

		WebElement frame = waits.until(ExpectedConditions.presenceOfElementLocated(advertFrame));
		scrollTo(frame);

		try {

			waits.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));

			WebElement body = waits.until(ExpectedConditions.elementToBeClickable(advertBody));
			body.click();
			body.sendKeys(v);
			
		/*	waits.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
			WebElement body = waits.until(ExpectedConditions.presenceOfElementLocated(advertBody));
			body.sendKeys(v); */

			System.out.println("Entered Job Advert Text: " + v);

		}finally {

			driver.switchTo().defaultContent();   // always return to the main page
		}
	}

	public void enbJScheckbX() {

		// 1. confirm the locator exists at all
		int found = driver.findElements(jobSmry).size();
		System.out.println("Job Summary checkbox matches: " + found);
		if (found == 0) {
			throw new RuntimeException("Checkbox locator not found: " + jobSmry);
		}

		WebElement chk = waits.until(ExpectedConditions.presenceOfElementLocated(jobSmry));
		scrollTo(chk);

		// 2. tick it only if it is not already ticked
		if (!chk.isSelected()) {
			try {
				waits.until(ExpectedConditions.elementToBeClickable(chk)).click();
			} catch (Exception e) {
				// custom-styled checkboxes are often hidden or covered by a label
				jsE.executeScript("arguments[0].click();", chk);
			}
		}

		// 3. verify
		waits.until(ExpectedConditions.elementToBeSelected(chk));
		System.out.println("===== Job Summary CheckBox enabled =====");
	}

	public void enterSalaryFrom(String v)   { 
		typeInto(salaryFromF, v); 
	}
	public void enterSalaryTo(String v)     { 
		typeInto(salaryToF, v); 
	}

	public void clkSaveCntu() {
		String before = driver.getCurrentUrl();
		safeClick(saveCnt);
		waits.until(d -> !d.getCurrentUrl().equals(before)
				|| !d.findElements(By.xpath("//a[contains(.,'Application')]")).isEmpty());
	}

}
