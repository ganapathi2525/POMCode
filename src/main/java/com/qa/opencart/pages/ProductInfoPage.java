package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By productHeader = By.tagName("h1");
	private By productImages = By.xpath("//ul[@class='thumbnails']//a");
	private By productMetadata = By.xpath("(//div[@id='content']//div[@class='col-sm-4']//ul)[position()=1]/li");
	private By productPricedata = By.xpath("(//div[@id='content']//div[@class='col-sm-4']//ul)[position()=2]/li");
    
	private By quantity=By.id("input-quantity");
	private By addToCartBtn=By.id("button-cart");
	
	private By cartSuccessMessg=By.xpath("//div[contains(@class,'alert-success')]");
	
	private Map<String, String> productInfoMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeaderValue() {
		String productHeaderVal = eleUtil.doElementGetText(productHeader);
		System.out.println("Product header: " + productHeaderVal);
		return productHeaderVal;
	}

	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("Images Count :" + imagesCount);
		return imagesCount;

	}
	
	
	public void enterQuantity(int qty) {
		System.out.println("Product Quantity :"+qty);
		eleUtil.doSendKeys(quantity, String.valueOf(qty));
	}
	public String addProductToCart() {
		eleUtil.doClick(addToCartBtn);
		String successMesg=eleUtil.waitForElementVisible(cartSuccessMessg,AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		
		StringBuilder sb=new StringBuilder(successMesg);
		
		String mesg=sb.substring(0,successMesg.length()-1).replace("\n", "").toString();
		System.out.println("Cart Success Message" +mesg);
		return mesg;	
	}
	
	

	public Map<String, String> getProductInfo() {
		// hash map doesn't follow order..that is why we used linked hasmap
		//productInfoMap = new HashMap<String, String>();
		productInfoMap = new LinkedHashMap<String, String>();
		
		// tree map will automatically sorted
		//productInfoMap = new TreeMap<String, String>();
		
		// header
		productInfoMap.put("productname", getProductHeaderValue());
		getProductMetadata();
		getProductPricedata();
		System.out.println(productInfoMap);
		return productInfoMap;
	}

	//fetching meta data
	private void getProductMetadata() {
		// Meta data
		List<WebElement> metaList = eleUtil.getElements(productMetadata);

		for (WebElement e : metaList) {
			String meta = e.getText();
			String metaInfo[] = meta.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();

			productInfoMap.put(key, value);
		}

	}

	//fetching pricee data
	private void getProductPricedata() {
		// Price data
		List<WebElement> priceList = eleUtil.getElements(productPricedata);
		String price = priceList.get(0).getText();
		String exTax = priceList.get(1).getText();
		String exTaxVal = exTax.split(":")[1].trim();

		productInfoMap.put("productprice", price);
		productInfoMap.put("exTax", exTaxVal);

	}

}
