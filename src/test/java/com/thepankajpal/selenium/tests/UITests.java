package com.thepankajpal.selenium.tests;

import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.thepankajpal.selenium.platform.actions.BaseActions;
import com.thepankajpal.selenium.platform.exception.AutomationPlatformException;
import com.thepankajpal.selenium.platform.validators.SoftAssertion;

public class UITests {
	
	@Test
	public void verifyInputString(ITestContext iTestContext) throws AutomationPlatformException {
		SoftAssertion softAssertion = new SoftAssertion();
		BaseActions.navigateAndWaitUntilPageLoad("https://www.tutorialspoint.com/index.htm");
		WebElement inputElement = BaseActions.getWebElement("tagname", "input");
		BaseActions.sendKeys(inputElement, "Selenium");
		String value = BaseActions.getAttribute(inputElement, "value");
        softAssertion.assertEquals(value, "Selenium");
        
	    softAssertion.assertAll();
	    iTestContext.setAttribute("assertion", softAssertion);
	}
	
	@Test
	public void verifyClick(ITestContext iTestContext) throws AutomationPlatformException {
		SoftAssertion softAssertion = new SoftAssertion();
		BaseActions.navigateAndWaitUntilPageLoad("https://accounts.lambdatest.com/register");
		BaseActions.click("xpath", "//a[text()='Sign In']");
		String pageTitle = BaseActions.getTitle();
		softAssertion.assertEquals(pageTitle, "Log in");
		
		softAssertion.assertAll();
		iTestContext.setAttribute("assertion", softAssertion);
	}
}
