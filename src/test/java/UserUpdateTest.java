import client.user.ClientUser;
import generator.GeneratorUser;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

@Slf4j
@RunWith(Parameterized.class)
public class UserUpdateTest extends GeneralTest {
    private final String email;
    private final String password;
    private final String name;
    private final String testName;
    public static final String OLD = "old";
    public static final String EMPTY = "null";
    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";
    public static final String MESSAGE_AUTHORISED = "You should be authorised";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    private final GeneratorUser generatorUser = new GeneratorUser();
    private final ClientUser clientUser = new ClientUser();


    public UserUpdateTest (String email, String password, String name, String testName) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.testName = testName;
    }

    @Parameterized.Parameters(name = "{index} : update {3}")
    public static Object[][] getParameters() {
        return new Object[][]{
                {uniqueEmailCreate(), passwordCreate(), nameCreate(), "all fields"},
                {uniqueEmailCreate(), OLD, OLD, EMAIL},
                {OLD, passwordCreate(), OLD, PASSWORD},
                {OLD, OLD, nameCreate(), NAME},
                {uniqueEmailCreate(), EMPTY, EMPTY, "email without another fields"},
                {EMPTY, passwordCreate(), EMPTY, "password without another fields"},
                {EMPTY, EMPTY, nameCreate(), "name without another fields"},
                {uniqueEmailCreate(), passwordCreate(), EMPTY, "email + password without name"},
                {EMPTY, passwordCreate(), nameCreate(), "password + name without email"},
                {uniqueEmailCreate(), EMPTY, nameCreate(), "email + name without password"}

        };
    }

    @Test
    public void loginUpdateUser() {
        Map<String, String> updateData = createMap(email, password, name);
        log.info("Обновляемые данные: {}", updateData);

        Response response = clientUser.userUpdate(updateData, accessToken);
        log.info("Получен ответ от сервера {}", response.body().asString());

        response.then().statusCode(HttpStatus.SC_OK)
                .and().body(SUCCESS, equalTo(true));


    }

    @Test
    public void loginWithoutUpdate() {
        Map<String, String> updateData = createMap(email, password, name);
        log.info("Обновляемые данные: {}", updateData);

        Response response = clientUser.userWithoutLoginUpdate(updateData);
        log.info("Получен ответ от сервера {}", response.body().asString());

        response.then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body(SUCCESS, equalTo(false))
                .and().body(MESSAGE, equalTo(MESSAGE_AUTHORISED));
    }


    private Map<String, String> createMap(String email, String password, String name) {
        Map<String, String> updateData = new HashMap<>();
        if (email.contains(OLD)) {
            updateData.put(EMAIL, user.getEmail());
        } else if (!email.contains(EMPTY)) {
            updateData.put(EMAIL, email);
        }
        if (password.contains(OLD)) {
            updateData.put(PASSWORD, user.getPassword());
        } else if (!password.contains(EMPTY)) {
            updateData.put(PASSWORD, password);
        }
        if (name.contains(OLD)) {
            updateData.put(NAME, user.getName());
        } else if (!name.contains(EMPTY)) {
            updateData.put(NAME, name);
        }

        return updateData;
    }

    private static String uniqueEmailCreate() {
        return String.format("%s@%s.ru", RandomStringUtils.randomAlphabetic(5),
                RandomStringUtils.randomAlphabetic(3));
    }
    private static String passwordCreate() {

        return RandomStringUtils.randomAlphanumeric(8);

    }

    private static String nameCreate() {
        return RandomStringUtils.randomAlphabetic(6);
    }
}