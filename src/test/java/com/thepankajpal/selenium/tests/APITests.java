package com.thepankajpal.selenium.tests;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.thepankajpal.selenium.platform.validators.SoftAssertion;
import com.thepankajpal.selenium.service.PostsService;

import io.restassured.response.Response;

public class APITests {

	@Test
	public void getPosts(ITestContext iTestContext) {
		SoftAssertion softAssertion = new SoftAssertion();
		
		Response response = PostsService.getPostsList();
		softAssertion.assertEquals(response.statusCode(), 200);
		
		softAssertion.assertAll();
		iTestContext.setAttribute("assertion", softAssertion);
	}
	
	@Test
	public void createPost(ITestContext iTestContext) {
		SoftAssertion softAssertion = new SoftAssertion();
		
		Response response = PostsService.createPost();
		softAssertion.assertEquals(response.statusCode(), 201);
		
		softAssertion.assertAll();
		iTestContext.setAttribute("assertion", softAssertion);
	}
}
