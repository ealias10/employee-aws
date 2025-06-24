package iqness.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class ProjectsCreateRequest {

    @NotEmpty
    @JsonProperty(value = "project_name")
    private String projectName;

    @NotEmpty
    @JsonProperty(value = "employee_id")
    private String employeeId;

    @NotEmpty
    @JsonProperty("language")
    private String language;

    @NotEmpty
    @JsonProperty("eta_date")
    private String etaDate;
}
