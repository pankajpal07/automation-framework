package com.thepankajpal.selenium.platform.listener;

import static com.thepankajpal.selenium.platform.constants.Constants.DOLLAR;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IExecutionListener;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentHtmlReporterConfiguration;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.thepankajpal.selenium.platform.constants.Constants;
import com.thepankajpal.selenium.platform.model.Execution;
import com.thepankajpal.selenium.platform.utility.ListenerHelper;
import com.thepankajpal.selenium.platform.validators.SoftAssertion;

/**
 * Listeners that will be triggered after some event that will complete or before start.
 * @author Pankaj Kumar Pal <pankajpalrd@gmail.com>
 * @version 1.0
 */
public class Listener extends TestListenerAdapter implements IReporter, IExecutionListener, ISuiteListener {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    public static ExtentReports extentReports;
	public static ExtentTest extentTest;
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    /**
	 * It will create and get instance of ExtentReports.
	 * 
	 * @return instance of ExtentReports
	 */
    public static ExtentReports getInstance() {
		if (extentReports == null) {
			createInstance();
		}

		return extentReports;
	}
    
    /**
	 * @param fileName The filename which will be created for extent reporting
	 * @return instance of ExtentReports.
	 */
    public static ExtentReports createInstance() {
    	String reportName = "extent_report";
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(Constants.EXTENT_REPORT_HTML);
		ExtentHtmlReporterConfiguration extentHtmlReporterConfiguration = new ExtentHtmlReporterConfiguration(
				htmlReporter);
		extentHtmlReporterConfiguration.setTheme(Theme.DARK);
		extentHtmlReporterConfiguration.setDocumentTitle(reportName);
		extentHtmlReporterConfiguration.setEncoding("UTF-8");
		extentHtmlReporterConfiguration.setReportName(reportName);

		extentReports = new ExtentReports();
		extentReports.attachReporter(htmlReporter);

		return extentReports;
    }
    
    public void onExecutionStart() {
    	LOGGER.info("Execution Started");
    	ListenerHelper.setStartDateTime();
    	ListenerHelper.readConfigurationFiles();
    	extentReports = getInstance();
    }
    
    public void onStart(ITestContext testContext) {
    	LOGGER.info("Starting Execution for suite: {}", testContext.getCurrentXmlTest().getName());
    	extentTest = extentReports.createTest(testContext.getCurrentXmlTest().getName());
		extentTest.assignAuthor("Pankaj Kumar Pal");
		extentTest.assignCategory("Demo Suite");
		parentTest.set(extentTest);
    }
    
    @Override
    public void beforeConfiguration(ITestResult testResult) {
    	LOGGER.info("Configuring {}", testResult.getName());
    	
    	LOGGER.info("Configured {}", testResult.getName());
    }

    @Override
    public void onConfigurationSkip(ITestResult testResult) {
    	LOGGER.info("Configuration Skipped for {}", testResult.getName());
    }
    
    @Override
    public void onConfigurationSuccess(ITestResult testResult) {
    	LOGGER.info("Configuration Success for {}", testResult.getName());
    }
    
    @Override
    public void onConfigurationFailure(ITestResult testResult) {
    	LOGGER.info("Configuration Failed for {}", testResult.getName());
    }
    
