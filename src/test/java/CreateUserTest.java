import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.LogIn;
import org.example.User;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class CreateUserTest extends AbstractBeforeTest {
    @Test
    @DisplayName("Создание пользователя")
    @Description("Создание уникального ползователя")
    public void createUser() {
        Response createUser = LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        createUser.then().assertThat().statusCode(200).and().body("success", is(true));
    }

    @Test
    @DisplayName("Создание дубля пользователя")
    @Description("Проверка, если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void createDuplicateUser() {
        LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        Response createUser = LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME));
        createUser.then().assertThat().statusCode(403).and().body("success", is(false));
    }
    @Test
    @DisplayName("Создание пользователя без заполнения одного из обязательных полей")
    @Description("Не заполнено поле email")
    public void checkEmptyEmailField() {
        Response createUser = LogIn.getPostRequestCreateUser(new User("", LogIn.PASSWORD, LogIn.NAME));
        createUser.then().assertThat().statusCode(403).and().body("success", is(false));
    }
    @Test
    @DisplayName("Создание пользователя без заполнения одного из обязательных полей")
    @Description("Не заполнено поле password")
    public void checkEmptyPasswordField() {
        Response createUser = LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, "", LogIn.NAME));
        createUser.then().assertThat().statusCode(403).and().body("success", is(false));
    }

    @Test
    @DisplayName("Создание пользователя без заполнения одного из обязательных полей")
    @Description("Не заполнено поле name")
    public void checkEmptyNameField() {
        Response createUser = LogIn.getPostRequestCreateUser(new User(LogIn.EMAIL, LogIn.PASSWORD, ""));
        createUser.then().assertThat().statusCode(403).and().body("success", is(false));
    }

    @After
    public void deleteCourier() {
        String accessToken = LogIn.checkRequestAuthLogin(new User(LogIn.EMAIL, LogIn.PASSWORD, LogIn.NAME)).then().extract().path("accessToken");
        if (accessToken != null){
            LogIn.deleteUser(accessToken);
        }


    }
}
