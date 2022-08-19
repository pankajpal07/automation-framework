package com.thepankajpal.selenium.platform.model;

public class Execution {

	private String sessionId;
    private Status status;
    private long executionTime;
    private String failureDetails;
    private String exception;
    private String testCaseId;
    private String testCaseName;
    private String priority;
    private String os;
    private String osVersion;
    private String browser;
    private String browserVersion;
    private String screenShotPath;
    private Integer totalValidationPoints;
    private Integer passedValidationPoints;
    private Integer failedValidationPoints;
    private String validationPointsPassPercent;
    private String validationPointsFailPercent;
    private String failedLocatorComponentName;
    private String failedLocatorElementName;
    private Status beforeMethodStatus;
    private Status afterMethodStatus;
    
    public Execution(String testcaseName) {
		this.testCaseName = testcaseName;
	}

    public String getFailedLocatorComponentName() {
		return failedLocatorComponentName;
	}

	public void setFailedLocatorComponentName(String failedLocatorComponentName) {
		this.failedLocatorComponentName = failedLocatorComponentName;
	}

	public String getFailedLocatorElementName() {
		return failedLocatorElementName;
	}

	public void setFailedLocatorElementName(String failedLocatorElementName) {
		this.failedLocatorElementName = failedLocatorElementName;
	}

    public String getScreenShotPath() {
        return screenShotPath;
    }

    public String getOsVersion() {

		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public void setScreenShotPath(String screenShotPath) {
        this.screenShotPath = screenShotPath;
    }

    public String getValidationPointsPassPercent() {
        return validationPointsPassPercent;
    }

    public void setValidationPointsPassPercent(String validationPointsPassPercent) {
        this.validationPointsPassPercent = validationPointsPassPercent;
    }

    public String getValidationPointsFailPercent() {
        return validationPointsFailPercent;
    }

    public void setValidationPointsFailPercent(String validationPointsFailPercent) {
        this.validationPointsFailPercent = validationPointsFailPercent;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public String getFailureDetails() {
        return failureDetails;
    }

    public void setFailureDetails(String failureDetails) {
        this.failureDetails = failureDetails;
    }

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Integer getTotalValidationPoints() {
        return totalValidationPoints;
    }

    public void setTotalValidationPoints(Integer totalValidationPoints) {
        this.totalValidationPoints = totalValidationPoints;
    }

    public Integer getPassedValidationPoints() {
        return passedValidationPoints;
    }

    public void setPassedValidationPoints(Integer passedValidationPoints) {
        this.passedValidationPoints = passedValidationPoints;
    }

    public Integer getFailedValidationPoints() {
        return failedValidationPoints;
    }

    public void setFailedValidationPoints(Integer failedValidationPoints) {
        this.failedValidationPoints = failedValidationPoints;
    }

    public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public Status getBeforeMethodStatus() {
		return beforeMethodStatus;
	}

	public void setBeforeMethodStatus(Status beforeMethodStatus) {
		this.beforeMethodStatus = beforeMethodStatus;
	}

	public Status getAfterMethodStatus() {
		return afterMethodStatus;
	}

	public void setAfterMethodStatus(Status afterMethodStatus) {
		this.afterMethodStatus = afterMethodStatus;
	}

    public enum Status {
        PASS, FAIL, SKIP
    }

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
