package com.thepankajpal.selenium.platform.constants;

import java.io.File;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import com.thepankajpal.selenium.platform.model.Execution;
import com.thepankajpal.selenium.platform.model.PropertiesMap;

/**
 * It contains the Constants which are used in this project.
 * @author Pankaj Kumar Pal <pankajpalrd@gmail.com>
 * @version 1.0
 */
public class Constants {
	
	public static final String USER_DIR = "user.dir";

	public static final String DESIGN = "===================";
	public static final String DOT = "\\.";
	public static final String STARTED_AT = "started-at";
    public static final String FINISHED_AT = "finished-at";
    public static final String DURATION = "duration-ms";
    public static final String REPORT_DATE_FORMAT = "dd-MM-yyyy@HH-mm-ss-SSS";
    public static final String OVERALL_EXECUTION = "overallExecution";
    public static final String BASE_PATH = "basePath";
    public static final String DOLLAR = "$";
    public static final String API_BASE_URL = PropertiesMap.getProperty("api.base.url");
    public static final String TEST_RESULT_DIRECTORY_PATH = System.getProperty(USER_DIR) + File.separatorChar + "test-result";
    public static final String CURRENT_DAY_EXECUTION_REPORT = TEST_RESULT_DIRECTORY_PATH  + File.separator + LocalDateTime.now();
    
    public static final String REPORTS_PATH = CURRENT_DAY_EXECUTION_REPORT + File.separatorChar + "reports" + File.separatorChar;
    public static final String EXTENT_REPORT_HTML = CURRENT_DAY_EXECUTION_REPORT + File.separatorChar + "reports" + File.separatorChar + "execution-report.html";
    public static final String LOG_FILE_PATH = CURRENT_DAY_EXECUTION_REPORT + File.separatorChar + "reports" + File.separatorChar + "automation.log";
    
    public static Map<String, Execution> executionMap = new LinkedHashMap<String, Execution>();
}
