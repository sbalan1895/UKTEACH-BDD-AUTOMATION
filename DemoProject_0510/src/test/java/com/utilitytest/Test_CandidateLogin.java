package com.utilitytest;

import org.testng.annotations.Test;

import com.baseclass.BaseCandidateLogin;
import com.pomutkadmin.POM_CandLogin;

public class Test_CandidateLogin extends BaseCandidateLogin {
	
	POM_CandLogin pomCandL;
	
	@Test(priority= 1)
	public void validCandL() {
		
		pomCandL= new POM_CandLogin(driver);
		
		pomCandL.clkCandLogin();
		pomCandL.validateCandLogin();
		pomCandL.enterEmID("sbdcand@mailinator.com");
		pomCandL.enterPsw("Sbtest@123Sbtest@123");
		pomCandL.clkLoginBtn();
		pomCandL.getCandUser();
		
	}

}
