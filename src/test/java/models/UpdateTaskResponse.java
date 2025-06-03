package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateTaskResponse {
    @JsonProperty("id")
    String id;

    @JsonProperty("projectId")
    String projectId;

    @JsonProperty("title")
    String title;

    @JsonProperty("desc")
    String desc;

    @JsonProperty("isAllDay")
    boolean isAllDay;
}
