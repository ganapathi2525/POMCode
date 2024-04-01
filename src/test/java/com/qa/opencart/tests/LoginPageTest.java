 package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("EPIC -100: design login for open cart app")
@Story("US-login: 101: design login page features for open cart  ")
public class LoginPageTest extends BaseTest {
	
	@Severity(SeverityLevel.TRIVIAL)
	@Description("checking the title of the page.... Author:Ganapathi")
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String actualTitle=loginpage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}
	

	@Severity(SeverityLevel.NORMAL)
	@Description("checking the url of the page.... Author:Ganapathi")
	@Test(priority = 2)
	public void loginPageTitleURL() {
		String actualURL=loginpage.getLoginPageURL();
		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}
	

	@Severity(SeverityLevel.CRITICAL)
	@Description("checking forgot pwd link exist.... Author:Ganapathi")
	@Test(priority = 3)
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginpage.isForgotPwdLinkExist());
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("checking user is able to app with corrct username and password.......i")
	@Test(priority = 4)
	public void loginTest() {
		accPage=loginpage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	
	
	
	
	
}
