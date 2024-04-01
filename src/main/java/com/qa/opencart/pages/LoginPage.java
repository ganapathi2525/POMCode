package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1. private By locators
	
	private By emailID=By.id("input-email");
	private By password=By.id("input-password");
	private By loginBtn=By.xpath("//input[@value='Login']");
	private By forgotPwdLink=By.linkText("Forgotten Password");
	private By registerLink=By.linkText("Register");
	
	//2. page constructor
	// for driver intialization
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;	
		eleUtil =new ElementUtil(driver);
	}
	
	//3. page actions/methods
	
	@Step(".......getting the login page title........")
	public String getLoginPageTitle() {
		//String title=driver.getTitle();
		
		String title=eleUtil.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.LOGIN_PAGE_TITLE_VALUE);
		System.out.println(title);
		return title;
	}
	
	@Step(".......getting the login page url........")
	public String getLoginPageURL() {
		String url=eleUtil.waitForURLContainsAndFetch(10,AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE);
		System.out.println(url);
		return url;
	}
	
	@Step(".......getting the forgot password link........")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.waitForElementVisible(forgotPwdLink,AppConstants.DEFAULT_MEDIUM_TIME_OUT ).isDisplayed();
	}
	
	@Step("login with username : {0} and password : {1}")
	public AccountsPage doLogin(String un,String pwd) {
		System.out.println("app creds are"+un+" : "+pwd+"");
		eleUtil.waitForElementVisible(emailID,AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		
		return new AccountsPage(driver);
		
	}
	
	@Step("navigating to register page")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
		
	}



}
