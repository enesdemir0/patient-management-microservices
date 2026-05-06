package com.pm;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AuthIntegrationTest {

  @BeforeAll
  static void setUp(){
    // Reads -Dgateway.url passed by Maven (CI sets this to the K8s port-forward address)
    RestAssured.baseURI = System.getProperty("gateway.url", "http://localhost:4004");
  }

  @Test
  public void shouldReturnOKWithValidToken() {
    String loginPayload = """
          {
            "email": "testuser@test.com",
            "password": "password123"
          }
        """;

    Response response = given()
        .contentType("application/json")
        .body(loginPayload)
        .when()
        .post("/auth/login")
        .then()
        .statusCode(200)
        .body("token", notNullValue())
        .extract()
        .response();

    System.out.println("Generated Token: " + response.jsonPath().getString("token"));
  }

  @Test
  public void shouldReturnUnauthorizedOnInvalidLogin() {
    String loginPayload = """
          {
            "email": "invalid_user@test.com",
            "password": "wrongpassword"
          }
        """;

    given()
        .contentType("application/json")
        .body(loginPayload)
        .when()
        .post("/auth/login")
        .then()
        .statusCode(401);
  }
}