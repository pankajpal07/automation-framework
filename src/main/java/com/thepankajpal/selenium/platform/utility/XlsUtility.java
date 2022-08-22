package com.thepankajpal.selenium.platform.utility;

import static com.thepankajpal.selenium.platform.constants.Constants.DURATION;
import static com.thepankajpal.selenium.platform.constants.Constants.FINISHED_AT;
import static com.thepankajpal.selenium.platform.constants.Constants.OVERALL_EXECUTION;
import static com.thepankajpal.selenium.platform.constants.Constants.STARTED_AT;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thepankajpal.selenium.platform.model.Execution;
import com.thepankajpal.selenium.platform.model.Execution.Status;
import com.thepankajpal.selenium.platform.model.ModuleMetric;
import com.thepankajpal.selenium.platform.model.PropertiesMap;

/**
 * This class contains functions to generating excel report after test
 * execution.
 * 
 * @author Pankaj Kumar Pal <pankajpalrd@gmail.com>
 * @version 1.0
 */
public class XlsUtility {

	private static final Logger LOGGER = LoggerFactory.getLogger(XlsUtility.class);
	private static final String BLANK = "";

	/**
	 * Method to generate excel report.
	 * 
	 * @param detailsTestCases      Test case Details Map for printing in Excel
	 *                              Sheet.
	 * @param resultOutputDirectory Location to generate Excel Report.
	 */
	public static File Excelwritting(String xlsFilePath, Map<String, Execution> executionDetails, Map<String, Properties> timingProperties) throws IOException {
		LOGGER.info("Writing the testcases result to xls file");

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFCellStyle headerStyle = getCellFontStyle(workbook, 14, false);
		XSSFCellStyle titleStyle = getCellFontStyle(workbook, 11, true);
		createSummarySheet(workbook, headerStyle, titleStyle);

		Object[] detailHeaderNames = { 
				"Module Name", "Flow Name", "Test Case Name", "Test Case ID", "Number Of Valdation Points", "Passed Valdation Points",
				"Failed Valdation Points", "Execution Time", "Status", "Error Log", "Failure Category"
		};

		String lastClassName = null;
		Map<String, ModuleMetric> moduleMetricMap = new LinkedHashMap<String, ModuleMetric>();
		for (String tcName : executionDetails.keySet()) {
			LOGGER.debug("Test Case Name Key - " + tcName);
			String[] testCaseNameArray = tcName.split("\\$");
			String moduleName = testCaseNameArray[0];
			String className = testCaseNameArray[1];
			Execution execution = executionDetails.get(tcName);
			String testCaseStatus = executionStatusToString(execution.getStatus());
			XSSFCellStyle cellColorStyle = getCellColorStyle(workbook, testCaseStatus, true);
			XSSFCellStyle cellNonWrapColorStyle = getCellColorStyle(workbook, testCaseStatus, false);
			XSSFSheet detailSheet = workbook.getSheet(moduleName);
			ModuleMetric moduleMetric = moduleMetricMap.get(moduleName);
			Row row = null;

			if (detailSheet == null) {
				moduleMetric = createModuleMetric();
				detailSheet = createDetailSheet(workbook, moduleName, detailHeaderNames, headerStyle, titleStyle);
				row = detailSheet.createRow(4);
				createCell(row, 1, cellColorStyle, moduleName);
				createCell(row, 2, cellColorStyle, className);
				row.setHeightInPoints(30);
				lastClassName = className;
			} else {
				row = detailSheet.createRow(detailSheet.getLastRowNum() + 1);
				createCell(row, 1, cellColorStyle, BLANK);
				if (lastClassName.equals(className)) {
					createCell(row, 2, cellColorStyle, BLANK);
				} else {
					moduleMetric.setFlowCount(moduleMetric.getFlowCount() + 1);
					createCell(row, 2, cellColorStyle, className);
					lastClassName = className;
				}
			}
			LOGGER.debug("TotalValidationPoints:" + execution.getTotalValidationPoints() + " PassedValidationPoints:"
					+ execution.getPassedValidationPoints() + " FailedValidationPoints:"
					+ execution.getFailedValidationPoints() + " ExecutionTime:" + execution.getExecutionTime()
					+ " Status:" + execution.getStatus());
			createCell(row, 3, cellColorStyle, execution.getTestCaseName());
			createCell(row, 4, cellColorStyle, execution.getTestCaseId());
			createCell(row, 5, cellColorStyle, execution.getTotalValidationPoints());
			createCell(row, 6, cellColorStyle, execution.getPassedValidationPoints());
			createCell(row, 7, cellColorStyle, execution.getFailedValidationPoints());
			createCell(row, 8, cellColorStyle, execution.getExecutionTime());
			createCell(row, 9, cellColorStyle, testCaseStatus);

			createCell(row, 10, cellNonWrapColorStyle, execution.getFailureDetails());
			createCell(row, 11, cellColorStyle, BLANK);

			moduleMetric.setTestCaseCount(moduleMetric.getTestCaseCount() + 1);
			moduleMetric
					.setValidationPointSum(moduleMetric.getValidationPointSum() + execution.getTotalValidationPoints());
			moduleMetric.setPassVPSum(moduleMetric.getPassVPSum() + execution.getPassedValidationPoints());
			moduleMetric.setFailVPSum(moduleMetric.getFailVPSum() + execution.getFailedValidationPoints());
			moduleMetric.setTotalExeTime(moduleMetric.getTotalExeTime() + execution.getExecutionTime());
			moduleMetric.setTotalPassCount(
					moduleMetric.getTotalPassCount() + (execution.getStatus() == Execution.Status.PASS ? 1 : 0));

			moduleMetricMap.put(moduleName, moduleMetric);
		}

		fillTotalOnDetailSheet(workbook, titleStyle, moduleMetricMap);
		fillSummarySheet(workbook, titleStyle, moduleMetricMap, timingProperties);

		File xlsFile = writeToFile(workbook, xlsFilePath);

		
		LOGGER.info("Excel report has been generated");
		return xlsFile;
	}

