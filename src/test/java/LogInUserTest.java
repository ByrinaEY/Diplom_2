import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.LogIn;
import org.example.User;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class LogInUserTest extends AbstractBeforeTest {

    @Test
    @DisplayName("авторизация пользователя")
    @Description("логин под существующим пользователем")
    public void checkAutorization() {
        Response createUser = LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        Response loginUser = LogIn.checkRequestAuthLogin(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        loginUser.then().assertThat().statusCode(200).and().body("success", is(true));
    }
    @Test
    @DisplayName("авторизация пользователя")
    @Description("логин c неверным логином")
    public void autorizationIncorrectLogin() {
        Response createUser = LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        Response loginUser = LogIn.checkRequestAuthLogin(new User("email@emal.ru", LogIn.PASSWORD, LogIn.NAME));
        loginUser.then().assertThat().statusCode(401).and().body("success", is(false));
    }

    @Test
    @DisplayName("авторизация пользователя")
    @Description("логин c неверным паролем")
    public void autorizationIncorrectPassword() {
        Response createUser = LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        Response loginUser = LogIn.checkRequestAuthLogin(new User(LogIn.EMAIL, "incorrectPassword", LogIn.NAME));
        loginUser.then().assertThat().statusCode(401).and().body("success", is(false));
    }
    @After
    public void deleteCourier() {
        String accessToken = LogIn.checkRequestAuthLogin(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME)).then().extract().path("accessToken");
        if (accessToken != null){
            LogIn.deleteUser(accessToken);
        }
    }
}
