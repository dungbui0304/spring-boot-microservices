package com.dungbui.microservices.product;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
class ProductServiceApplicationTests {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldCreateProduct() {
		String requestBody = """
				{
					"name": "Test Product",
					"description": "This is a test product",
					"price": 19.99
				}
			""";

		RestAssured.given()
		.contentType("application/json")
		.body(requestBody)
		.when()
		.post("/api/products")
	    .then()
		.statusCode(201)
		  .body("id", Matchers.notNullValue())
		  .body("name", Matchers.equalTo("Test Product"))	
		  .body("description", Matchers.equalTo("This is a test product"));	
    }
}
