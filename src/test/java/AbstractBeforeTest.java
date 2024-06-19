import io.restassured.RestAssured;
import org.junit.Before;

import static org.example.LogIn.URL;

public class AbstractBeforeTest {
    @Before
    public void setUp(){
        RestAssured.baseURI = URL;

    }
}
