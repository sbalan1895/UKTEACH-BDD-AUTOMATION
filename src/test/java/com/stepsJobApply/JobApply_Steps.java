package com.stepsJobApply;

import java.io.IOException;
import java.util.Map;

import com.baseJobApply.ExcelResource;
import com.baseJobApply.SchoolLogin_Base;
import com.pomjobapply.POM_JobApply;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.apache.poi.EncryptedDocumentException;
import org.junit.Assert;

public class JobApply_Steps extends SchoolLogin_Base {
	
POM_JobApply pom_jobapply;

private static String CRED_FILE = System.getProperty("user.dir") + "/src/test/resources/ResourceFiles/UKTeach_SchoolCredentials.xlsx";
	

@Given("User able to login UK Teach in School Recruiter using {string} credentials")
public void user_able_to_login_uk_teach_in_school_recruiter_using_credentials(String testCase) throws EncryptedDocumentException, IOException {

	Map<String, String> data = ExcelResource.getRowData(CRED_FILE, "Sheet1", testCase);

	pom_jobapply = new POM_JobApply(driver);
	
	pom_jobapply.navigateToUKTeach();
	pom_jobapply.enterPassCode("Tal-UKTLive@2026!");
	pom_jobapply.handleCookiesPopup();
	
	pom_jobapply.enterEmailID(data.get("EmailID"));
	pom_jobapply.enterPassword(data.get("Password"));
	pom_jobapply.clkRLog();
	
}

@When("User able to validate user in Dashboard page")
public void user_able_to_validate_user_in_dashboard_page() {

pom_jobapply.getUserNameDashBoard();
pom_jobapply.getTotalJobCount();
pom_jobapply.getActiveJobCount();
pom_jobapply.getAppilcationsCount();
pom_jobapply.clkCreateVcny();
}

@When("User able to fill all the required fields in JobAdvertDetails page in Create Job Advert using {string} data")
public void user_able_to_fill_all_the_required_fields_in_job_advert_details_page_in_create_job_advert_using_data(String string) throws EncryptedDocumentException, IOException {
	
	Map<String, String> data = ExcelResource.getRowData(CRED_FILE, "Sheet1", string);

	pom_jobapply.openJobAdvertTab();
	pom_jobapply.enterJobTitle(data.get("JobTitle"));
	
	System.out.println("Job Title entered");
	
//	pom_jobapply.enterJobReference(data.get("JobReference"));
	pom_jobapply.selectLocation(data.get("Location"));
	pom_jobapply.clkAddBtn();
	pom_jobapply.enterJobAdvertText(data.get("JobAdvertText"));
	pom_jobapply.enbJScheckbX();
	pom_jobapply.enterSalaryFrom(data.get("SalaryFrom"));
	pom_jobapply.enterSalaryTo(data.get("SalaryTo"));
	
	pom_jobapply.clkSaveCntu();
	
	System.out.println("CurrentPage URL is: " + driver.getCurrentUrl());
	System.out.println("Excel data: " + data);

}

@When("User able to fill all the required fields in Application_Method page in Create Job for OnlineApplication.")
public void user_able_to_fill_all_the_required_fields_in_application_method_page_in_create_job_for_online_application() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

@When("User able to Select all the required fields in Publishing page in Create Job Advert.")
public void user_able_to_select_all_the_required_fields_in_publishing_page_in_create_job_advert() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

@Then("Verify the OnlineJob sccessfully created, JobTitle displays in Broadcast page.")
public void verify_the_online_job_sccessfully_created_job_title_displays_in_broadcast_page() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}



}
