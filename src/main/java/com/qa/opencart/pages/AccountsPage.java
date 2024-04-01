package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By logoutLink=By.xpath("//*[@id=\"column-right\"]/div/a[13]");
	private By accsHeader=By.xpath("//div[@id='content']//h2");
	private By search=By.name("search");
	private By searchIcon=By.xpath("//div[@id='search']//button");
	
	
	public AccountsPage(WebDriver driver) {
		this.driver=driver;	
		eleUtil=new ElementUtil(driver);
	}
	public String getLoginPageTitle() {
		//String title=driver.getTitle();
		String title=eleUtil.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);

		System.out.println(title);
		return title;
	}
	
	public String getLoginPageURL() {
		//String url=driver.getCurrentUrl();
		String url=eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,AppConstants.ACCOUNTS_PAGE_URL_FRACTION_VALUE);

		System.out.println(url);
		return url;
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementVisible(logoutLink,AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	
	public boolean isSearchExist() {
		return eleUtil.waitForElementVisible(search, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	
	public List<String> getAccountsPageHeadersList() {
		List<WebElement> accHeadersList=eleUtil.waitForElementsVisible(accsHeader, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		//List<WebElement> accHeadersList=driver.findElements(accsHeader);
		List<String> accHeadersValList = new ArrayList<>();
		
		for (WebElement e: accHeadersList) {
			String text=e.getText();
			accHeadersValList.add(text);
		}
		return accHeadersValList;		
	}
	
	public SearchPage performSearch(String searchKey) {
		if(isSearchExist()) {
			eleUtil.doSendKeys(search, searchKey);
			eleUtil.doClick(searchIcon);
			return new SearchPage(driver);
		}
		else {
			System.out.println("Search field is not present on the page");
			return null;
		}
		
		
	}

}
