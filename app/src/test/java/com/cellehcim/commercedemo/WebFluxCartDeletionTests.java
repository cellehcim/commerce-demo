package com.cellehcim.commercedemo;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.commercetools.api.models.cart.Cart;

import reactor.core.publisher.Mono;

@WebFluxTest
@ComponentScan(basePackages = { "com.cellehcim" })
public class WebFluxCartDeletionTests {
    @Autowired
    WebTestClient webTestClient;

    @Test
    void deleteExistingCart() throws Exception {

        JSONObject requestParams = TestHelperMethods.createEmptyAmericanCart();

        Mono<Cart> cartMono = webTestClient.post()
                    .uri(TestConstants.ENDPOINT_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestParams.toString()))
                    .exchange()
                    .expectStatus().isOk()
                    .returnResult(Cart.class)
                    .getResponseBody()
                    .single();

        String cartId = cartMono.block().getId();

        webTestClient.delete().uri(TestConstants.ENDPOINT_URL + "/" + cartId)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.id").isNotEmpty()
        .jsonPath("$.id").isEqualTo(cartId);

    }

    @Test
    void deleteNonExistentCart() {
        webTestClient.delete().uri(TestConstants.ENDPOINT_URL + "/" + TestConstants.INVALID_CART_ID)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isNotFound();
    }
}
