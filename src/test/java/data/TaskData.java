package data;

import com.github.javafaker.Faker;
import lombok.Getter;

@Getter
public class TaskData {
    private final static Faker faker = new Faker();

    public String taskTitle = faker.lebowski().quote();
    public String taskDesc = faker.lorem().characters();
    public String taskContent = faker.chuckNorris().fact();
    public boolean iAllDay = faker.bool().bool();

}
