import client.order.ClientOrder;
import generator.GeneratorIngredients;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderGetSpecificUserTest extends GeneralTest {
    private final GeneratorIngredients generatorIngredients = new GeneratorIngredients();
    protected final ClientOrder clientOrder = new ClientOrder();

    @Test
    @DisplayName("Получение заказа конкретного пользователя: авторизованный пользователь")
    public void ordersGetWithAuth() {
        clientOrder.createOrderWithLogin(generatorIngredients.getValidIngredients(), accessToken);
        Response response = clientOrder.ordersWithAuthGet(accessToken);
        response.then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("success", equalTo(true))
                .and().body("orders", notNullValue());
    }


    @Test
    @DisplayName("Получение заказа конкретного пользователя: неавторизованный пользователь")
    public void ordersGetWithoutAuth() {
        Response response = clientOrder.ordersWithoutAuthGet();
        response.then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body("success", equalTo(false))
                .and().body("message", equalTo("You should be authorised"));
    }
}
