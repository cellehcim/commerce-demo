package com.cellehcim.commercedemo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CartRetrievalTests {
    @Test
	void getExistingCart() throws Exception {
		String validCartId = "0cf86224-25f2-4e30-af5c-d91602a4f6bd";
		RestAssured.baseURI = TestConstants.ENDPOINT_URL;

		RequestSpecification request = RestAssured.given();
		
		Response response = request.get("/" + validCartId); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(200);

		String bodyString = response.getBody().asString();
		assertThat(bodyString).contains("0cf86224-25f2-4e30-af5c-d91602a4f6bd");
	}

	@Test 
	void getNonExistentCart() throws Exception {
		String invalidCartId = "0cf86224-25f2-4e30-af5c-d91602a4f6b";
		RestAssured.baseURI = TestConstants.ENDPOINT_URL;

		RequestSpecification request = RestAssured.given();
		
		Response response = request.get("/" + invalidCartId); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(404);
	}
}
