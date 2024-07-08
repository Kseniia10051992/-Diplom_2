import client.user.ClientUser;
import generator.GeneratorUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

@Slf4j
public class CorrectUserCreateTest extends GeneralTest {
    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";
    public static final String TEXT_MESSAGE_EXISTS = "User already exists";
    private final ClientUser clientUser = new ClientUser();
    private final GeneratorUser generatorUser = new GeneratorUser();

    @Test
    @DisplayName("Создаем уникального пользователя")
    public void correctUserCreate() {
        user = generatorUser.createUser();
        log.info("Создание пользователя: {}", user);

        Response response = clientUser.createUser(user);
        log.info("Ответ от сервера: {}", response.body().asString());
        accessToken = response.body().path("accessToken");

        response.then()
                .statusCode(HttpStatus.SC_OK)
                .and().body(SUCCESS, equalTo(true));
    }

    @Test
    @DisplayName("Создаем пользователя, который уже зарегистрирован")
    public void reCreatingUser() {
        user = generatorUser.createUser();
        Response response = clientUser.createUser(user);
        accessToken = response.body().path("accessToken");
        response.then()
                .statusCode( HttpStatus.SC_OK)
                .and().body(SUCCESS, equalTo(true));

        Response badResponse = clientUser.createUser(user);
        log.info("Создание уже существующего пользователя: {}", user);
        log.info("Ответ от сервера: {}", badResponse.body().asString());

        badResponse.then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body(SUCCESS, equalTo(false))
                .and().body(MESSAGE, equalTo(TEXT_MESSAGE_EXISTS));
    }
}