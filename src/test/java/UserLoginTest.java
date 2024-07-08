import client.user.ClientUser;
import generator.GeneratorUser;
import io.qameta.allure.junit4.DisplayName;
import model.user.WithoutEmailUser;
import model.user.WithoutNameUser;
import model.user.WithoutPasswordUser;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserLoginTest extends GeneralTest {
    public static final String SUCCESS = "success";
    public static final String NOT_VALID_EMAIL = "notValidEmail@new.ru";
    public static final String NOT_VALID_PASSWORD = "notvalid_password";
    private final GeneratorUser generatorUser = new GeneratorUser();
    private final ClientUser clientUser = new ClientUser();

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void userCorrectLogin() {
        clientUser.userLogin(user)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body(SUCCESS, equalTo(true));
    }


    @Test

    public void withoutNameLogin() {
        WithoutNameUser withoutNameUser = new WithoutNameUser(user.getEmail(), user.getPassword());

        clientUser.userWithoutNameLogin( withoutNameUser )
                .statusCode(HttpStatus.SC_OK)
                .and().body(SUCCESS, equalTo(true));
    }


    @Test
    public void withPasswordIncorrectLogin() {
        user.setPassword(NOT_VALID_PASSWORD);

        clientUser.userLogin(user)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body(SUCCESS, equalTo(false));
    }

    @Test
    public void withoutPasswordLogin() {
        WithoutPasswordUser withoutPasswordUser = new WithoutPasswordUser(user.getEmail(), user.getName());

        clientUser.userWithoutPasswordLogin( withoutPasswordUser )
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body(SUCCESS, equalTo(false));
    }


    @Test
    public void withEmailIncorrectLogin() {
        user.setEmail(NOT_VALID_EMAIL);
        clientUser.userLogin(user)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body(SUCCESS, equalTo(false));
    }

    @Test
    public void withoutEmailLogin() {
        WithoutEmailUser withoutEmailUser = new WithoutEmailUser(user.getPassword(), user.getName());
        clientUser.userWithoutEmailLogin( withoutEmailUser )
                .statusCode( HttpStatus.SC_UNAUTHORIZED)
                .and().body(SUCCESS, equalTo(false));
    }
}
