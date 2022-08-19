package com.thepankajpal.selenium.platform.model;

public class ModuleMetric {

	private int moduleCount;

	public int getModuleCount() {
		return moduleCount;
	}

	public void setModuleCount(int moduleCount) {
		this.moduleCount = moduleCount;
	}

	private int flowCount;
	private int testCaseCount;
	private int validationPointSum;
	private int passVPSum;
	private int failVPSum;
	private int totalTCCount;
	private int automatedTCCount;
	private int totalPassCount;

	private long totalTime;
	private long totalExeTime;

	public int getFlowCount() {
		return flowCount;
	}

	public void setFlowCount(int flowCount) {
		this.flowCount = flowCount;
	}

	public int getTestCaseCount() {
		return testCaseCount;
	}

	public void setTestCaseCount(int testCaseCount) {
		this.testCaseCount = testCaseCount;
	}

	public int getValidationPointSum() {
		return validationPointSum;
	}

	public void setValidationPointSum(int validationPointSum) {
		this.validationPointSum = validationPointSum;
	}

	public int getPassVPSum() {
		return passVPSum;
	}

	public void setPassVPSum(int passVPSum) {
		this.passVPSum = passVPSum;
	}

	public int getFailVPSum() {
		return failVPSum;
	}

	public void setFailVPSum(int failVPSum) {
		this.failVPSum = failVPSum;
	}

	public int getTotalTCCount() {
		return totalTCCount;
	}

	public void setTotalTCCount(int totalTCCount) {
		this.totalTCCount = totalTCCount;
	}

	public int getAutomatedTCCount() {
		return automatedTCCount;
	}

	public void setAutomatedTCCount(int automatedTCCount) {
		this.automatedTCCount = automatedTCCount;
	}

	public int getTotalPassCount() {
		return totalPassCount;
	}

	public void setTotalPassCount(int totalPassCount) {
		this.totalPassCount = totalPassCount;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public long getTotalExeTime() {
		return totalExeTime;
	}

	public void setTotalExeTime(long totalExeTime) {
		this.totalExeTime = totalExeTime;
	}
}
