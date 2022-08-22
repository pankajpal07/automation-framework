package com.thepankajpal.selenium.platform.actions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.thepankajpal.selenium.platform.exception.AutomationPlatformException;
import com.thepankajpal.selenium.platform.listener.AnnotationImplementation;
import com.thepankajpal.selenium.platform.listener.Listener;
import com.thepankajpal.selenium.platform.utility.ObjectUtil;

public class BaseActions {
	
	private static WebDriver webDriver;
	
	static {
		webDriver = AnnotationImplementation.getWebDriver();
	}
	
	public static String getTitle() {
		return webDriver.getTitle();
	}
	
	public static void click(String by, String locator) throws AutomationPlatformException {
		WebElement webElement = getWebElement(by, locator);
		Listener.info("Clicking on element " + by + " : " + locator);
		webElement.click();
	}
	
	public static void mouseClick(String by, String locator) throws AutomationPlatformException {
		WebElement webElement = getWebElement(by, locator);
		Actions actions = new Actions(webDriver);
		Listener.info("Clicking on element using mouse " + by + " : " + locator);
		actions.moveToElement(webElement).click();
	}
	
	public static WebElement getWebElement(String by, String locator) throws AutomationPlatformException {
		WebElement webElement;
		By byLocator = ObjectUtil.getLocator(by, locator);
		Listener.info("Finding element " + by + " : " + locator);
		webElement = webDriver.findElement(byLocator);
		
		return webElement;
	}
	
	public static List<WebElement> getWebElements(String by, String locator) throws AutomationPlatformException {
		List<WebElement> webElements;
		By byLocator = ObjectUtil.getLocator(by, locator);
		Listener.info("Finding elements " + by + " : " + locator);
		webElements = webDriver.findElements(byLocator);
		
		return webElements;
	}
	
	public static void sendKeys(String by, String locator, String text) throws AutomationPlatformException {
		WebElement webElement = getWebElement(by, locator);
		Listener.info("Sending value `" + text + "` to element " + by + " : " + locator);
		webElement.sendKeys(text);
	}
	
	public static void sendKeys(WebElement webElement, String text) throws AutomationPlatformException {
		webElement.sendKeys(text);
	}
	
	public static String getAttribute(WebElement webElement, String attribute) {
		return webElement.getAttribute(attribute);
	}
	
	public static void navigateTo(String url) {
		webDriver.navigate().to(url);
	}
	
	public static void navigateAndWaitUntilPageLoad(String url) {
		Listener.info("Navigating to URL: " + url);
		webDriver.get(url);
	}
	
	public static void switchToFrame(String by, String locator) throws AutomationPlatformException {
		WebElement webElement = getWebElement(by, locator);
		webDriver.switchTo().frame(webElement);
	}
	
	public static void switchToFrame(int index) {
		webDriver.switchTo().frame(index);
	}
	
	public static void switchToFrame(String nameOrId) {
		webDriver.switchTo().frame(nameOrId);
	}
	
	public static void switchToNewTab(String nameOrHandle) {
		webDriver.switchTo().window(nameOrHandle);
	}
}
