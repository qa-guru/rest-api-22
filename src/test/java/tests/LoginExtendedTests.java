package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import models.lombok.LoginBodyLombokModel;
import models.lombok.LoginResponseLombokModel;
import models.lombok.MissingPasswordModel;
import models.pojo.LoginBodyPojoModel;
import models.pojo.LoginResponsePojoModel;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpec.*;

public class LoginExtendedTests {

    @Test
    void successfulLoginBadPracticeTest() {
        String authBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }"; // BAD PRACTICE

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(authBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void successfulLoginWithPojoTest() {
        LoginBodyPojoModel authBody = new LoginBodyPojoModel();
        authBody.setEmail("eve.holt@reqres.in");
        authBody.setPassword("cityslicka");

        LoginResponsePojoModel response =  given()
                .log().uri()
                .log().method()
                .log().body()
                .body(authBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponsePojoModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void successfulLoginWithLombokTest() {
        LoginBodyLombokModel authBody = new LoginBodyLombokModel();
        authBody.setEmail("eve.holt@reqres.in");
        authBody.setPassword("cityslicka");

        LoginResponseLombokModel response =  given()
                .log().uri()
                .log().method()
                .log().body()
                .body(authBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void successfulLoginWithAllureTest() {
        LoginBodyLombokModel authBody = new LoginBodyLombokModel();
        authBody.setEmail("eve.holt@reqres.in");
        authBody.setPassword("cityslicka");

        LoginResponseLombokModel response =  given()
                .filter(new AllureRestAssured())
                .log().uri()
                .log().method()
                .log().body()
                .body(authBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void successfulLoginWithCustomAllureTest() {
        LoginBodyLombokModel authBody = new LoginBodyLombokModel();
        authBody.setEmail("eve.holt@reqres.in");
        authBody.setPassword("cityslicka");

        LoginResponseLombokModel response =  given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().method()
                .log().body()
                .body(authBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void successfulLoginWithAllureStepsTest() {
        LoginBodyLombokModel authBody = new LoginBodyLombokModel();
        authBody.setEmail("eve.holt@reqres.in");
        authBody.setPassword("cityslicka");

        LoginResponseLombokModel response = step("Make login request", () ->
            given()
                    .filter(withCustomTemplates())
                    .log().uri()
                    .log().method()
                    .log().body()
                    .body(authBody)
                    .contentType(JSON)
                    .when()
                    .post("https://reqres.in/api/login")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(LoginResponseLombokModel.class));

        step("Verify response", () ->
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void successfulLoginWithSpecsTest() {
        LoginBodyLombokModel authBody = new LoginBodyLombokModel();
        authBody.setEmail("eve.holt@reqres.in");
        authBody.setPassword("cityslicka");

        LoginResponseLombokModel response = step("Make login request", () ->
            given(loginRequestSpec)
                    .body(authBody)
                    .when()
                    .post()
                    .then()
                    .spec(loginResponseSpec)
                    .extract().as(LoginResponseLombokModel.class));

        step("Verify response", () ->
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void missingPasswordTest() {
        LoginBodyLombokModel authBody = new LoginBodyLombokModel();
        authBody.setEmail("eve.holt@reqres.in");

        MissingPasswordModel response = step("Make login request", () ->
                given(loginRequestSpec)
                        .body(authBody)
                        .when()
                        .post()
                        .then()
                        .spec(missingPasswordSpec)
                        .extract().as(MissingPasswordModel.class));

        step("Verify response", () ->
                assertEquals("Missing password", response.getError()));

    }
}
