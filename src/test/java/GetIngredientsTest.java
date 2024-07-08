import client.ingredient.ClientIngredient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

@Slf4j
public class GetIngredientsTest {
    private final ClientIngredient clientIngredient = new ClientIngredient();

    @Test
    @DisplayName("Получение списка ингредиентов")
    public void getAllIngredients() {
        Response response = clientIngredient.allIngredientsGet();
        log.info("Ответ от сервера: {}", response.body().asString());

        response.then().statusCode( HttpStatus.SC_OK)
                .and().body("success", equalTo(true));
    }
}
