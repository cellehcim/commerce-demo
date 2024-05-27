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
class CartCreationTests {

	@Test
	void createEmptyCartWithoutParameters() throws Exception {
		RestAssured.baseURI = TestConstants.ENDPOINT_URL; 
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
		RestAssured.baseURI = TestConstants.ENDPOINT_URL; 
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

		RestAssured.baseURI = TestConstants.ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "USD"); 
		requestParams.put("countryCode", "US"); 

		JSONArray lineItemsJson = new JSONArray();
		requestParams.put("lineItemDetail", lineItemsJson);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(200);

		String bodyString = response.getBody().asString();
		assertThat(bodyString).contains("\"lineItems\":[]");
	}

	@Test
	void createCartWithOneLineItem() throws Exception {

		RestAssured.baseURI = TestConstants.ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "USD"); 
		requestParams.put("countryCode", "US"); 

		JSONArray lineItemsJson = new JSONArray();
		JSONObject lineItem = new JSONObject();
		lineItem.put("sku", "ARG-56");
		lineItem.put("quantity", (long) 1);
		lineItemsJson.put(lineItem);

		requestParams.put("lineItemDetails", lineItemsJson);

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
	void createCartWithMoreThanLineItem() throws Exception {

		RestAssured.baseURI = TestConstants.ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();


		JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "USD"); 
		requestParams.put("countryCode", "US"); 

		JSONArray lineItemsJson = new JSONArray();
		
		JSONObject lineItemOne = new JSONObject();
		lineItemOne.put("sku", "ARG-56");
		lineItemOne.put("quantity", (long) 1);
		lineItemsJson.put(lineItemOne);

		JSONObject lineItemTwo = new JSONObject();
		lineItemTwo.put("sku", "ADCT-01");
		lineItemTwo.put("quantity", (long) 1);
		lineItemsJson.put(lineItemTwo);

		requestParams.put("lineItemDetails", lineItemsJson);

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

		RestAssured.baseURI = TestConstants.ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();


		JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "USD"); 
		requestParams.put("countryCode", "US"); 

		JSONArray lineItemsJson = new JSONArray();
		JSONObject lineItem = new JSONObject();
		lineItem.put("sku", "ARG-5");
		lineItem.put("quantity", (long) 1);
		lineItemsJson.put(lineItem);

		requestParams.put("lineItemDetails", lineItemsJson);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(404);
	}

	@Test
	void createCartWithEveryParamInLowercase() throws Exception {

		RestAssured.baseURI = TestConstants.ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();


		JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "usd"); 
		requestParams.put("countryCode", "us"); 

		JSONArray lineItemsJson = new JSONArray();

		JSONObject lineItemOne = new JSONObject();
		lineItemOne.put("sku", "arg-56");
		lineItemOne.put("quantity", (long) 1);
		lineItemsJson.put(lineItemOne);

		JSONObject lineItemTwo = new JSONObject();
		lineItemTwo.put("sku", "adct-01");
		lineItemTwo.put("quantity", (long) 1);
		lineItemsJson.put(lineItemTwo);
		requestParams.put("lineItemDetails", lineItemsJson);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(200);

		String bodyString = response.getBody().asString();

		// product names for the line item skus included
		assertThat(bodyString).contains("Ashen Rug", "Coffee Table");
	}

	void createCartWithOneLineItemZeroQuantity() throws Exception {

		RestAssured.baseURI = TestConstants.ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "USD"); 
		requestParams.put("countryCode", "US"); 

		JSONArray lineItemsJson = new JSONArray();
		JSONObject lineItem = new JSONObject();
		lineItem.put("sku", "ARG-56");
		lineItem.put("quantity", (long) 0);
		lineItemsJson.put(lineItem);

		requestParams.put("lineItemDetails", lineItemsJson);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(404);
	}

	void createCartWithOneLineItemNegativeQuantity() throws Exception {

		RestAssured.baseURI = TestConstants.ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "USD"); 
		requestParams.put("countryCode", "US"); 

		JSONArray lineItemsJson = new JSONArray();
		JSONObject lineItem = new JSONObject();
		lineItem.put("sku", "ARG-56");
		lineItem.put("quantity", (long) -1);
		lineItemsJson.put(lineItem);

		requestParams.put("lineItemDetails", lineItemsJson);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(404);
	}
}
