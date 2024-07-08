package client.user;

import client.Client;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import model.user.User;
import model.user.WithoutEmailUser;
import model.user.WithoutNameUser;
import model.user.WithoutPasswordUser;
import java.util.Map;

public class ClientUser extends Client {
    private static final String ENDPOINT = "/auth";
    private static final String ENDPOINT2 = "/register";
    private static final String LOGIN = "/login";
    private static final String USER = "/user";
    public static final String AUTHORIZATION_MESSAGE = "Success";

    @Step("Создание пользователя")
    public Response createUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(ENDPOINT + ENDPOINT2);
    }
    @Step("Создание пользователя без email")
    public ValidatableResponse createUserWithoutEmail(WithoutEmailUser withoutEmailUser) {
        return spec()
                .body( withoutEmailUser )
                .post(ENDPOINT + ENDPOINT2)
                .then();
    }
    @Step("Создание пользователя без password")
    public ValidatableResponse createUserWithoutPassword(WithoutPasswordUser withoutPasswordUser) {
        return spec()
                .body( withoutPasswordUser )
                .post(ENDPOINT + ENDPOINT2)
                .then();
    }
    @Step("Создание пользователя без name")
    public ValidatableResponse createUserWithoutName(WithoutNameUser withoutNameUser) {
        return spec()
                .body( withoutNameUser )
                .post(ENDPOINT + ENDPOINT2)
                .then();
    }
    @Step("Авторизация пользователя")
    public Response userLogin(User user) {
        return spec()
                .body(user)
                .post(ENDPOINT + LOGIN);
    }
    @Step("Авторизация пользователя без email")
    public ValidatableResponse userWithoutEmailLogin(WithoutEmailUser withoutEmailUser) {
        return spec()
                .body( withoutEmailUser )
                .post(ENDPOINT + LOGIN)
                .then();
    }
    @Step("Авторизация пользователя без password")
    public ValidatableResponse userWithoutPasswordLogin(WithoutPasswordUser withoutPasswordUser) {
        return spec()
                .body( withoutPasswordUser )
                .post(ENDPOINT + LOGIN)
                .then();
    }
    @Step("Авторизация пользователя без name")
    public ValidatableResponse userWithoutNameLogin(WithoutNameUser withoutNameUser) {
        return spec()
                .body( withoutNameUser )
                .post(ENDPOINT + LOGIN)
                .then();
    }

    @Step("Получить информацию о пользователе")
    public Response userInfoGet(String accessToken) {
        return spec()
                .header(AUTHORIZATION_MESSAGE, accessToken)
                .get(ENDPOINT + USER);
    }

    @Step("Обновить информацию о пользователе с авторизацией")
    public Response userUpdate(Map<String, String> updateData, String accessToken) {
        return spec()
                .header(AUTHORIZATION_MESSAGE, accessToken)
                .body(updateData)
                .patch(ENDPOINT + USER);
    }

    @Step("Обновить информацию о пользователе без авторизации")
    public Response userWithoutLoginUpdate(Map<String, String> updateData) {
        return spec()
                .body(updateData)
                .patch(ENDPOINT + USER);
    }

    @Step("Удалить пользователя")
    public Response userDelete(User user, String accessToken) {
        return spec()
                .header(AUTHORIZATION_MESSAGE, accessToken)
                .body(user)
                .delete(ENDPOINT + USER);
    }
}
