package data;

import com.github.javafaker.Faker;
import lombok.Getter;

@Getter
public class TaskData {
    private final static Faker faker = new Faker();

    String taskTitle;
    String taskDesc;
    String taskContent;
    boolean isAllDay;

    public TaskData() {
        this.taskTitle = faker.lebowski().quote();
        this.taskDesc = faker.lorem().characters();
        this.taskContent = faker.chuckNorris().fact();
        this.isAllDay = faker.bool().bool();
    }
}
