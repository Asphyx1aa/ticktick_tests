package models;

import lombok.Data;

@Data
public class UpdateTaskResponse {
    String id;
    String projectId;
    String title;
    String desc;
    boolean isAllDay;
}
