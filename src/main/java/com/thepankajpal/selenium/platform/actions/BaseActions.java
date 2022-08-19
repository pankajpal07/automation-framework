package com.thepankajpal.selenium.platform.actions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.thepankajpal.selenium.platform.exception.AutomationPlatformException;
import com.thepankajpal.selenium.platform.utility.ObjectUtil;

public class BaseActions {
	
	private WebDriver webDriver;
	
	public BaseActions(WebDriver webDriver) {
		this.webDriver = webDriver;
	}
	
	public void click(String by, String locator) throws AutomationPlatformException {
		WebElement webElement = getWebElement(by, locator);
		webElement.click();
	}
	
	public void mouseClick(String by, String locator) throws AutomationPlatformException {
		WebElement webElement = getWebElement(by, locator);
		Actions actions = new Actions(webDriver);
		actions.moveToElement(webElement).click();
	}
	
	public WebElement getWebElement(String by, String locator) throws AutomationPlatformException {
		WebElement webElement;
		By byLocator = ObjectUtil.getLocator(by, locator);
		webElement = webDriver.findElement(byLocator);
		
		return webElement;
	}
	
	public List<WebElement> getWebElements(String by, String locator) throws AutomationPlatformException {
		List<WebElement> webElements;
		By byLocator = ObjectUtil.getLocator(by, locator);
		webElements = webDriver.findElements(byLocator);
		
		return webElements;
	}
	
	public void sendKeys(String by, String locator, String text) throws AutomationPlatformException {
		WebElement webElement = getWebElement(by, locator);
		webElement.sendKeys(text);
	}
	
	public void navigateTo(String url) {
		webDriver.navigate().to(url);
	}
	
	public void navigateAndWaitUntilPageLoad(String url) {
		webDriver.get(url);
	}
	
	public void switchToFrame(String by, String locator) throws AutomationPlatformException {
		WebElement webElement = getWebElement(by, locator);
		webDriver.switchTo().frame(webElement);
	}
	
	public void switchToFrame(int index) {
		webDriver.switchTo().frame(index);
	}
	
	public void switchToFrame(String nameOrId) {
		webDriver.switchTo().frame(nameOrId);
	}
}
