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
public class ModulesAdminUpdateRequest {


    @NotEmpty
    @JsonProperty("module_name")
    private String modulesName;

    @NotEmpty
    @JsonProperty("roles")
    private String roles;


    @NotEmpty
    @JsonProperty("note")
    private String note;

    @NotEmpty
    @JsonProperty("status")
    private String status;

    @NotEmpty
    @JsonProperty(value = "employee_id")
    private String employeeId;

    @NotEmpty
    @JsonProperty(value = "project_id")
    private String projectId;

}
