package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTaskRequest {
    String id;
    String projectId;
    String desc;
    boolean isAllDay;
}
