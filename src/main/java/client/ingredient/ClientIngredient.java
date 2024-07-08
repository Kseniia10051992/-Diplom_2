package client.ingredient;

import client.Client;
import io.restassured.response.Response;

public class ClientIngredient extends Client {
    private static final String ENDPOINT = "/ingredients";

    public Response allIngredientsGet() {
        return spec()
                .get(ENDPOINT);
    }
}