	private static void fillTotalOnDetailSheet(XSSFWorkbook workbook, XSSFCellStyle titleStyle,
			Map<String, ModuleMetric> moduleMetricMap) throws IOException {
		for (String moduleName : moduleMetricMap.keySet()) {
			XSSFSheet detailSheet = workbook.getSheet(moduleName);
			Row row = detailSheet.createRow(detailSheet.getLastRowNum() + 1);

			ModuleMetric moduleMetric = moduleMetricMap.get(moduleName);

			createCell(row, 0, titleStyle, "Grand Total");
			createCell(row, 1, titleStyle, String.valueOf(moduleMetric.getModuleCount()));
			createCell(row, 2, titleStyle, String.valueOf(moduleMetric.getFlowCount()));
			createCell(row, 3, titleStyle, String.valueOf(moduleMetric.getTestCaseCount()));
			createCell(row, 4, titleStyle, String.valueOf(moduleMetric.getTestCaseCount()));
			createCell(row, 5, titleStyle, String.valueOf(moduleMetric.getValidationPointSum()));
			createCell(row, 6, titleStyle, String.valueOf(moduleMetric.getPassVPSum()));
			createCell(row, 7, titleStyle, String.valueOf(moduleMetric.getFailVPSum()));
			createCell(row, 8, titleStyle, moduleMetric.getTotalExeTime());

			int failTcCount = moduleMetric.getTestCaseCount() - moduleMetric.getTotalPassCount();
			int automatedTcCount = moduleMetric.getTestCaseCount();
			double passPercent = ((double) (automatedTcCount - failTcCount) / (double) automatedTcCount) * 100;
			double failPercent = ((double) failTcCount / (double) automatedTcCount) * 100;
			LOGGER.info("Filling test case details in sheet : " + moduleName + " is completed");
		}
	}

