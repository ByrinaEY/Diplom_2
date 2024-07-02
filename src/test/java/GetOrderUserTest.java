import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.*;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/*Получение заказов конкретного пользователя:
* авторизованный пользователь,
* неавторизованный пользователь. */
public class GetOrderUserTest extends AbstractBeforeTest {
    private String accessToken;

    @Test
    @DisplayName("Получение заказов конкретного пользователя")
    @Description("С авторизацией")
    public void getOrderListWithAuthorization() {
        LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        accessToken = LogIn.checkRequestAuthLogin(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME)).then().extract().path("accessToken");
        Response response = OrderUser.getOrderListWithAuthorization(accessToken);
        response.then().assertThat().statusCode(200).and().body("success", is(true))
                .and().body("orders", Matchers.notNullValue())
                .and().body("total", Matchers.notNullValue())
                .and().body("totalToday", Matchers.notNullValue());
    }
    @Test
    @DisplayName("Получение заказов конкретного пользователя")
    @Description("без авторизации")
    public void getOrderListWithoutAuthorization() {
        Response response = OrderUser.getOrderListWithoutAuthorization();
        response.then().assertThat().statusCode(401).and().body("success", is(false))
                .and().body("message", is("You should be authorised"));
    }

    @After
    public void deleteCourier() {
        if (accessToken != null){
            LogIn.deleteUser(accessToken);
        }
    }
}
