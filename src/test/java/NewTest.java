import io.qameta.allure.Allure;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.ConfigManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Story("Запуск тестов на https://reqres.in/")
@DisplayName("Тесты API https://reqres.in/")
public class NewTest {
    String URL = ConfigManager.TEST_CONFIG.baseUrl();
    String name = ConfigManager.REQRES_CONFIG.name();
    String job = ConfigManager.REQRES_CONFIG.job();
    String jobCREATE = ConfigManager.REQRES_CONFIG.jobCREATE();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String updatedAt = LocalDateTime.now().format(formatter);
    Integer id = ConfigManager.REQRES_CONFIG.id();
    String token = ConfigManager.REQRES_CONFIG.token();


    @Test
    @DisplayName("Тест GET LIST USERS")
    public void getListUsersApi() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());

        List<People> people = given().filter(new AllureRestAssured())
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath()
                .getList("data", People.class);
        System.out.println(people.get(0).first_name);
        Assertions.assertEquals("Michael", people.get(0).first_name);
    }

    @Test
    @DisplayName("Тест PUT метод")
    public void succesPUTUserApi() {

        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());

        MethodPUTReq req = new MethodPUTReq("morpheus", "zion resident");
        MethodPUTPesp resp = given().filter(new AllureRestAssured())
                .body(req)
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(MethodPUTPesp.class);
        Assertions.assertNotNull(resp.getName());

        Assertions.assertEquals(name, resp.getName());
        Assertions.assertEquals(job, resp.getJob());
        Assertions.assertEquals(updatedAt, resp.getUpdatedAt().substring(0, 10));
    }

    @Test
    @DisplayName("Тест PATCH метод")
    public void succesPATCHUserApi() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());

        MethodPUTReq req = new MethodPUTReq("morpheus", "zion resident");
        MethodPUTPesp resp = given().filter(new AllureRestAssured())
                .body(req)
                .when()
                .patch("api/users/2")
                .then().log().all()
                .extract().as(MethodPUTPesp.class);

        Assertions.assertNotNull(resp.getName());
        Assertions.assertEquals(name, resp.getName());
        Assertions.assertEquals(job, resp.getJob());
        Assertions.assertEquals(updatedAt, resp.getUpdatedAt().substring(0, 10));
    }

    @Test
    @DisplayName("Тест GET SINGLE USER NOT FOUND метод")
    public void getSingleUserNotFoundApi() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecError404());

        Response response = RestAssured
                .get("api/users/23")
                .andReturn();
        response.prettyPrint();
        String report = response.getStatusLine() + "\n" + response.getHeaders();
        Allure.addAttachment("response", report);
        Assertions.assertEquals(404, response.getStatusCode());

    }

    @Test
    @DisplayName("Тест DELETE метод")
    public void successDELETEUserApi() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK204());

        Response response = RestAssured
                .delete("api/users/2")
                .andReturn();
        response.prettyPrint();
        String report = response.getStatusLine() + "\n" + response.getHeaders();
        Allure.addAttachment("response", report);
        Assertions.assertEquals(204, response.getStatusCode());

    }

    @Test
    @DisplayName("Тест POST REGISTER - SUCCESSFUL метод")
    public void succesPOSTRegisterApi() {

        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());

        MethodPOSTRegisterReq req = new MethodPOSTRegisterReq("eve.holt@reqres.in", "pistol");
        MethodPOSTRegisterResp resp = given().filter(new AllureRestAssured())
                .body(req)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(MethodPOSTRegisterResp.class);

        Assertions.assertNotNull(resp.getId());
        Assertions.assertEquals(id, resp.getId());
        Assertions.assertEquals(token, resp.getToken());

    }

    @Test
    @DisplayName("Тест GET SINGLE USER метод")
    public void succesGETSingleUserApi() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());

        Response response = RestAssured.given().filter(new AllureRestAssured())
                .get("api/users/2")
                .then().log().all()
                .extract().response();

        Map<String, ?> userMap = response.getBody().path("data");
        Assertions.assertEquals(2,userMap.get("id"));
        Assertions.assertEquals("janet.weaver@reqres.in", userMap.get("email"));
        Assertions.assertEquals("Janet", userMap.get("first_name"));
    }

    @Test
    @DisplayName("Тест POST CREATE метод")
    public void succesPOSTCreateApi() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK201());

        MethodPUTReq req = new MethodPUTReq("morpheus", "leader");

        MethodPOSTCreateResp resp = given().filter(new AllureRestAssured())
                .body(req)
                .when()
                .post("api/users")
                .then().log().all()
                .extract().as(MethodPOSTCreateResp.class);
        Assertions.assertNotNull(resp.getName());

        Assertions.assertEquals(name, resp.getName());
        Assertions.assertEquals(jobCREATE, resp.getJob());
        Assertions.assertEquals(updatedAt, resp.getCreatedAt().substring(0, 10));
    }

    @Test
    @DisplayName("Тест GET LIST RESOURCE метод")
    public void succesGETListResourceApi() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());


        List<MethodGETListResourceResp> resource = given().filter(new AllureRestAssured())
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath()
                .getList("data", MethodGETListResourceResp.class);

        Assertions.assertEquals("cerulean", resource.get(0).getName());
        Assertions.assertEquals(2000, resource.get(0).getYear());
        Assertions.assertEquals("#98B2D1", resource.get(0).getColor());

    }

    @Test
    @DisplayName("Тест POST LOGIN - SUCCESSFUL метод")
    public void succesPOSTLoginApi() {

        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());

        MethodPOSTRegisterReq req = new MethodPOSTRegisterReq("eve.holt@reqres.in", "cityslicka");
        MethodPOSTRegisterResp resp = given().filter(new AllureRestAssured())
                .body(req)
                .when()
                .post("api/login")
                .then().log().all()
                .extract().as(MethodPOSTRegisterResp.class);

        Assertions.assertNotNull(resp.getToken());
        Assertions.assertEquals(token, resp.getToken());

    }
}
