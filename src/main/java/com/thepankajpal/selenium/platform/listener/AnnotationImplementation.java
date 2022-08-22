package com.thepankajpal.selenium.platform.listener;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.thepankajpal.selenium.platform.utility.WebDriverUtility;

/**
 * Implementation for TestNG annotations. 
 * Add this class in the xml inside the suite tag.
 * // TODO: Add format of tag to be added in xml 
 * @author Pankaj Kumar Pal <pankajpalrd@gmail.com>
 * @version 1.0
 */
public class AnnotationImplementation {
	
	private static ThreadLocal<WebDriver> webDriverCache = new ThreadLocal<WebDriver>();
	
	/**
	 * This method will be triggered before the execution of suite in xml starts.
	 * @throws MalformedURLException 
	 */
	@BeforeSuite
	public void beforeSuite() throws MalformedURLException {
		
	}
	
	/**
	 * This method will be triggered before the execution of test in xml starts.
	 * @throws MalformedURLException 
	 */
	@BeforeTest
	public void beforeTest() throws MalformedURLException {
		WebDriver webDriver = WebDriverUtility.getBrowserStackWebDriver();
		webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		webDriver.manage().window().maximize();
		webDriverCache.set(webDriver);
	}
	
	/**
	 * This method will be triggered before the execution of test class in xml starts.
	 */
	@BeforeClass
	public void beforeClass() {
		
	}
	
	
	/**
	 * This method will be triggered before the execution of each test-case.
	 */
	@BeforeMethod
	public void beforeMethod() {
		
	}
	
	/**
	 * This method will be triggered after the execution of each test-case completes.
	 */
	@AfterMethod
	public void afterMethod() {
		
	}
	
	/**
	 * This method will be triggered after the execution of test class in xml completes.
	 */
	@AfterClass
	public void afterClass() {
		
	}
	
	/**
	 * This method will be triggered after the execution of test in xml completes.
	 */
	@AfterTest
	public void afterTest() {
		WebDriver webDriver = webDriverCache.get();
		webDriver.close();
		webDriver.quit();
	}
	
	/**
	 * This method will be triggered after the execution of suite in xml completes.
	 */
	@AfterSuite
	public void afterSuite() {
		
	}
	
	public static WebDriver getWebDriver() {
		return webDriverCache.get();
	}

}
