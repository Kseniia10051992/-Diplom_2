import client.user.ClientUser;
import generator.GeneratorUser;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import model.user.User;
import org.junit.After;
import org.junit.Before;

@Slf4j
public class GeneralTest {
    protected final ClientUser ClientUser = new ClientUser();
    public static final String ACCESS_TOKEN = "accessToken";
    protected final GeneratorUser generatorUser = new GeneratorUser();
    protected User user;
    protected String accessToken;
    protected Response responseUserCreate;


    @Before
    public void createUser() {
        user = generatorUser.createUser();
        responseUserCreate = ClientUser.createUser(user);
        accessToken = responseUserCreate.body().path(ACCESS_TOKEN);
    }

    @After
    public void deleteUser() {
        if (user != null) {
            ClientUser.userDelete(user, accessToken);
        }
    }
}
