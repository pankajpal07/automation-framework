package com.thepankajpal.selenium.service.rest;

import java.util.Objects;

import com.thepankajpal.selenium.platform.listener.Listener;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {

	private static final RequestSpecification request;

	static {
		request = RestAssured.given();
		request.header("Content-Type", "application/json");
	}
	
	public static Response submitRequest(String endpoint, RequestType requestType, Object... body) {
		Response response = null;

		switch (requestType) {
		case DELETE:
			response = request.delete(endpoint);
			break;
		case GET:
			response = request.get(endpoint);
			break;
		case POST:
			request.body((String) body[0]);
			response = request.post(endpoint);
			break;
		case PUT:
			request.body((String) body[0]);
			response = request.put(endpoint);
			break;
		case PATCH:
			request.body((String) body[0]);
			response = request.put(endpoint);
			break;
		default:
			break;
		}
		if (Objects.nonNull(body[0]) && !body[0].toString().isBlank()) {
			Listener.info("Request: " + body[0].toString());
		}
		Listener.info("Status: " + response.getStatusCode());
		Listener.info("Response: " + response.prettyPrint());

		return response;
	}
}
