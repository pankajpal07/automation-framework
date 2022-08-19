package com.thepankajpal.selenium.platform.validators;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import com.thepankajpal.selenium.platform.model.TestValidation;

public class SoftAssertion extends Assertion {

	private static final Character OPENING_CHARACTER = '[';
	private static final Character CLOSING_CHARACTER = ']';

	private static final String ASSERT_LEFT = "expected " + OPENING_CHARACTER;
	private static final String ASSERT_MIDDLE = CLOSING_CHARACTER + " but found " + OPENING_CHARACTER;
	private static final String ASSERT_RIGHT = Character.toString(CLOSING_CHARACTER);

	// LinkedHashMap to preserve the order
	private final Map<AssertionError, IAssert<?>> errors = Maps.newLinkedHashMap();
	private final Map<String, IAssert<?>> success = Maps.newLinkedHashMap();
	private int successCount;
	private int failureCount;
	private String passValidationMessage;
	private String failValidationMessage;
	private int validationPoint;
	private String validation;
	private List<TestValidation> testValidations = new ArrayList<>();

	private enum Status {
		PASS, FAIL
	}

	@Override
	protected void doAssert(IAssert<?> a) {
		onBeforeAssert(a);

		TestValidation testValidation = new TestValidation();
		testValidation.setActualResult(nonNull(a.getActual()) ? a.getActual().toString() : null);
		testValidation.setExpectedResult(nonNull(a.getExpected()) ? a.getExpected().toString() : null);
		testValidation.setValidationPoint(getValidationPoint());
		testValidation.setValidation(getValidation());
		try {
			a.doAssert();
			onAssertSuccess(a);
			testValidation.setValidationStatus(Status.PASS.name());
			testValidation.setValidationMessage(a.getMessage());
			success.put(format(a.getActual(), a.getExpected(), getPassValidationMessage()), a);
		} catch (AssertionError ex) {
			testValidation.setValidationMessage(ex.getMessage());
			testValidation.setValidationStatus(Status.FAIL.name());
			onAssertFailure(a, ex);
			errors.put(ex, a);
		} finally {
			testValidations.add(testValidation);
			onAfterAssert(a);
		}
	}

	public void assertAll() {
		if (!errors.isEmpty()) {
			StringBuilder sb = new StringBuilder("The following validations failed:");
			boolean first = true;
			for (Map.Entry<AssertionError, IAssert<?>> ae : errors.entrySet()) {
				if (first) {
					first = false;
				} else {
					sb.append(",");
				}
				sb.append("\n\t");
				sb.append(ae.getKey().getMessage());
			}
			throw new AssertionError(sb.toString());
		}
	}

	public String getSuccessMessage() {
		StringBuilder sb = new StringBuilder("The following validations passed:");
		boolean first = true;
		for (Map.Entry<String, IAssert<?>> sm : success.entrySet()) {
			if (first) {
				first = false;
			} else {
				sb.append(",");
			}
			sb.append("\n\t");
			sb.append(sm.getKey());
		}
		return sb.toString();
	}

	@Override
	public void onAssertSuccess(IAssert<?> assertCommand) {
		successCount++;
	}

	@Override
	public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
		failureCount++;
	}

	public int getValidationCount() {
		return failureCount + successCount;
	}

	public int getPassedValidationCount() {
		return successCount;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public int getFailedValidationCount() {
		return failureCount;
	}

	private String getPassValidationMessage() {
		return passValidationMessage;
	}

	private String getFailValidationMessage() {
		return failValidationMessage;
	}

	public void setPassValidationMessage(String passValidationMessage) {
		this.passValidationMessage = passValidationMessage;
	}

	public void setFailValidationMessage(String failValidationMessage) {
		this.failValidationMessage = failValidationMessage;
	}

	public int getValidationPoint() {
		return validationPoint;
	}

	public void setValidationPoint(int validationPoint) {
		this.validationPoint = validationPoint;
	}

	public List<TestValidation> getTestValidations() {
		return testValidations;
	}

	public void setTestValidations(List<TestValidation> resultDtos) {
		this.testValidations = resultDtos;
	}

	private String format(Object actual, Object expected, String message) {
		String formatted = "";
		if (null != message) {
			formatted = message + " ";
		}

		return formatted + ASSERT_LEFT + expected + ASSERT_MIDDLE + actual + ASSERT_RIGHT;
	}
}