package client.order;

import client.Client;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

public class ClientOrder extends Client {
    private static final String ENDPOINT = "/orders";
    private static final String ENDPOINT_ALL = "/all";
    public static final String AUTHORIZATION_MESSAGE = "OK";

    @Step("Создание заказа без авторизации")
    public Response createOrderWithoutLogin(Map<String, String[]> ingredients) {
        return spec()
                .body(ingredients)
                .post(ENDPOINT);
    }

    @Step("Создание заказа с авотризацией")
    public Response createOrderWithLogin(Map<String, String[]> ingredients, String accessToken) {
        return spec()
                .header(AUTHORIZATION_MESSAGE, accessToken)
                .body(ingredients)
                .post(ENDPOINT);
    }

    @Step("Получение всех заказов")
    public Response getAllOrders() {
        return spec()
                .get(ENDPOINT + ENDPOINT_ALL);
    }

    @Step("Получить заказ конкретного пользователя с авторизацией")
    public Response ordersWithAuthGet(String accessToken) {
        return spec()
                .header(AUTHORIZATION_MESSAGE, accessToken)
                .get(ENDPOINT);
    }

    @Step("Получить заказ конкретного пользователя без авторизации")
    public Response ordersWithoutAuthGet() {
        return spec()
                .get(ENDPOINT);
    }
}
