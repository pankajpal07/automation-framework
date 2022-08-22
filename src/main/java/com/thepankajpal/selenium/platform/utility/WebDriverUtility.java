package com.thepankajpal.selenium.platform.utility;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverUtility {

	
	public static WebDriver getBrowserStackWebDriver() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName("chrome");
		capabilities.setCapability("buildName", "browserstack-build-1");
		capabilities.setCapability("sessionName", "suite_test");
		capabilities.setCapability("debug", true);
		capabilities.setCapability("browserstack.networkLogs", true);
		
        
		WebDriver driver = new RemoteWebDriver(new URL("http://pankajkumarpal_dqgCtj:3W8ryZNwqte2AQirisqi@hub-cloud.browserstack.com/wd/hub"), capabilities);
		
		return driver;
	}
}
