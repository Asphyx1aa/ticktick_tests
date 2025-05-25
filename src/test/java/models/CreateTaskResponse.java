package models;

import lombok.Data;

@Data
public class CreateTaskResponse {
    String id;
    String projectId;
    String title;
    String content;
    String desc;
    boolean isAllDay;
}
