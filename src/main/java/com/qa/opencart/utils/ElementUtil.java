package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void doSendKeys(By locator, String value) {
		WebElement element=getElement(locator);
		element.clear();
		element.sendKeys(value);
	}

	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}

	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	public boolean doElementISDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public String getAttributeValue(By locator, String value) {
		return getElement(locator).getAttribute(value);
	}

	public void getElementAttributes(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String attrValue = e.getAttribute(attrName);
			System.out.println(attrValue);
		}
	}

	public int getTotalElementCount(By locator) {
		int eleCount = getElements(locator).size();
		System.out.println("total elements for :" + locator + " --->" + eleCount);
		return eleCount;

	}

	public List<String> getElementTextList(By locator) {
		List<String> eleTextList = new ArrayList<String>();
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			eleTextList.add(text);
		}
		return eleTextList;
	}

	// **************** Select based Dropdown Utils **************//

	public void doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);

	}

	public void doSelectDropDownByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);

	}

	public void doSelectDropDownByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);

	}

	public List<WebElement> getDropDownOptionsList(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions();
	}

	public List<String> getDropDownOptionsTextList(By locator) {
		List<WebElement> optionsList = getDropDownOptionsList(locator);
		List<String> optionsTextList = new ArrayList<String>();
		for (WebElement e : optionsList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
	}

	public void selectDropDownValue(By locator, String expVal) {
		List<WebElement> optionsList = getDropDownOptionsList(locator);
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(expVal)) {
				e.click();
				break;
			}

		}
	}

	public int getTotalDropDownOptions(By locator) {
		return getDropDownOptionsList(locator).size();
	}

	public void doSearch(By suggListLocator, String suggName) {
		List<WebElement> sugglist = getElements(suggListLocator);
		System.out.println(sugglist.size());

		for (WebElement e : sugglist) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(suggName)) {
				e.click();
				break;
			}
		}
	}

	// ********************** Wait Utils *******************//

	// This Method check only in DOM element
	public WebElement waitForElementPresence(By locator, int Timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Timeout));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}

	// This Method check only in DOM elements
	public List<WebElement> waitForElementsPresence(By locator, int Timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

	}

	// THis method check in DOM and also check the element is present on the page
	// also and check the element width and height is
	// Greater than 1 or not
	public WebElement waitForElementVisible(By locator, int Timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Timeout));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	// THis method check in DOM and also check the elements is present on the page
	// also and check the element width and height is
	// Greater than 1 or not
	public List<WebElement> waitForElementsVisible(By locator, int Timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

	}

	// ****************** Non WebElement Using WAIT ************//
	public Alert waitForAlertPresence(int TimeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int TimeOut) {
		return waitForAlertPresence(TimeOut).getText();
	}

	public void acceptAlert(int TimeOut) {
		waitForAlertPresence(TimeOut).accept();
	}

	public void dismissAlert(int TimeOut) {
		waitForAlertPresence(TimeOut).dismiss();
	}

	public void alertSendKeys(int TimeOut, String value) {
		waitForAlertPresence(TimeOut).sendKeys(value);
	}

	public String waitForTitleContainsAndFetch(int TimeOut, String titleFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		wait.until(ExpectedConditions.titleContains(titleFractionValue));
		return driver.getTitle();

	}

	public String waitForTitleIsAndFetch(int TimeOut, String titleValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		wait.until(ExpectedConditions.titleIs(titleValue));
		return driver.getTitle();

	}

	public String waitForURLContainsAndFetch(int TimeOut, String URLFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		wait.until(ExpectedConditions.urlContains(URLFractionValue));
		return driver.getCurrentUrl();

	}

	public String waitForURLISAndFetch(int TimeOut, String URLValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		wait.until(ExpectedConditions.urlToBe(URLValue));
		return driver.getCurrentUrl();

	}

	public boolean waitForURLContains(int TimeOut, String URLFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		return wait.until(ExpectedConditions.urlContains(URLFractionValue));

	}

	// **************************frames Utils using WAIT****************//
	public void waitForFrameAndSwitchItToByIDOrName(int TImeOut, String iDorName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TImeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iDorName));

	}

	public void waitForFrameAndSwitchItToByIndex(int TImeOut, int frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TImeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));

	}

	public void waitForFrameAndSwitchItToByFrameElement(int TImeOut, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TImeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));

	}

	public void waitForFrameAndSwitchItToByFrameLocator(int TImeOut, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TImeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));

	}

	// *****************Click Utils *****************//

	public void clickWhenReady(int TimeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	public WebElement waitForElementToBeClickable(int TimeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void doClickWithActionAndWait(int TimeOut, By locator) {
		WebElement ele = waitForElementToBeClickable(TimeOut, locator);
		Actions act = new Actions(driver);
		act.click(ele).build().perform();
	}

}
