package tests.api;

import api.TaskApiSteps;
import data.TaskData;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Owner("Timur Vlasov")
@Tag("API")
@Feature("Task")
public class TickTickProjectsTests extends TestBase {

    private final TaskApiSteps taskApiSteps = new TaskApiSteps();
    TaskData taskData = new TaskData();

    @Test
    @DisplayName("Проверка на успешное создание задачи через API")
    @Severity(CRITICAL)
    void shouldCreateNewTaskTest() {

        CreateTaskResponse response = taskApiSteps.createNewTask(taskData.getTaskTitle(), taskData.getTaskDesc(), taskData.getTaskContent(), taskData.isIAllDay());

        step("Проверяем, что задача создана успешно", () -> {
            assertThat(response.getId(), notNullValue());
            assertThat(response.getTitle(), equalTo(taskData.getTaskTitle()));
            assertThat(response.getContent(), equalTo(taskData.getTaskDesc()));
            assertThat(response.isAllDay(), equalTo(taskData.isIAllDay()));
        });
    }

    @Test
    @DisplayName("Проверяем, что нельзя создать задачу неавторизованному пользователю")
    @Severity(NORMAL)
    void createTaskUnauthorizedUserTest() {
        CreateTaskResponseWithError createTaskResponseWithError = taskApiSteps.createNewTaskUnauthorized(taskData.getTaskTitle(), taskData.getTaskDesc(), taskData.getTaskContent(), taskData.isIAllDay());

        step("Проверяем, что вернулась ошибка 401 -'Нет доступа'", () ->
                assertThat(createTaskResponseWithError.getError(), equalTo("unauthorized"))
        );
    }

    @Test
    @DisplayName("Проверяем, что возвращается корректная задача")
    @Severity(CRITICAL)
    void shouldGetCorrectTaskTest() {
        CreateTaskResponse taskResponse = taskApiSteps.createNewTask(taskData.getTaskTitle(), taskData.getTaskDesc(), taskData.getTaskContent(), taskData.isIAllDay());

        CreateTaskResponse getTaskResponse = taskApiSteps.getTaskById(taskResponse.getId(), taskResponse.getProjectId());

        step("Проверяем, что вернулась корректная задача", () -> {
            assertThat(getTaskResponse.getId(), equalTo(taskResponse.getId()));
            assertThat(getTaskResponse.getTitle(), equalTo(taskResponse.getTitle()));
            assertThat(getTaskResponse.getContent(), equalTo(taskResponse.getContent()));
            assertThat(getTaskResponse.isAllDay(), equalTo(taskResponse.isAllDay()));
        });
    }

    @Test
    @DisplayName("Проверяем, что возвращается 404 ошибка при попытке получить несуществующую задачу")
    @Severity(CRITICAL)
    void shouldReturnNotFoundTaskErrorTest() {
        CreateTaskResponseWithError getTaskResponse = taskApiSteps.getNotExistingTaskById("test", "test");

        step("Проверяем, что вернулась 404", () -> {
            assertThat(getTaskResponse.getError(), equalTo("not found"));
        });
    }

    @Test
    @DisplayName("Проверяем, что задача обновляется успешно")
    @Severity(CRITICAL)
    void shouldUpdateTaskTest() {
        CreateTaskResponse taskResponse = taskApiSteps.createNewTask(taskData.getTaskTitle(), taskData.getTaskDesc(), taskData.getTaskContent(), taskData.isIAllDay());

        UpdateTaskResponse updateTaskResponse = taskApiSteps.updateTask(taskResponse.getId(), taskResponse.getProjectId(), taskData.getTaskContent(), taskData.isIAllDay());

        step("Проверяем, что задача обновилась успешно", () -> {
            assertThat(updateTaskResponse.getDesc(), equalTo(taskData.getTaskContent()));
            assertThat(updateTaskResponse.isAllDay(), equalTo(taskData.isIAllDay()));
        });
    }

    @Test
    @DisplayName("Проверяем, что задача удаляется успешно")
    @Severity(CRITICAL)
    void shouldDeleteTaskTest() {
        CreateTaskResponse taskResponse = taskApiSteps.createNewTask(taskData.getTaskTitle(), taskData.getTaskDesc(), taskData.getTaskContent(), taskData.isIAllDay());

        taskApiSteps.deleteTask(taskResponse.getId(), taskResponse.getProjectId());

        step("Проверяем, что задача успешно удалена", () ->
                assertThat(taskApiSteps.getTaskById(taskResponse.getId(), taskResponse.getProjectId()), equalTo("not found"))
        );
    }
}