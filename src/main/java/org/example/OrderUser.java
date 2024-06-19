package org.example;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class OrderUser {
    public static final String GET_INGREDIENTS = "api/ingredients";
    public static final String CREATE_ORDER = "api/orders";
    public static final String GET_ORDER = "api/orders";
    @Step("Получение списка ингредиентов")
    public static Ingredients getIngredientFromAPI() {
        return given()
                .header("Content-type", "application/json")
                .get(GET_INGREDIENTS)
                .body()
                .as(Ingredients.class);
    }
    @Step ("Создание заказа с авторизацией")
    public static Response createOrderWithAuthorization(Order order, String accessToken){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",accessToken)
                .body(order)
                .when()
                .post(CREATE_ORDER);

    }
    @Step ("Создание заказа без авторизацией")
    public static Response createOrderWithoutAuthorization(Order order){
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(CREATE_ORDER);

    }
    @Step("Создание заказа с авторизацией, без ингредиентов")
    public static Response createOrderWithAuthorizationWithoutIngredients(String accessToken){
        return  given()
                .header("Content-type", "application/json")
                .header("Authorization",accessToken)
                .body("")
                .when()
                .post(CREATE_ORDER);
    }
    @Step("Создание заказа без авторизации, без ингредиентов")
    public static Response createOrderWithoutAuthorizationWithoutIngredients(){
        return given()
                .header("Content-type", "application/json")
                .body("")
                .when()
                .post(CREATE_ORDER);

    }
    @Step ("Получение списка заказов с авторизацией")
    public static Response getOrderListWithAuthorization(String accessToken){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .when()
                .get(GET_ORDER);
    }
    @Step ("Получение списка заказов без авторизации")
    public static Response getOrderListWithoutAuthorization(){
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(GET_ORDER);
    }

}
