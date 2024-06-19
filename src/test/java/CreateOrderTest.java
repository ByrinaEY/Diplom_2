/*Создание заказа:
* с авторизацией,
* без авторизации,
* с ингредиентами,
* без ингредиентов,
* с неверным хешем ингредиентов. */

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.*;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;

public class CreateOrderTest extends AbstractBeforeTest {
    private String accessToken;
    private List<String> ingredient;
    @Test
    @DisplayName("Создание заказа с ингредиентами")
    @Description("С авторизацией")
    public void createOrderWithAuthorization() {
        Response createUser = LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        accessToken = LogIn.checkRequestAuthLogin(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME)).then().extract().path("accessToken");
        Ingredients ingredients = OrderUser.getIngredientFromAPI();
        ingredient = new ArrayList<>();
        for (int i=1; i<=4; i++){
            ingredient.add(ingredients.getData().get(i).get_id());
        }
        Order order = new Order(ingredient);
        Response response = OrderUser.createOrderWithAuthorization(order, accessToken);
        response.then().assertThat().statusCode(200).and().body("success", is(true))
                .and().body("name", Matchers.notNullValue())
                .and().body("order.number", Matchers.notNullValue());
    }
    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("С авторизацией")
    public void createOrderWithAuthorizationWithoutIngredients() {
        Response createUser = LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        accessToken = LogIn.checkRequestAuthLogin(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME)).then().extract().path("accessToken");
        Response response = OrderUser.createOrderWithAuthorizationWithoutIngredients(accessToken);
        response.then().assertThat().statusCode(400).and().body("success", is(false))
                .and().body("message", is("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    @Description("Без авторизации")
    public void createOrderWithoutAuthorization() {
        Ingredients ingredients = OrderUser.getIngredientFromAPI();
        ingredient = new ArrayList<>();
        for (int i=1; i<=4; i++){
            ingredient.add(ingredients.getData().get(i).get_id());
        }
        Order order = new Order(ingredient);
        Response response = OrderUser.createOrderWithoutAuthorization(order);
        response.then().assertThat().statusCode(200).and().body("success", is(true))
                .and().body("name", Matchers.notNullValue())
                .and().body("order.number", Matchers.notNullValue());
    }
    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Без авторизации")
    public void createOrderWithoutAuthorizationWithoutIngredients() {
        Response response = OrderUser.createOrderWithoutAuthorizationWithoutIngredients();
        response.then().assertThat().statusCode(400).and().body("success", is(false))
                .and().body("message", is("Ingredient ids must be provided"));
    }
    @Test
    @DisplayName("Создание заказа c неверным хешем ингредиентов")
    @Description("Без авторизации")
    public void createOrderWithoutAuthorizationWithWrongHash() {
        ingredient = new ArrayList<>();
        for (int i=1; i<=4; i++){
            ingredient.add(RandomStringUtils.randomAlphabetic(15));
        }
        Order order = new Order(ingredient);
        Response response = OrderUser.createOrderWithoutAuthorization(order);
        response.then().assertThat().statusCode(500);
    }

    @Test
    @DisplayName("Создание заказа c неверным хешем ингредиентов")
    @Description("С авторизацией")
    public void createOrderWithAuthorizationWithWrongHash() {
        Response createUser = LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        accessToken = LogIn.checkRequestAuthLogin(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME)).then().extract().path("accessToken");
        ingredient = new ArrayList<>();
        for (int i=1; i<=4; i++){
            ingredient.add(RandomStringUtils.randomAlphabetic(15));
        }
        Order order = new Order(ingredient);
        Response response = OrderUser.createOrderWithAuthorization(order, accessToken);
        response.then().assertThat().statusCode(500);
    }

    @After
    public void deleteCourier() {
        if (accessToken != null){
            LogIn.deleteUser(accessToken);
        }
    }
}