	private static void fillSummarySheet(XSSFWorkbook workbook, XSSFCellStyle titleStyle,
			Map<String, ModuleMetric> moduleMetricMap, Map<String, Properties> timingProperties) throws IOException {
		int totalTCCount = 0;
		int totalPassCount = 0;
		long totalTime = 0;
		XSSFCellStyle wrapCellStyle = getCellWrapStyle(workbook);
		XSSFSheet summarySheet = workbook.getSheet("Summary");
		for (String moduleName : moduleMetricMap.keySet()) {
			ModuleMetric moduleMetric = moduleMetricMap.get(moduleName);

			Row row = summarySheet.createRow(summarySheet.getLastRowNum() + 1);
			int passTc = moduleMetric.getTotalPassCount();
			createCell(row, 1, wrapCellStyle, moduleName);
			createCell(row, 2, wrapCellStyle, moduleMetric.getTestCaseCount());
			createCell(row, 3, wrapCellStyle, moduleMetric.getTestCaseCount());
			createCell(row, 4, wrapCellStyle, passTc);
			createCell(row, 5, wrapCellStyle, moduleMetric.getTestCaseCount() - passTc);

			double passPercent = ((double) passTc / moduleMetric.getTestCaseCount()) * 100;
			double failPercent = ((double) (moduleMetric.getTestCaseCount() - passTc) / moduleMetric.getTestCaseCount())
					* 100;

			createCell(row, 6, wrapCellStyle, new DecimalFormat("##.##").format(passPercent) + "%");
			createCell(row, 7, wrapCellStyle, new DecimalFormat("##.##").format(failPercent) + "%");
			createCell(row, 8, wrapCellStyle, moduleMetric.getTotalExeTime());

			totalTCCount = totalTCCount + moduleMetric.getTestCaseCount();
			totalPassCount = totalPassCount + passTc;
			totalTime = totalTime + moduleMetric.getTotalExeTime();

		}

		Row totalRow = summarySheet.createRow(summarySheet.getLastRowNum() + 1);
		createCell(totalRow, 0, titleStyle, "Grand Total");
		createCell(totalRow, 1, titleStyle, moduleMetricMap.size());
		createCell(totalRow, 2, titleStyle, totalTCCount);
		createCell(totalRow, 3, titleStyle, totalTCCount);
		createCell(totalRow, 4, titleStyle, totalPassCount);
		createCell(totalRow, 5, titleStyle, totalTCCount - totalPassCount);

		double passPercent = ((double) totalPassCount / (double) totalTCCount) * 100;
		double failPercent = ((double) (totalTCCount - totalPassCount) / (double) totalTCCount) * 100;

		createCell(totalRow, 6, titleStyle, new DecimalFormat("##.##").format(passPercent) + "%");
		createCell(totalRow, 7, titleStyle, new DecimalFormat("##.##").format(failPercent) + "%");

		Properties executionTimingProps = timingProperties.get(OVERALL_EXECUTION);
		createRow(summarySheet, 2, titleStyle, wrapCellStyle, "ExecutionStartTime",
				executionTimingProps.getProperty(STARTED_AT));
		createRow(summarySheet, 1, titleStyle, wrapCellStyle, "ExecutionEndTime",
				executionTimingProps.getProperty(FINISHED_AT));

		createRow(summarySheet, 1, titleStyle, wrapCellStyle, "TotalExecutionTime",
				duration(Long.parseLong(executionTimingProps.getProperty(DURATION))));

		LOGGER.info("Filling test case summary details in summary sheet is completed");
	}

	private static void createSummarySheet(XSSFWorkbook workbook, XSSFCellStyle headerStyle, XSSFCellStyle titleStyle) {
		XSSFSheet summarySheet = workbook.createSheet("Summary");
		summarySheet.setColumnWidth(0, 8000);
		summarySheet.setColumnWidth(1, 6000);

		Object[] summaryCoulmnTitleNames = { 
			"Module Name", "Total Test cases", "Automated Test cases",	"Total Passed", "Total Failed",	"Pass Percentage", "Fail Percentage", "Execution Time" 
		};

		Row titleRow = summarySheet.createRow(0);
		Cell titleCell = titleRow.createCell(1);
		titleCell.setCellStyle(headerStyle);
		titleCell.setCellValue(
				String.format("%s Automation Report Summary", PropertiesMap.getProperty("product.name")));

		addCoulmnTitles(titleStyle, summarySheet, summaryCoulmnTitleNames);
		LOGGER.info("Created Base format for summary sheet");
	}

