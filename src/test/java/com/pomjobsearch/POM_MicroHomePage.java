package com.pomjobsearch;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pomjobsearch.POM_MicrositeNavig.locInfo;
import com.ukteachbase.Base_JobSearch;

public class POM_MicroHomePage extends Base_JobSearch {
	
	WebDriverWait waits;
	Actions action;
	JavascriptExecutor js;

	public POM_MicroHomePage(WebDriver driver) {
		
		this.waits = new WebDriverWait(driver, Duration.ofSeconds(50));
		this.action = new Actions(driver);
		this.js = (JavascriptExecutor) driver;
		
	}
	
	private By findJobs = By.xpath("//ul[@class='mainNav']//li//a[contains(text(),'Find Your Job')]");
	private By jobC = By.xpath("//h2[@class='job-count-heading']");
	private By blogs = By.xpath("//ul[@class='mainNav']//li//a[contains(text(),'Blogs')]");
	private By aboutUs = By.xpath("//ul[@class='mainNav']//li//a[contains(text(),'About Us')]");
	private By aboutCont = By.xpath("//div[@class='banner-block__content']//p");
	private By FAQbtn = By.xpath("//ul[@class='f-menu']//li//a[contains(text(),'FAQs')]");
	private By faqs = By.xpath("//div[@class='container']//h2[contains(text(),'FAQ')]");
	private By FAQqa = By.xpath("//div[@class='faq-sec aos-init aos-animate']//following::button[@class='accordion-button']");
	//========= Data holder ===========

	public static class locInfo {

		private final String name;
		private final String title;
		private final String url;

		public locInfo(String name, String title, String url ) {

			this.name = name;
			this.title = title;
			this.url = url;

		}

		public String getName() {
			return name;
		}
		public String getTitle() {
			return title;
		}
		public String getURL() {
			return url;
		}

		public String toString() {
			return name + " --> " + title + " | " + url;

		}

	}
	
	
	//================== Helpers==============
	
	private String readText(WebElement el) {

		String text = el.getText().trim();

		if(text.isEmpty()) {

			String attribTxt = el.getAttribute("textContent");
			text = attribTxt == null ? "" : attribTxt.trim().replaceAll("\\s+", " ");
		}

		return text;
	}
	
	private void navigateBack() {
		
		driver.navigate().back();
		
	}
	
	private void scrollToDown(WebElement elem) {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	//=============Execution ==============
	
	public void findYourJobsPage() {
		
		WebElement findJb = waits.until(ExpectedConditions.presenceOfElementLocated(findJobs));
		
		if(findJb.isDisplayed()) {
			
			WebElement fjEle = driver.findElement(findJobs);
			action.moveToElement(fjEle).click().build().perform();
			
			WebElement jobsC = waits.until(ExpectedConditions.elementToBeClickable(jobC));
			String jobCountTxt = readText(jobsC);
			
			LocalDateTime currentTime = LocalDateTime.now();
			DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			
			String formatTime = currentTime.format(dateTime);
			System.out.println("Total Job count: " + jobCountTxt + "CurrentTime: " + formatTime);
			
			@Nullable
			String currentUrlJ = driver.getCurrentUrl();
			String currentTJ = driver.getTitle();
			
			System.out.println("FindYourJob page URL is: " + currentUrlJ);
			System.out.println("FindYourJob page Title is: " + currentTJ);
			
		}
		
	}
	
	public void blogsPage() {
		
		WebElement blogE = driver.findElement(blogs);
		action.moveToElement(blogE).click().build().perform();
		
		@Nullable
		String currentUrlB = driver.getCurrentUrl();
		String currentTB = driver.getTitle();
		
		System.out.println("Blogs page URL is: " + currentUrlB);
		System.out.println("Blogs page Title is: " + currentTB);
	}
	
	public void aboutUsPage() {
		
		WebElement aboutUsE = driver.findElement(aboutUs);
		action.moveToElement(aboutUsE).click().build().perform();
		
		@Nullable
		String currentUrlAu = driver.getCurrentUrl();
		String currentAu = driver.getTitle();
		
		System.out.println("AboutUs page URL is: " + currentUrlAu);
		System.out.println("AboutUs page Title is: " + currentAu);
	}
	
//	public void faqsPage() {
//		
//		
//		
//		WebElement aboutUsE = driver.findElement(FAQbtn);
//		scrollToDown(FAQbtn);
//		
//		action.moveToElement(aboutUsE).click().build().perform();
//		
//	}
	
	

}
