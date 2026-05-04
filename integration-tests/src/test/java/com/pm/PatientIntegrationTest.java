package com.pm; // <--- DO NOT FORGET THIS

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*; // Use * to get notNullValue and greaterThan

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PatientIntegrationTest {
  
  @BeforeAll
  static void setUp(){
    RestAssured.baseURI = "http://localhost:4004";
  }

  @Test
  public void shouldReturnPatientsWithValidToken () {
    String loginPayload = """
          {
            "email": "testuser@test.com",
            "password": "password123"
          }
        """;

    // 1. Get the token
    String token = given()
        .contentType("application/json")
        .body(loginPayload)
        .when()
        .post("/auth/login")
        .then()
        .statusCode(200)
        .extract()
        .jsonPath()
        .getString("token");

    // 2. Use the token to get patients
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/patients")
        .then()
        .statusCode(200)
        // "$" means "The root of the JSON". 
        // Use this because your API returns a List [...]
        .body("$", notNullValue()) 
        .body("size()", greaterThan(0)); 
  }
}