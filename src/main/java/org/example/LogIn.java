package org.example;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;

public class LogIn {
    public static final String URL = "https://stellarburgers.nomoreparties.site/";
    public static final String EMAIL = RandomStringUtils.randomAlphabetic(8)+"@mail.ru";
    public static final String EMAIL_NEW = RandomStringUtils.randomAlphabetic(8)+"@mail.ru";
    public static final String PASSWORD = RandomStringUtils.randomAlphabetic(8);
    public static final String PASSWORD_NEW = RandomStringUtils.randomAlphabetic(8);
    public static final String NAME = RandomStringUtils.randomAlphabetic(8);
    public static final String NAME_NEW = RandomStringUtils.randomAlphabetic(8);

    public static final String CREATE_USER = "api/auth/register";
    public static final String LOGIN_USER = "/api/auth/login";
    public static final String DELETE_USER = "/api/auth/user";
    public static final String CHANGE_USER = "api/auth/user";


    @Step("Создание курьера, проверка кода ответа")
    public static Response getPostRequestCreateUser(User user) {
        return given().log().all().filter(new AllureRestAssured()).header("Content-type", "application/json").body(user).when().post(CREATE_USER);
    }

    @Step("Авторизация пользователя")
    public static Response checkRequestAuthLogin(User user) {
        return given()
                .log()
                .all()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(LOGIN_USER);
    }
    @Step ("Удаление пользователя")
    public static Response deleteUser(String accessToken){
        return given()
                .header("Authorization",accessToken)
                .when()
                .delete(DELETE_USER);
    }

    @Step ("Изменение данных о пользователе")
    public static Response changeDataUser(String accessToken, User user){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",accessToken)
                .body(user)
                .when()
                .patch(CHANGE_USER);

    }
}
