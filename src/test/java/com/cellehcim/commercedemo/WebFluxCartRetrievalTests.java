package com.cellehcim.commercedemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
@ComponentScan(basePackages = { "com.cellehcim" })
public class WebFluxCartRetrievalTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void getExistingCart() {
        webTestClient.get().uri(TestConstants.ENDPOINT_URL + "/" + TestConstants.VALID_CART_ID)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.id").isNotEmpty()
        .jsonPath("$.id").isEqualTo(TestConstants.VALID_CART_ID);

    }

    @Test
    void getNonExistentCart() {
        webTestClient.get().uri(TestConstants.ENDPOINT_URL + "/" + TestConstants.INVALID_CART_ID)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isNotFound();
    }
}
