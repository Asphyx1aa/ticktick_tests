package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTaskRequest {
    String title;
    String content;
    String desc;
    boolean isAllDay;
}
