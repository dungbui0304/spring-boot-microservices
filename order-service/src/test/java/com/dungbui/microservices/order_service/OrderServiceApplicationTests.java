package com.dungbui.microservices.order_service;

import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import com.dungbui.microservices.order_service.stubs.InventoryClientStub;
import io.restassured.RestAssured;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@LocalServerPort
	private int port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	void shouldSubmitOrder() {
	String submitOrderJson = """
		{
			"skuCode": "iphone_15",
			"price": 1000,
			"quantity": 1
		}
		""";
		InventoryClientStub.stubInventoryCall("iphone_15", 1);
		var responseBodyString = RestAssured.given()
			.contentType("application/json")
			.body(submitOrderJson)
			.when()
			.post("/api/orders")
			.then()
			.log().all()
			.statusCode(201)
			.extract()
			.body().asString();

		assertThat(responseBodyString, Matchers.is("Order Placed Successfully"));	
	} 

	@Test
	void shouldFailOrderWhenProductNotInStock() {
		String submitOrderJson = """
			{
				"skuCode": "iphone_15",
				"price": 1000,
				"quantity": 1000
			}
			""";
		InventoryClientStub.stubInventoryCall("iphone_15", 1000);
		
		RestAssured.given()
		.contentType("application/json")
		.body(submitOrderJson)
		.when()
		.post("/api/orders")
		.then()
		.log().all()
		.statusCode(500);
	}
}