    @Override
    public void onTestStart(ITestResult iTestResult) {
    	ITestNGMethod testMethod = iTestResult.getMethod();
		LOGGER.info("Start Execution for testcase: {} $ {} $ {}",
				iTestResult.getTestContext().getName(), ListenerHelper.getTestClassName(testMethod.getTestClass().getName()),
				testMethod.getMethodName());
		Execution execution = ListenerHelper.getExecutionRecord(iTestResult);
		execution.setTestCaseName(testMethod.getMethodName());
		execution.setTestCaseId(testMethod.getDescription());
		
		String testId = iTestResult.getMethod().getConstructorOrMethod().getMethod()
				.getAnnotation(org.testng.annotations.Test.class).testName();
		testId = testId.isEmpty() ? "" : "[" + testId + "] ";
		extentTest = parentTest.get().createNode(testId + iTestResult.getMethod().getMethodName());
		test.set(extentTest);
    }
    
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
    	ITestNGMethod testMethod = iTestResult.getMethod();
		LOGGER.info("Started testcase skipped: {} $ {} $ {}",
				iTestResult.getTestContext().getName(), ListenerHelper.getTestClassName(testMethod.getTestClass().getName()),
				testMethod.getMethodName());
		extentTest.log(Status.SKIP, MarkupHelper
				.createLabel("Test : [" + iTestResult.getMethod().getMethodName() + "] : SKIPPED", ExtentColor.ORANGE));
		Execution execution = ListenerHelper.getExecutionRecord(iTestResult);
		execution.setStatus(Execution.Status.SKIP);
		onTestComplete(execution, iTestResult);
		LOGGER.info("Completed testcase skipped: {} $ {} $ {}",
				iTestResult.getTestContext().getName(), ListenerHelper.getTestClassName(testMethod.getTestClass().getName()),
				testMethod.getMethodName());
    }
    
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
    	ITestNGMethod testMethod = iTestResult.getMethod();
		LOGGER.info("Started testcase success: {} $ {} $ {}",
				iTestResult.getTestContext().getName(), ListenerHelper.getTestClassName(testMethod.getTestClass().getName()),
				testMethod.getMethodName());
		extentTest.log(Status.PASS, MarkupHelper
				.createLabel("Test : [" + iTestResult.getMethod().getMethodName() + "] :  PASSED", ExtentColor.GREEN));
		Execution execution = ListenerHelper.getExecutionRecord(iTestResult);
		execution.setStatus(Execution.Status.PASS);
		onTestComplete(execution, iTestResult);
		LOGGER.info("Completed testcase success: {} $ {} $ {}",
				iTestResult.getTestContext().getName(), ListenerHelper.getTestClassName(testMethod.getTestClass().getName()),
				testMethod.getMethodName());
    }
    
    @Override
    public void onTestFailure(ITestResult iTestResult) {
    	ITestNGMethod testMethod = iTestResult.getMethod();
		LOGGER.info("Started testcase failure: {} $ {} $ {}",
				iTestResult.getTestContext().getName(), ListenerHelper.getTestClassName(testMethod.getTestClass().getName()),
				testMethod.getMethodName());
		extentTest.log(Status.FAIL, MarkupHelper.createLabel(iTestResult.getThrowable().toString(), ExtentColor.RED));
		extentTest.log(Status.FAIL, MarkupHelper.createLabel("Test : [" + iTestResult.getMethod().getMethodName() + "] : FAILED", ExtentColor.RED));
		Execution execution = ListenerHelper.getExecutionRecord(iTestResult);
		execution.setStatus(Execution.Status.FAIL);
		onTestComplete(execution, iTestResult);
		LOGGER.info("Completed testcase failure: {} $ {} $ {}",
				iTestResult.getTestContext().getName(), ListenerHelper.getTestClassName(testMethod.getTestClass().getName()),
				testMethod.getMethodName());
    }
    
    public void onFinish(ISuite suite, ITestContext testContext) {
    	LOGGER.info("Execution Completed");
    	parentTest.get().getModel().setStartTime(ListenerHelper.getTime(testContext.getStartDate().getTime()));
		parentTest.get().getModel().setEndTime(ListenerHelper.getTime(testContext.getEndDate().getTime()));
		extentReports.flush();
    }
    
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
    	LOGGER.info("Generating Report");
    	ListenerHelper.generateExcelReport(xmlSuites, suites);
        
    	LOGGER.info("Generated Report");
    }
    
    public void onExecutionFinish() {
    	extentReports.flush();
    	LOGGER.info("Execution Completed");
    	File logFile = new File("automation.log");
    	logFile.renameTo(new File(Constants.LOG_FILE_PATH));
    }
    
    private void onTestComplete(Execution execution, ITestResult iTestResult) {
    	
    	String testcaseName = ListenerHelper.getTestClassName(iTestResult.getMethod().getTestClass().getName()) + DOLLAR + iTestResult.getMethod().getMethodName();
    	SoftAssertion softAssertion = (SoftAssertion) iTestResult.getTestContext().getAttribute("assertion");
    	execution.setTotalValidationPoints(softAssertion.getValidationCount());
    	execution.setFailedValidationPoints(softAssertion.getFailedValidationCount());
    	execution.setPassedValidationPoints(softAssertion.getPassedValidationCount());
    	Constants.executionMap.put(testcaseName, execution);
    }
    
    /**
	 * It will set the total execution time for the test case in report.
	 * 
	 * @param ITestResult
	 */
	public static void setExecutionTime(ITestResult result) {
		extentTest.getModel().setStartTime(ListenerHelper.getTime(result.getStartMillis()));
		extentTest.getModel().setEndTime(ListenerHelper.getTime(result.getEndMillis()));
	}
	
	/**
	 * It will add provided info on extent report on extent report.
	 * 
	 * @param details
	 */
	public static void info(String details) {
		extentTest.log(Status.INFO, MarkupHelper.createLabel(details, ExtentColor.WHITE));
	}

	/**
	 * It will add pass status on test step on extent report.
	 * 
	 * @param details
	 */
	public static void pass(String details) {
		extentTest.log(Status.PASS, MarkupHelper.createLabel(details, ExtentColor.WHITE));
	}

	/**
	 * It will add fail status on test step on extent report.
	 * 
	 * @param details
	 */
	public static void fail(String details) {
		extentTest.log(Status.FAIL, MarkupHelper.createLabel(details, ExtentColor.RED));
	}

	/**
	 * It will add skip status on test step on extent report.
	 * 
	 * @param details
	 */
	public static void skip(String details) {
		extentTest.log(Status.SKIP, MarkupHelper.createLabel(details, ExtentColor.ORANGE));
	}

	/**
	 * @param details Throwable object
	 */
	public static void fail(Throwable details) {
		extentTest.log(Status.FAIL, details);
	}

	/**
	 * It will add warning status on test step on extent report.
	 * 
	 * @param details
	 */
	public static void warning(String details) {
		extentTest.log(Status.WARNING, MarkupHelper.createLabel(details, ExtentColor.YELLOW));
	}
}
