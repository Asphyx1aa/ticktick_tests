package api;

import config.ApiConfig;
import io.restassured.response.ValidatableResponse;
import models.*;
import org.aeonbits.owner.ConfigFactory;
import tests.api.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseResponseSpec;
import static specs.TaskSpec.task;

public class TaskApiSteps extends TestBase {
    protected final ApiConfig config = ConfigFactory.create(ApiConfig.class);

    public CreateTaskResponse createNewTask(String title, String desc, String content, boolean isAllDay) {

        return step("Отправляем API запрос на создание задачи с заголовком " + title, () -> {
                    CreateTaskRequest createTaskRequest = new CreateTaskRequest(title, desc, content, isAllDay);

                    return given()
                            .spec(task)
                            .body(createTaskRequest)
                            .header("Authorization", "Bearer " + config.getApiKey())
                            .when()
                            .post("task")
                            .then()
                            .spec(baseResponseSpec(200))
                            .extract().as(CreateTaskResponse.class);
                }
        );
    }

    public CreateTaskResponseWithError createNewTaskUnauthorized(String title, String desc, String content, boolean isAllDay) {
        return step("Отправляем запрос на создание задачи под неавторизованным пользователем", () -> {
            CreateTaskRequest createTaskRequest = new CreateTaskRequest(title, desc, content, isAllDay);

            return given()
                    .spec(task)
                    .body(createTaskRequest)
                    .when()
                    .post("task")
                    .then()
                    .spec(baseResponseSpec(401))
                    .extract().as(CreateTaskResponseWithError.class);
        });

    }

    public CreateTaskResponseWithError getNotExistingTaskById(String taskId, String projectId) {

        return step("Получаем несуществующую задачу", () -> given()
                .spec(task)
                .header("Authorization", "Bearer " + config.getApiKey())
                .pathParam("projectId", projectId)
                .pathParam("taskId", taskId)
                .when()
                .get("project/{projectId}/task/{taskId}")
                .then()
                .spec(baseResponseSpec(404))
                .extract().as(CreateTaskResponseWithError.class));
    }

    public CreateTaskResponse getTaskById(String taskId, String projectId) {

        return step("Получаем задачу", () -> given()
                .spec(task)
                .header("Authorization", "Bearer " + config.getApiKey())
                .pathParam("projectId", projectId)
                .pathParam("taskId", taskId)
                .when()
                .get("project/{projectId}/task/{taskId}")
                .then()
                .spec(baseResponseSpec(200))
                .extract().as(CreateTaskResponse.class));
    }

    public UpdateTaskResponse updateTask(String taskId, String projectId, String desc, boolean isAllDay) {

        return step("Отправляем запрос на обновление задачи", () -> {
            UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest(taskId, projectId, desc, isAllDay);

            return given()
                    .spec(task)
                    .body(updateTaskRequest)
                    .header("Authorization", "Bearer " + config.getApiKey())
                    .pathParam("taskId", taskId)
                    .post("task/{taskId}")
                    .then().spec(baseResponseSpec(200))
                    .extract().as(UpdateTaskResponse.class);
        });
    }

    public ValidatableResponse deleteTask(String taskId, String projectId) {
        return step("Отправляем запрос на удаление задачи", () -> given()
                .spec(task)
                .header("Authorization", "Bearer " + config.getApiKey())
                .pathParam("taskId", taskId)
                .pathParam("projectId", projectId)
                .delete("project/{projectId}/task/{taskId}")
                .then()
                .spec(baseResponseSpec(200)));
    }
}