	private static XSSFSheet createDetailSheet(XSSFWorkbook workbook, String sheetName, Object[] detailHeaderNames,
			XSSFCellStyle headerStyle, XSSFCellStyle titleStyle) {
		XSSFSheet detailSheet = workbook.createSheet(sheetName);

		detailSheet.setColumnWidth(1, 3700);
		detailSheet.setColumnWidth(2, 3700);
		detailSheet.setColumnWidth(3, 9000);

		detailSheet.setColumnWidth(4, 3500);
		detailSheet.setColumnWidth(5, 2200);
		detailSheet.setColumnWidth(6, 2200);
		detailSheet.setColumnWidth(7, 2200);
		detailSheet.setColumnWidth(8, 3000);
		detailSheet.setColumnWidth(9, 3000);
		detailSheet.setColumnWidth(10, 3000);
		detailSheet.setColumnWidth(11, 2500);
		detailSheet.setColumnWidth(12, 2500);
		detailSheet.setColumnWidth(13, 9000);
		detailSheet.setColumnWidth(14, 2500);

		Row detailTitleRow = detailSheet.createRow(0);
		Cell detailTitleCell = detailTitleRow.createCell(1);
		detailTitleCell.setCellStyle(headerStyle);
		detailTitleCell.setCellValue(
				String.format("%s Automation Detail Report", PropertiesMap.getProperty("product.name")));

		addCoulmnTitles(titleStyle, detailSheet, detailHeaderNames);
		LOGGER.info("Created Base format for test case details sheet : " + sheetName);
		return detailSheet;
	}

	private static File writeToFile(XSSFWorkbook workbook, String filePath) throws IOException {
		File xlsFile = new File(filePath);
		xlsFile.mkdirs();
		if (xlsFile.exists()) {
			xlsFile.delete();
		}
		xlsFile.createNewFile();
		// Write the workbook in file system
		FileOutputStream out = new FileOutputStream(xlsFile);
		try {
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			LOGGER.error(
					"Exception while seraching Excel file containing testcase details [XlsUtility] >> writeToFile()"
							+ e.getMessage());
		}
		return xlsFile;
	}

	public static File createSessionIdReport(String xlsFilePath, Map<String, Execution> executionDetails)
			throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFCellStyle headerStyle = XlsUtility.getCellFontStyle(workbook, 14, false);
		XSSFCellStyle titleStyle = getCellFontStyle(workbook, 11, true);

