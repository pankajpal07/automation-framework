package com.thepankajpal.selenium.platform.utility;

import org.openqa.selenium.By;

import com.thepankajpal.selenium.platform.exception.AutomationPlatformException;

public class ObjectUtil {

	public static By getLocator(String by, String locator) throws AutomationPlatformException {
		By byLocator;
		switch (by) {
		case "xpath": byLocator = By.xpath(locator);
		break;
		case "css": byLocator = By.cssSelector(locator);
		break;
		case "id": byLocator = By.id(locator);
		break;
		case "classname": byLocator = By.className(locator);
		break;
		case "linktext": byLocator = By.linkText(locator);
		break;
		case "name": byLocator = By.name(locator);
		break;
		case "partiallinktext": byLocator = By.partialLinkText(locator);
		break;
		case "tagname": byLocator = By.tagName(locator);
		break;
		default: throw new AutomationPlatformException("No such locator with type:" + by);
		}
		
		return byLocator;
	}
}
