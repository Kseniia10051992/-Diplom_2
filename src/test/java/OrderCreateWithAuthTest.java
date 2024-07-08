import client.order.ClientOrder;
import generator.GeneratorIngredients;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import model.ingredient.Ingredient;
import model.order.OrderAfterCreate;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static javax.management.Query.and;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

@Slf4j
public class OrderCreateWithAuthTest extends GeneralTest {
    private final ClientOrder clientOrder = new ClientOrder();
    private final GeneratorIngredients generatorIngredients = new GeneratorIngredients();
    public static final String SUCCESS = "success";
    public static final String ORDER = "order";
    public static final String MESSAGE = "message";
    public static final String MESSAGE_INGREDIENT = "Ingredient ids must be provided";
    public static final String INGREDIENT = "ingredient";
    private OrderAfterCreate orderAfterCreate;

    @Test
    @DisplayName("Создаем заказ с авторизацией")
    public void сorrectСreateOrderWithLogin() {
        Map<String, String[]> ingredientsMap = generatorIngredients.getValidIngredients();
        Response response = clientOrder.createOrderWithLogin(ingredientsMap, accessToken);

        orderAfterCreate = response.body().jsonPath().getObject(ORDER, OrderAfterCreate.class);

        List<String> expected = new ArrayList<>(Arrays.asList(ingredientsMap.get(INGREDIENT)));
        List<String> actual = getActual(orderAfterCreate);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Создаем заказ с авторизацией, с неверным хешем ингредиентов.")
    public void orderWrongСreateIngredients() {
        Map<String, String[]> ingredientsMap = generatorIngredients.getWrongIngredients();
        log.info("Получение списка ингредиентов: {}", ingredientsMap);
        Response response = clientOrder.createOrderWithLogin(ingredientsMap, accessToken);
        log.info("Ответ от сервера: {}", response.body().asString());

        response.then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Создаем заказ с авторизацией, без ингредиентов")
    public void orderCreateWithoutIngredients() {
        Map<String, String[]> ingredientsMap = generatorIngredients.getNotFilledIngredients();
        log.info("Получение списока ингредиентов: {}", ingredientsMap);
        Response response = clientOrder.createOrderWithLogin(ingredientsMap, accessToken);
        log.info("Ответ от сервера: {}", response.body().asString());

        response.then()
                .statusCode( HttpStatus.SC_BAD_REQUEST)
                .and().body(SUCCESS, equalTo(false))
                .and().body(MESSAGE, equalTo(MESSAGE_INGREDIENT));
    }

    private List<String> getActual(OrderAfterCreate orderAfterCreate) {
        List<String> actual = new ArrayList<>();
        for (Ingredient ingredient : orderAfterCreate.getIngredients()) {
            actual.add(ingredient.get_id());
        }
        return actual;
    }
}
