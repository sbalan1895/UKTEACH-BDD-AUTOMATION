package com.pomjobsearch;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.helpers.Reporter;

import com.ukteachbase.Base_JobSearch;
import com.ukteachbase.ExecutionTracker;

public class POM_JOBSEARCH extends Base_JobSearch{

	WebDriverWait waits;
	Actions action;

	public static final String UKT_URL = "https://staging-www.ukteach.com/";

	public POM_JOBSEARCH(WebDriver driver) {

		this.driver= driver;
		this.waits= new WebDriverWait(driver, Duration.ofSeconds(30));
		this.action= new Actions(driver);

	}

	//============ Locators ===================

	// Example locator - replace with the actual locator
	private By keywordSearchBox = By.id("keywords");

	//enter passcode
	private By psCode= By.xpath("//h1[contains(text(),'Enter site passcode')]");
	private By entPsC = By.xpath("//input[@name='passcode']");
	private By clkPsC = By.xpath("//button[@type='submit']");

	//cookies handle popup
	private By cookRj = By.xpath("//button[contains(text(),'Reject All')]");

	private By clkSearc = By.id("jobsearchbtn");
	private By jobTxt = By.xpath("//div[@class='col-xl-12']//h1[contains(text(),'Teacher')]");
	private By jobCnLst = By.xpath("//h2[@class='job-count-heading']");

	// ---- locators (replace the old jobList locator) ----
	private By topJ = By.id("divOnetopJob");

	// one job card, only cards that contain a job title (excludes any non-job block)
	private By jobCard = By.xpath(
			"//section[contains(@class,'job-list-bg')]//div[contains(@class,'col-lg-4') "
					+ "and contains(@class,'col-md-6') and contains(@class,'aos-init')]"
					+ "[.//h4[contains(@class,'jobtitle')]]");

	// relative to a card
	private By titleInCard = By.xpath(".//h4[contains(@class,'jobtitle')]");
	private By infoInSchool  = By.xpath("//div[@class='searchdeatilsinfo']//p");
	private By infoInLocation  = By.xpath(".//div[@class='searchdeatilsinfo new']//p");


	//============== Helper =====================

	private String readText(WebElement el) {
		String t = el.getText().trim();
		if (t.isEmpty()) {   // hidden / animating element: fall back to raw DOM text
			String raw = el.getAttribute("textContent");
			t = raw == null ? "" : raw.trim().replaceAll("\\s+", " ");
		}
		return t;
	}

	//=============Executions====================

	// Navigate to UK Teach
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

	// Enter search keyword
	public void enterKeyword(String keyword) {
		driver.findElement(keywordSearchBox).sendKeys(keyword);
	}	

	public void clkSearchBtn() {

		WebElement clkSrch = waits.until(ExpectedConditions.elementToBeClickable(clkSearc));
		clkSrch.click();

	}

	public void getJobCount() {

		WebElement jobT = waits.until(ExpectedConditions.visibilityOfElementLocated(jobTxt));
		String jText = jobT.getText();
		Reporter.info(jText);

		WebElement jobC = waits.until(ExpectedConditions.visibilityOfElementLocated(jobCnLst));
		String jcTxt = jobC.getText();
		Reporter.info(jcTxt);

	}

	public void getTopJob() {

		WebElement jobTop = waits.until(ExpectedConditions.visibilityOfElementLocated(topJ));
		String jTopT = jobTop.getText();
		System.out.println(jTopT);
		Reporter.info(jTopT);

	}


	public List<JobInfo> getJobList() {

		List<WebElement> cards = waits.until(ExpectedConditions.presenceOfAllElementsLocatedBy(jobCard));
		List<JobInfo> jobs = new ArrayList<>();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		for (int i = 0; i < cards.size(); i++) {
			WebElement card = cards.get(i);
			js.executeScript("arguments[0].scrollIntoView({block:'center'});", card);

			String title = "N/A", school = "N/A", location = "N/A";

			try {
				title = readText(card.findElement(titleInCard));
			} catch (Exception e) { /* keep N/A */ }

			List<WebElement> schoolEls = card.findElements(infoInSchool);
			if (!schoolEls.isEmpty()) {
				String s = readText(schoolEls.get(0));
				if (!s.isEmpty()) school = s;
			}

			// location
			List<WebElement> locEls = card.findElements(infoInLocation);
			if (!locEls.isEmpty()) {
				List<String> locParts = new ArrayList<>();
				for (WebElement p : locEls) {
					String t = readText(p);
					if (!t.isEmpty()) locParts.add(t);
				}
				if (!locParts.isEmpty()) location = String.join(", ", locParts);
			}

			JobInfo job = new JobInfo(title, school, location);
			jobs.add(job);
			System.out.println("Job " + (i + 1) + " -> " + job);
		}

		System.out.println("Total Jobs displayed in JobSearch: " + jobs.size());

		if (jobs.isEmpty()) {
			ExecutionTracker.fail("getJobList", "no jobs found");
			throw new AssertionError("No jobs displayed on the job search page");
		}
		ExecutionTracker.pass("getJobList");
		return jobs;
	}

}
