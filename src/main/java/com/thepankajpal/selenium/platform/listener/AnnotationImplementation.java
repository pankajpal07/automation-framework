package com.thepankajpal.selenium.platform.listener;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

/**
 * Implementation for TestNG annotations. 
 * Add this class in the xml inside the suite tag.
 * // TODO: Add format of tag to be added in xml 
 * @author Pankaj Kumar Pal <pankajpalrd@gmail.com>
 * @version 1.0
 */
public class AnnotationImplementation {
	
	/**
	 * This method will be triggered before the execution of suite in xml starts.
	 */
	@BeforeSuite
	public void beforeSuite() {
		
	}
	
	/**
	 * This method will be triggered before the execution of test in xml starts.
	 */
	@BeforeTest
	public void beforeTest() {
		
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
		
	}
	
	/**
	 * This method will be triggered after the execution of suite in xml completes.
	 */
	@AfterSuite
	public void afterSuite() {
		
	}

}
