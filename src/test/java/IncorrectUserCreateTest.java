import client.user.ClientUser;
import generator.GeneratorUser;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import model.user.User;
import model.user.WithoutEmailUser;
import model.user.WithoutNameUser;
import model.user.WithoutPasswordUser;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

@Slf4j
public class IncorrectUserCreateTest {
    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";
    public static final String TEXT_MESSAGE = "Email, password and name are required fields";
    private final ClientUser clientUser = new ClientUser();
    private final GeneratorUser generatorUser = new GeneratorUser();


    @Test
    public void userCreateWithoutEmail() {
        WithoutEmailUser withoutEmailUser = generatorUser.createUserWithoutEmail();

        ValidatableResponse response = clientUser.createUserWithoutEmail( withoutEmailUser );

        response.statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body(SUCCESS, equalTo(false))
                .and().body(MESSAGE, equalTo(TEXT_MESSAGE));
    }


    @Test
    public void userCreateWithoutName() {
        WithoutNameUser withoutNameUser = generatorUser.createUserWithoutName();

        ValidatableResponse response = clientUser.createUserWithoutName( withoutNameUser );

        response.statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body(SUCCESS, equalTo(false))
                .and().body(MESSAGE, equalTo(TEXT_MESSAGE));
    }


    @Test
    public void userCreateWithoutPassword() {
        WithoutPasswordUser withoutPasswordUser = generatorUser.createUserWithoutPassword();
        ValidatableResponse response = clientUser.createUserWithoutPassword( withoutPasswordUser );
        response.statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body(SUCCESS, equalTo(false))
                .and().body(MESSAGE, equalTo(TEXT_MESSAGE));
    }

}
