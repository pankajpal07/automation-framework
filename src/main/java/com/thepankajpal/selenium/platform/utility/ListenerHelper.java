package com.thepankajpal.selenium.platform.utility;

import static com.thepankajpal.selenium.platform.constants.Constants.DOLLAR;
import static com.thepankajpal.selenium.platform.constants.Constants.DOT;
import static com.thepankajpal.selenium.platform.constants.Constants.DURATION;
import static com.thepankajpal.selenium.platform.constants.Constants.FINISHED_AT;
import static com.thepankajpal.selenium.platform.constants.Constants.OVERALL_EXECUTION;
import static com.thepankajpal.selenium.platform.constants.Constants.REPORT_DATE_FORMAT;
import static com.thepankajpal.selenium.platform.constants.Constants.STARTED_AT;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.thepankajpal.selenium.platform.constants.Constants;
import com.thepankajpal.selenium.platform.model.Execution;
import com.thepankajpal.selenium.platform.model.PropertiesMap;

/**
 * Helper for the listerne class.
 * @author Pankaj Kumar Pal <pankajpalrd@gmail.com>
 * @version 1.0
 */
public class ListenerHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ListenerHelper.class);
	
	private static Date startDateTime;
    private static Date endDateTime;
    
    public static void setStartDateTime() {
    	startDateTime = new Date();
    }
    
    public static void readConfigurationFiles() {
    	String configDirectoryPath = getResourcesDirectoryPath() + "config";
    	File configDirectory = new File(configDirectoryPath);
    	List<File> configFiles = Arrays.asList(configDirectory.listFiles());
    	configFiles.forEach(configFile -> {
			PropertiesMap.loadProperties(configFile);
    	});
    }

	public static String getTestClassName(String name) {
		String[] packageArray = name.split(DOT);
		return packageArray[packageArray.length - 1];
	}
	
	public static void generateExcelReport(List<XmlSuite> xmlSuites, List<ISuite> suites) {
		endDateTime = new Date();
        String resultOutputDirectory = Constants.REPORTS_PATH + File.separatorChar + "Results.xlsx";
        if (new File(resultOutputDirectory).exists()) {
        	new File(resultOutputDirectory).mkdirs();
        }
        Map<String, Properties> timingAttributes = new HashMap<>();
        for(ISuite suite:suites) {
            Properties props = setTimingAttributes(suite);
            timingAttributes.put(suite.getName(), props);
        }
        
        timingAttributes.put(OVERALL_EXECUTION, setDurationAttributes(startDateTime, endDateTime));
        
        try {
        	XlsUtility.Excelwritting(resultOutputDirectory, Constants.executionMap, timingAttributes);
        } catch (IOException ioException) {
        	LOGGER.error("Error while creating excel report on path: {}. Exception is: {}", resultOutputDirectory, ioException);
        }
	}
	
	private static Properties setTimingAttributes(ISuite suite) {
        Map<String, ISuiteResult> results = suite.getResults();
        Date minStartDate = new Date();
        Date maxEndDate = null;

        for (Map.Entry<String, ISuiteResult> result : results.entrySet()) {
            ITestContext testContext = ((ISuiteResult) result.getValue()).getTestContext();
            Date startDate = testContext.getStartDate();
            Date endDate = testContext.getEndDate();
            if (minStartDate.after(startDate)) {
                minStartDate = startDate;
            }
            if ((maxEndDate == null) || (maxEndDate.before(endDate))) {
                maxEndDate = endDate != null ? endDate : startDate;
            }
        }

        if (maxEndDate == null) {
            maxEndDate = minStartDate;
        }
        Properties props = setDurationAttributes(minStartDate, maxEndDate);
        return props;
    }
	
	private static Properties setDurationAttributes(Date minStartDate, Date maxEndDate) {
        Properties attributes = new Properties();
        
        SimpleDateFormat format = new SimpleDateFormat(REPORT_DATE_FORMAT);
        String startTime = format.format(minStartDate);
        String endTime = format.format(maxEndDate);
        long duration = maxEndDate.getTime() - minStartDate.getTime();

        attributes.setProperty(STARTED_AT, startTime);
        attributes.setProperty(FINISHED_AT, endTime);
        attributes.setProperty(DURATION, Long.toString(duration));
        
        return attributes;
    }
	
	public static String getResourcesDirectoryPath() {
		String defaultConfigDirectoryPath = System.getProperty("user.dir") + File.separatorChar + "resources" + File.separatorChar;
		File defaultConfigDirectory = new File(defaultConfigDirectoryPath);
		if (defaultConfigDirectory.exists()) {
			return defaultConfigDirectoryPath;
		} else {
			String anotherConfigDirectoryPath = System.getProperty("user.dir") + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources" + File.separatorChar;
			return anotherConfigDirectoryPath;
		}
	}
	
	public static Execution getExecutionRecord(ITestResult iTestResult) {
    	String key = ListenerHelper.getTestClassName(iTestResult.getMethod().getTestClass().getName()) + DOLLAR + iTestResult.getMethod().getMethodName();
		Map<String, Execution> executionMap = Constants.executionMap;
        if (executionMap.containsKey(key)) {
            return executionMap.get(key);
        } else {
            return new Execution(key);
        }
    }
	
	/**
	 * @param millis
	 * @return
	 */
	public static Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
}
