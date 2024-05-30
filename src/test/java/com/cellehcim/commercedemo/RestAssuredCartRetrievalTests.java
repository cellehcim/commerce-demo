package com.cellehcim.commercedemo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredCartRetrievalTests {
    @Test
	void getExistingCart() throws Exception {
		RestAssured.baseURI = TestConstants.ENDPOINT_URL;

		RequestSpecification request = RestAssured.given();
		
		Response response = request.get("/" + TestConstants.VALID_CART_ID); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(200);

		String bodyString = response.getBody().asString();
		assertThat(bodyString).contains(TestConstants.VALID_CART_ID);
	}

	@Test 
	void getNonExistentCart() throws Exception {
		RestAssured.baseURI = TestConstants.ENDPOINT_URL;

		RequestSpecification request = RestAssured.given();
		
		Response response = request.get("/" + TestConstants.INVALID_CART_ID); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(404);
	}
}