		Object[] summaryCoulmnTitleNames = { 
			"Module Name", "Flow Name", "Test Case Name", "TestCase ID", "Session ID", "Test Case Status" 
		};
		String lastClassName = null;
		Map<String, ModuleMetric> moduleMetricMap = new LinkedHashMap<String, ModuleMetric>();
		for (String tcName : executionDetails.keySet()) {
			LOGGER.debug("Test Case Name Key - " + tcName);
			String[] testCaseNameArray = tcName.split("\\$");
			String moduleName = testCaseNameArray[0];
			String className = testCaseNameArray[1];
			Execution execution = executionDetails.get(tcName);
			String testCaseStatus = executionStatusToString(execution.getStatus());
			XSSFCellStyle cellColorStyle = getCellColorStyle(workbook, testCaseStatus, true);
			XSSFCellStyle cellNonWrapColorStyle = getCellColorStyle(workbook, testCaseStatus, false);
			XSSFSheet detailSheet = workbook.getSheet("SessionID");
			ModuleMetric moduleMetric = moduleMetricMap.get(moduleName);
			Row row = null;
			if (detailSheet == null) {
				moduleMetric = createModuleMetric();
				detailSheet = createSessionSheet(workbook, moduleName, summaryCoulmnTitleNames, headerStyle,
						titleStyle);
				row = detailSheet.createRow(4);
				createCell(row, 1, cellColorStyle, moduleName);
				createCell(row, 2, cellColorStyle, className);
				row.setHeightInPoints(30);
				lastClassName = className;
			} else {
				row = detailSheet.createRow(detailSheet.getLastRowNum() + 1);
				createCell(row, 1, cellColorStyle, BLANK);
				if (lastClassName.equals(className)) {
					createCell(row, 2, cellColorStyle, BLANK);
				} else {
					moduleMetric.setFlowCount(moduleMetric.getFlowCount() + 1);
					createCell(row, 2, cellColorStyle, className);
					lastClassName = className;
				}
			}
			LOGGER.debug(" Status:" + execution.getStatus());
			createCell(row, 3, cellColorStyle, execution.getTestCaseName());
			createCell(row, 4, cellColorStyle, execution.getTestCaseId());
			createCell(row, 5, cellColorStyle, execution.getSessionId());
			createCell(row, 6, cellColorStyle, testCaseStatus);
			moduleMetricMap.put(moduleName, moduleMetric);
		}
		File xlsFile = writeToFile(workbook, xlsFilePath);
		LOGGER.info("Excel report has been generated");
		return xlsFile;
	}

	private static XSSFSheet createSessionSheet(XSSFWorkbook workbook, String sheetName, Object[] detailHeaderNames,
			XSSFCellStyle headerStyle, XSSFCellStyle titleStyle) {
		XSSFSheet detailSheet = workbook.createSheet("SessionID");
		detailSheet.setColumnWidth(1, 3700);
		detailSheet.setColumnWidth(2, 3700);
		detailSheet.setColumnWidth(3, 9000);
		detailSheet.setColumnWidth(4, 3500);
		detailSheet.setColumnWidth(5, 10000);
		detailSheet.setColumnWidth(6, 2200);
		Row detailTitleRow = detailSheet.createRow(0);
		Cell detailTitleCell = detailTitleRow.createCell(1);
		detailTitleCell.setCellStyle(headerStyle);
		detailTitleCell.setCellValue(
				String.format("%s Automation Session ID Report", PropertiesMap.getProperty("product.name")));
		addCoulmnTitles(titleStyle, detailSheet, detailHeaderNames);
		LOGGER.info("Created Base format for test case details sheet : " + sheetName);
		return detailSheet;
	}

	private static ModuleMetric createModuleMetric() {
		ModuleMetric metric = new ModuleMetric();
		metric.setFlowCount(1);
		metric.setModuleCount(1);
		return metric;
	}

	private static void createRow(XSSFSheet sheet, int rowOffset, XSSFCellStyle titleStyle, XSSFCellStyle cellStyle,
			String title, String value) {
		Row row = sheet.createRow(sheet.getLastRowNum() + rowOffset);
		createCell(row, 0, titleStyle, title);
		createCell(row, 1, cellStyle, value);
	}

	private static void createCell(Row row, int index, XSSFCellStyle cellStyle, String value) {
		Cell cell = row.createCell(index);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	private static void createCell(Row row, int index, XSSFCellStyle cellStyle, Integer value) {
		if (value == null) {
			value = 0;
		}
		Cell cell = row.createCell(index);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	private static void createCell(Row row, int index, XSSFCellStyle cellStyle, Long value) {
		Cell cell = row.createCell(index);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	/**
	 * Method to print failed test case details in red color.
	 * 
	 * @param status Test case status.
	 * @param cell   Cell object name.
	 */
	private static XSSFCellStyle getCellColorStyle(XSSFWorkbook workbook, String status, boolean wraptext) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont cellFont = workbook.createFont();

		if (status == "Fail") {
			cellFont.setColor(XSSFFont.COLOR_RED);
		} else {
			cellFont.setColor(XSSFFont.DEFAULT_FONT_COLOR);
		}
		cellStyle.setFont(cellFont);
		cellStyle.setWrapText(wraptext);
		return cellStyle;
	}

	private static String executionStatusToString(Status status) {
		String executionStatus;
		if (status == Status.FAIL) {
			executionStatus = "Fail";
		} else if (status == Status.PASS) {
			executionStatus = "Pass";
		} else {
			executionStatus = "Skip";
		}
		return executionStatus;
	}

	private static XSSFCellStyle getCellFontStyle(XSSFWorkbook workbook, int font, boolean wraptext) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont cellFont = workbook.createFont();
//        cellFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		cellFont.setFontHeight(font);
		cellStyle.setFont(cellFont);
		cellStyle.setWrapText(wraptext);
		return cellStyle;
	}

	private static XSSFCellStyle getCellWrapStyle(XSSFWorkbook workbook) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(true);
		return cellStyle;
	}

	private static void addCoulmnTitles(XSSFCellStyle cellStyle, XSSFSheet sheet, Object[] columnTitleNames) {
		Row row = sheet.createRow(3);
		int cellnum = 1;
		for (Object titleName : columnTitleNames) {
			Cell cell = row.createCell(cellnum++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue((String) titleName);
		}
	}

	public static String duration(long timeInMs) {

		long s = (timeInMs / 1000) % 60;
		long m = ((timeInMs / (1000 * 60)) % 60);
		long h = ((timeInMs / (1000 * 60 * 60)) % 24);
		return String.format("%d:%02d:%02d", h, m, s);
	}
}
