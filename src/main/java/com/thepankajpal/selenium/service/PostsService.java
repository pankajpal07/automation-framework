package com.thepankajpal.selenium.service;

import com.thepankajpal.selenium.platform.constants.Constants;
import com.thepankajpal.selenium.service.rest.RequestType;
import com.thepankajpal.selenium.service.rest.RestClient;

import io.restassured.response.Response;

public class PostsService {

	public static Response getPostsList() {
		
		String endpoint = Constants.API_BASE_URL + "posts";
		Response response = RestClient.submitRequest(endpoint, RequestType.GET, "");
		return response;
	}
	
	public static Response createPost() {
		String endpoint = Constants.API_BASE_URL + "posts";
		String body = "{\"title\":\"foo\",\"body\": \"bar\",\"userId\": 1}";
		Response response = RestClient.submitRequest(endpoint, RequestType.POST, body);
		return response;
	}
}
