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
class RestAssuredCartCreationTests {

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

		JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();

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

		JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();

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

		JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();

		JSONArray lineItemsJson = new JSONArray();
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject(TestConstants.ASHEN_RUG_SKU, 1));
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

		JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();

		JSONArray lineItemsJson = new JSONArray();
		
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject(TestConstants.ASHEN_RUG_SKU, 1));
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject(TestConstants.ART_DECO_COFFEE_TABLE_SKU, 1));
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

		JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();

		JSONArray lineItemsJson = new JSONArray();
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject("ARG-5", 1));
		requestParams.put("lineItemDetails", lineItemsJson);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(404);
	}

	/*@Test
	void createCartWithEveryParamInLowercase() throws Exception {
		RestAssured.baseURI = TestConstants.ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "usd"); 
		requestParams.put("countryCode", "us"); 

		JSONArray lineItemsJson = new JSONArray();
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject(TestConstants.ASHEN_RUG_SKU.toLowerCase(), 1));
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject(TestConstants.ART_DECO_COFFEE_TABLE_SKU.toLowerCase(), 1));
		requestParams.put("lineItemDetails", lineItemsJson);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(200);

		String bodyString = response.getBody().asString();

		// product names for the line item skus included
		assertThat(bodyString).contains("Ashen Rug", "Coffee Table");
	}*/

	void createCartWithOneLineItemZeroQuantity() throws Exception {
		RestAssured.baseURI = TestConstants.ENDPOINT_URL; 
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();

		JSONArray lineItemsJson = new JSONArray();
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject(TestConstants.ASHEN_RUG_SKU.toLowerCase(), 0));
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

		JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();

		JSONArray lineItemsJson = new JSONArray();
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject(TestConstants.ASHEN_RUG_SKU.toLowerCase(), -1));

		requestParams.put("lineItemDetails", lineItemsJson);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		
		Response response = request.post(""); 
		int statusCode = response.getStatusCode(); 

		assertThat(statusCode).isEqualTo(404);
	}
}
