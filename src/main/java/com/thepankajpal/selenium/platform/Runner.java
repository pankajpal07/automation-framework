package com.thepankajpal.selenium.platform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestNGListener;
import org.testng.TestNG;

import com.thepankajpal.selenium.platform.constants.Constants;
import com.thepankajpal.selenium.platform.listener.Listener;

public class Runner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);
	
	public static void runSuite(List<String> suiteXmls) {
        try {
        	int suiteThreadSize = suiteXmls.size();
        	String outputDirectory = Constants.REPORTS_PATH + "test-output" + File.separatorChar;
            List<Class<? extends ITestNGListener>> listenerClasses = new ArrayList<>();
            listenerClasses.add(Listener.class);

            TestNG testng = new TestNG();
            testng.setTestSuites(suiteXmls);
            testng.setOutputDirectory(outputDirectory);
            testng.setSuiteThreadPoolSize(suiteThreadSize);
            testng.setListenerClasses(listenerClasses);
            
            LOGGER.info("Running suite XML : {} and generating output at : {}", suiteXmls, outputDirectory);
            testng.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}