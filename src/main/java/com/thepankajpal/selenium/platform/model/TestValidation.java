package com.thepankajpal.selenium.platform.model;

public class TestValidation {

	private int validationPoint;

	private String actualResult;

	private String expectedResult;

	private String validationMessage;

	private String validationStatus;

	private String validation;

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public int getValidationPoint() {
		return validationPoint;
	}

	public void setValidationPoint(int validationPoint) {
		this.validationPoint = validationPoint;
	}

	public String getActualResult() {
		return actualResult;
	}

	public void setActualResult(String actualResult) {
		this.actualResult = actualResult;
	}

	public String getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

	public String getValidationStatus() {
		return validationStatus;
	}

	public void setValidationStatus(String validationStatus) {
		this.validationStatus = validationStatus;
	}
}