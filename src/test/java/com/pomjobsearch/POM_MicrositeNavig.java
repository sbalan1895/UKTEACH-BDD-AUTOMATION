package com.pomjobsearch;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
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

public class POM_MicrositeNavig extends Base_JobSearch {

	WebDriverWait waits;
	Actions action;
	JavascriptExecutor js;

	public POM_MicrositeNavig(WebDriver driver) {

		this.waits = new WebDriverWait(driver, Duration.ofSeconds(50));
		this.action = new Actions(driver);
		this.js = (JavascriptExecutor) driver;

	}

	private By mainNv= By.xpath("//ul[@class='mainNav']");
	private By locations = By.xpath("//ul[@class='mainNav']/li/a[normalize-space()='Locations']");
	private By subMLocs  = By.xpath("//ul[@class='mainNav']/li[a[normalize-space()='Locations']]//ul//a");
	private By uktHome = By.xpath("//ul[@class='mainNav']//li/a[contains(text(),'UK Teach Home')]");

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

	//========= helper =============

	private String readText(WebElement el) {

		String text = el.getText().trim();

		if(text.isEmpty()) {

			String attribTxt = el.getAttribute("textContent");
			text = attribTxt == null ? "" : attribTxt.trim().replaceAll("\\s+", " ");
		}

		return text;

	}

	private void safeClick(WebElement el) {

		try {
			if(el.isDisplayed()) {

				waits.until(ExpectedConditions.elementToBeClickable(el)).click();

			}
			else {
				js.executeScript("arguments[0].click();", el);
			}

		}
		catch (Exception e) {
			js.executeScript("arguments[0].click();", el);
		}
	}

	private void waitForPageLoad() {
		waits.until(d -> "complete".equals(js.executeScript("return document.readyState")));
	}

	private List<WebElement> openLocationMenu() {
		WebElement locaT = waits.until(ExpectedConditions.visibilityOfElementLocated(locations));
		action.moveToElement(locaT).build().perform();

		return waits.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(subMLocs));

	}

	//======================================

	public List<String> getHeaderMainNavi() {

		WebElement mainN = waits.until(ExpectedConditions.presenceOfElementLocated(mainNv));

		List<WebElement> naviLinks = mainN.findElements(By.xpath("./li/a"));

		List<String> navOptions = new ArrayList<>();

		for(WebElement headerLinks:  naviLinks ) {

			String headerText = readText(headerLinks);

			if(!headerText.isBlank()) {

				navOptions.add(headerText);
				System.out.println(headerText);
			}

		}
		return navOptions;

	}

	public List<String> getLocations() {

		List<WebElement> locSubM= openLocationMenu();

		List<String> getLocSM = new ArrayList<String>();

		for(WebElement locli : locSubM) {

			String locationli = readText(locli);

			if(!locationli.isBlank()) {
				getLocSM.add(locationli);
				System.out.println(locationli);
			}

		}
		return getLocSM;

	}

	public List<locInfo> navigateAllLocation(){

		List<locInfo> results = new ArrayList<>();

		String homeUrl = driver.getCurrentUrl();
		String parentWindow = driver.getWindowHandle();

		int locationCount = openLocationMenu().size();
		System.out.println("Total location count is " + locationCount);

		for(int i = 0; i < locationCount; i++) {

			WebElement itemLocat = openLocationMenu().get(i);
//			String text = readText(itemLocat);
			String beforeURL =  driver.getCurrentUrl();

			String name = readText(itemLocat);
	        String href = itemLocat.getAttribute("href");
	        String target = itemLocat.getAttribute("target");
	        System.out.println("Clicking: " + name + " | href=" + href + " | target=" + target);

	        Set<String> handlesBefore = driver.getWindowHandles();
			
			safeClick(itemLocat);
			
			// wait for EITHER a new tab OR a URL change
	        waits.until(d -> d.getWindowHandles().size() > handlesBefore.size()
	                      || !d.getCurrentUrl().equals(homeUrl));

	        boolean newTab = driver.getWindowHandles().size() > handlesBefore.size();

	        if (newTab) {
	            // switch to the new tab
	            for (String h : driver.getWindowHandles()) {
	                if (!handlesBefore.contains(h)) {
	                    driver.switchTo().window(h);
	                    break;
	                }
	            }
	        }

	        waitForPageLoad();

	        locInfo info = new locInfo(name, driver.getTitle(), driver.getCurrentUrl());
	        System.out.println(info);
	        results.add(info);

	        if (newTab) {
	            // no need to go home, just close the tab and return
	            driver.close();
	            driver.switchTo().window(parentWindow);
	        } else {
	            // same tab: try "UK Teach Home", then fall back
	            try {
	                WebElement home = new WebDriverWait(driver, Duration.ofSeconds(5))
	                        .until(ExpectedConditions.presenceOfElementLocated(uktHome));
	                safeClick(home);
	            } catch (Exception e) {
	                System.out.println("UK Teach Home link not found, using driver.get(homeUrl)");
	                driver.get(homeUrl);
	            }
	        }

	        waits.until(ExpectedConditions.visibilityOfElementLocated(locations));
	        waitForPageLoad();
	    }
	    return results;

	}

}
