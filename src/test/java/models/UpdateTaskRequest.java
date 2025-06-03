package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTaskRequest {
    String id;
    String projectId;
    String desc;

    @JsonProperty("isAllDay")
    boolean isAllDay;
}
