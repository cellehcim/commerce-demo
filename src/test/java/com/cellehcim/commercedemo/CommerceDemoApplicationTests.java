package com.cellehcim.commercedemo;

import static org.assertj.core.api.Assertions.assertThat;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.json.JSONArray;

@SpringBootTest
class CommerceDemoApplicationTests {

	private final String ENDPOINT_URL = "http://localhost:8080/api/v1/carts";

	@Test
	void getExistingCart() throws Exception {
		String validCartId = "0cf86224-25f2-4e30-af5c-d91602a4f6bd";
		RestAssured.baseURI = ENDPOINT_URL;

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
		RestAssured.baseURI = ENDPOINT_URL;

		RequestSpecification request = RestAssured.given();
		
		Response response = request.get("/" + invalidCartId); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(404);
	}

	@Test
	void createEmptyCartWithoutParameters() throws Exception {
		RestAssured.baseURI = ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject(); 

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(404);
	}

	@Test
	void createEmptyCartWithValidCurrencyCodeAndCountry() throws Exception {
		RestAssured.baseURI = ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "USD"); 
		requestParams.put("countryCode", "US"); 

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(200);

		String bodyString = response.getBody().asString();
		assertThat(bodyString).contains("\"lineItems\":[]");
	}

	@Test
	void createEmptyCartWithEmptyLineItemArray() throws Exception {

		RestAssured.baseURI = ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "USD"); 
		requestParams.put("countryCode", "US"); 

		JSONArray lineItemsJson = new JSONArray();
		requestParams.put("lineItemSkus", lineItemsJson);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(200);

		String bodyString = response.getBody().asString();
		assertThat(bodyString).contains("\"lineItems\":[]");
	}

	@Test
	void createEmptyCartWithOneLineItem() throws Exception {

		RestAssured.baseURI = ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "USD"); 
		requestParams.put("countryCode", "US"); 

		JSONArray lineItemsJson = new JSONArray();
		lineItemsJson.put("ARG-56");
		requestParams.put("lineItemSkus", lineItemsJson);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(200);

		String bodyString = response.getBody().asString();

		// product name for the line item sku arg-56
		assertThat(bodyString).contains("Ashen Rug");
	}

	@Test
	void createEmptyCartWithMoreThanLineItem() throws Exception {

		RestAssured.baseURI = ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();


		JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "USD"); 
		requestParams.put("countryCode", "US"); 

		JSONArray lineItemsJson = new JSONArray();
		lineItemsJson.put("ARG-56");
		lineItemsJson.put("ADCT-01");
		requestParams.put("lineItemSkus", lineItemsJson);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(200);

		String bodyString = response.getBody().asString();

		// product names for the line item skus included
		assertThat(bodyString).contains("Ashen Rug", "Coffee Table");
	}

	@Test
	void createCartWithAnInvalidSku() throws Exception {

		RestAssured.baseURI = ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();


		JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "USD"); 
		requestParams.put("countryCode", "US"); 

		JSONArray lineItemsJson = new JSONArray();
		lineItemsJson.put("arg-56");
		lineItemsJson.put("ADCT");
		requestParams.put("lineItemSkus", lineItemsJson);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(404);
	}

	@Test
	void createCartWithEveryParamInLowercase() throws Exception {

		RestAssured.baseURI = ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();


		JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "usd"); 
		requestParams.put("countryCode", "us"); 

		JSONArray lineItemsJson = new JSONArray();
		lineItemsJson.put("arg-56");
		lineItemsJson.put("adct-01");
		requestParams.put("lineItemSkus", lineItemsJson);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(200);

		String bodyString = response.getBody().asString();

		// product names for the line item skus included
		assertThat(bodyString).contains("Ashen Rug", "Coffee Table");
	}

}
