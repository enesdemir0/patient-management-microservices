package com.pm;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PatientIntegrationTest {
  @BeforeAll
  static void setUp(){
    // Reads -Dgateway.url passed by Maven (CI sets this to the K8s port-forward address)
    RestAssured.baseURI = System.getProperty("gateway.url", "http://localhost:4004");
  }

  @Test
  public void shouldReturnPatientsWithValidToken () {
    String loginPayload = """
          {
            "email": "testuser@test.com",
            "password": "password123"
          }
        """;

    String token = given()
        .contentType("application/json")
        .body(loginPayload)
        .when()
        .post("/auth/login")
        .then()
        .statusCode(200)
        .extract()
        .jsonPath()
        .get("token");

    // PatientController returns a JSON array at root: [{...}, {...}]
    // GPath "patients" on an array root is always null — use "$" (root) instead.
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/patients")
        .then()
        .statusCode(200)
        .body("$", notNullValue());
  }
}