package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest{
	
	@BeforeClass
	public void accPageSetUp() {
		accPage = loginpage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	
	@Test
	public void accPageTitleTest() {
		String actTitle=accPage.getLoginPageTitle();
		Assert.assertEquals(actTitle,AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
	}
	
	@Test
	public void accPageURLTest() {
		String actURL=accPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.ACCOUNTS_PAGE_URL_FRACTION_VALUE));
	}
	
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void accPageHeadersCountTest() {
		List<String> actualAccPageHeadersList=accPage.getAccountsPageHeadersList();
	   // System.out.println(actualAccPageHeadersList);
		Assert.assertEquals(actualAccPageHeadersList.size(),AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	
	}
	
	@Test
	public void accPageHeadersValueTest() {
		List<String> actualAccPageHeadersList=accPage.getAccountsPageHeadersList();
	    System.out.println(actualAccPageHeadersList);
	    Assert.assertEquals(actualAccPageHeadersList, AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
	
	}
	
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"Macbook"},
			{"iMac"},
			{"samsung"}
		
		};
	}
	
	@Test(dataProvider = "getProductData")
	public void searchProductCountTest(String searchKey) {
		searchPage=accPage.performSearch(searchKey);
		Assert.assertTrue(searchPage.getSearchProductsCount() >0);
	}
	
	
	@DataProvider
	public Object[][] getproductData() {
		return new Object[][] {
			{"Macbook","MacBook Pro"},
			{"Macbook","MacBook Air"},
			{"iMac","iMac"},
			{"samsung","Samsung SyncMaster 941BW"},
			{"samsung","Samsung Galaxy Tab 10.1"}
		};
	}
	
	@Test(dataProvider = "getproductData")
	public void selectSpecificProductTest(String searchKey,String productName) {
		searchPage=accPage.performSearch(searchKey);
		
		if(searchPage.getSearchProductsCount()>0) {
			productInfoPage=searchPage.selectProduct(productName);
			String actProductHeader=productInfoPage.getProductHeaderValue();
			Assert.assertEquals(actProductHeader,productName);
		}
		
	}
	
	

}
