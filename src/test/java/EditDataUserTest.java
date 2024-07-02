import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.LogIn;
import org.example.User;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class EditDataUserTest extends AbstractBeforeTest{
    private String accessToken;
    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("С авторизацией, изменение имейла")
    public void changeEmailAuthUser() {
        Response createUser = LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        accessToken = LogIn.checkRequestAuthLogin(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME)).then().extract().path("accessToken");
        Response newUser = LogIn.changeDataUser(accessToken, new User(LogIn.EMAIL_NEW, LogIn.PASSWORD, LogIn.NAME));
        newUser.then().assertThat().statusCode(200).and().body("success", is(true));
    }

    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("С авторизацией, изменение пароля")
    public void changePasswordAuthUser() {
        Response createUser = LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        accessToken = LogIn.checkRequestAuthLogin(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME)).then().extract().path("accessToken");
        Response newUser = LogIn.changeDataUser(accessToken, new User(LogIn.EMAIL, LogIn.PASSWORD_NEW, LogIn.NAME));
        newUser.then().assertThat().statusCode(200).and().body("success", is(true));
    }
    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("С авторизацией, изменение имени пользователя")
    public void changeNameAuthUser() {
        Response createUser = LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        accessToken = LogIn.checkRequestAuthLogin(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME)).then().extract().path("accessToken");
        Response newUser = LogIn.changeDataUser(accessToken, new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME_NEW));
        newUser.then().assertThat().statusCode(200).and().body("success", is(true));
    }
    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Без авторизации, изменение email")
    public void changeEmailNotAuthUser() {
        Response createUser = LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        Response newUser = LogIn.changeDataUser("", new User(LogIn.EMAIL_NEW, LogIn.PASSWORD, LogIn.NAME));
        newUser.then().assertThat().statusCode(401).and().body("message", is("You should be authorised"));
    }

    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Без авторизации, изменение пароля")
    public void changePasswordNotAuthUser() {
        LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        Response newUser = LogIn.changeDataUser("", new User(LogIn.EMAIL, LogIn.PASSWORD_NEW, LogIn.NAME));
        newUser.then().assertThat().statusCode(401).and().body("message", is("You should be authorised"));
    }

    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Без авторизации, изменение name")
    public void changeNameNotAuthUser() {
        LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        Response newUser = LogIn.changeDataUser("", new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME_NEW));
        newUser.then().assertThat().statusCode(401).and().body("message", is("You should be authorised"));
    }
    @After
    public void deleteCourier() {
        if (accessToken != null){
            LogIn.deleteUser(accessToken);
        }
    }
}
