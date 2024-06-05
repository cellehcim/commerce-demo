package com.cellehcim.commercedemo;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import org.json.JSONArray;

@WebFluxTest
@ComponentScan(basePackages = { "com.cellehcim" })
public class WebFluxCartCreationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
	void createEmptyCartWithoutParameters() throws Exception {
        webTestClient.post()
                    .uri(TestConstants.ENDPOINT_URL)
                    .header("Content-Type", "application/json")
                    .bodyValue(BodyInserters.fromValue(new JSONObject()))
                    .exchange()
                    .expectStatus().isNotFound();
	}

    @Test
	void createEmptyCartWithValidCurrencyCodeAndCountry() throws Exception {
        JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();

        webTestClient.post()
                    .uri(TestConstants.ENDPOINT_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestParams.toString()))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath(TestConstants.ID_JSON_PATH_VALUE).isNotEmpty()
                    .jsonPath(TestConstants.LINE_ITEMS_JSON_PATH_VALUE).isEmpty()
                    .jsonPath(TestConstants.COUNTRY_JSON_PATH_VALUE).isEqualTo("US")
                    .jsonPath(TestConstants.CURRENCY_CODE_JSON_PATH_VALUE).isEqualTo("USD");
    }

    @Test
	void createEmptyCartWithEmptyLineItemArray() throws Exception {
        JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();
		JSONArray lineItemsJson = new JSONArray();
        requestParams.put("lineItemDetails", lineItemsJson);

        webTestClient.post()
                    .uri(TestConstants.ENDPOINT_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestParams.toString()))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath(TestConstants.ID_JSON_PATH_VALUE).isNotEmpty()
                    .jsonPath(TestConstants.LINE_ITEMS_JSON_PATH_VALUE).isEmpty()
                    .jsonPath(TestConstants.COUNTRY_JSON_PATH_VALUE).isEqualTo("US")
                    .jsonPath(TestConstants.CURRENCY_CODE_JSON_PATH_VALUE).isEqualTo("USD");
    }

    @Test
	void createCartWithOneLineItem() throws Exception {
        JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();

		JSONArray lineItemsJson = new JSONArray();
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject(TestConstants.ASHEN_RUG_SKU, 1));
		requestParams.put("lineItemDetails", lineItemsJson);

        webTestClient.post()
                    .uri(TestConstants.ENDPOINT_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestParams.toString()))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath(TestConstants.ID_JSON_PATH_VALUE).isNotEmpty()
                    .jsonPath(TestConstants.LINE_ITEMS_JSON_PATH_VALUE).isNotEmpty()
                    .jsonPath(TestConstants.COUNTRY_JSON_PATH_VALUE).isEqualTo("US")
                    .jsonPath(TestConstants.CURRENCY_CODE_JSON_PATH_VALUE).isEqualTo("USD");
    }

    @Test
    void createCartWithMoreThanOneLineItem() throws Exception {
        JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();

		JSONArray lineItemsJson = new JSONArray();
		
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject(TestConstants.ASHEN_RUG_SKU, 1));
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject(TestConstants.ART_DECO_COFFEE_TABLE_SKU, 1));
		requestParams.put("lineItemDetails", lineItemsJson);

        webTestClient.post()
                    .uri(TestConstants.ENDPOINT_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestParams.toString()))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath(TestConstants.ID_JSON_PATH_VALUE).isNotEmpty()
                    .jsonPath(TestConstants.LINE_ITEMS_JSON_PATH_VALUE).isNotEmpty()
                    .jsonPath(TestConstants.LINE_ITEMS_LENGTH_JSON_PATH_VALUE).isEqualTo(2)
                    .jsonPath(TestConstants.COUNTRY_JSON_PATH_VALUE).isEqualTo("US")
                    .jsonPath(TestConstants.CURRENCY_CODE_JSON_PATH_VALUE).isEqualTo("USD");
    }

    @Test
	void createCartWithAnInvalidSku() throws Exception {
		JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();

		JSONArray lineItemsJson = new JSONArray();
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject("ARG-5", 1));
		requestParams.put("lineItemDetails", lineItemsJson);

        webTestClient.post()
                    .uri(TestConstants.ENDPOINT_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestParams.toString()))
                    .exchange()
                    .expectStatus().isNotFound();
	}

    /*@Test
	void createCartWithEveryParamInLowercase() throws Exception {
        JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "usd"); 
		requestParams.put("countryCode", "us"); 

		JSONArray lineItemsJson = new JSONArray();
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject(TestConstants.ASHEN_RUG_SKU.toLowerCase(), 1));
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject(TestConstants.ART_DECO_COFFEE_TABLE_SKU.toLowerCase(), 1));
		requestParams.put("lineItemDetails", lineItemsJson);

        webTestClient.post()
                    .uri(TestConstants.ENDPOINT_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestParams.toString()))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath(TestConstants.ID_JSON_PATH_VALUE).isNotEmpty()
                    .jsonPath(TestConstants.LINE_ITEMS_JSON_PATH_VALUE).isNotEmpty()
                    .jsonPath(TestConstants.LINE_ITEMS_LENGTH_JSON_PATH_VALUE).isEqualTo(2)
                    .jsonPath(TestConstants.COUNTRY_JSON_PATH_VALUE).isEqualTo("US")
                    .jsonPath(TestConstants.CURRENCY_CODE_JSON_PATH_VALUE).isEqualTo("USD");
    }*/

    @Test
    void createCartWithOneLineItemZeroQuantity() throws Exception {
        JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();

		JSONArray lineItemsJson = new JSONArray();
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject(TestConstants.ASHEN_RUG_SKU, 0));
		requestParams.put("lineItemDetails", lineItemsJson);

        webTestClient.post()
                    .uri(TestConstants.ENDPOINT_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestParams.toString()))
                    .exchange()
                    .expectStatus().isNotFound();
    }

    @Test
    void createCartWithOneLineItemNegativeQuantity() throws Exception {
        JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();

		JSONArray lineItemsJson = new JSONArray();
		lineItemsJson.put(TestHelperMethods.createLineItemRequestObject(TestConstants.ASHEN_RUG_SKU, -1));
		requestParams.put("lineItemDetails", lineItemsJson);

        webTestClient.post()
                    .uri(TestConstants.ENDPOINT_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestParams.toString()))
                    .exchange()
                    .expectStatus().isNotFound();
    }
}
