package client;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class Client {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static final String BASE_PEN = "/api";

    public RequestSpecification spec() {
        return given()
                .contentType( ContentType.JSON)
                .baseUri(BASE_URL)
                .basePath(BASE_PEN);
    }
}
