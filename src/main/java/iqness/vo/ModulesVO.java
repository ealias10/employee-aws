package iqness.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ModulesVO {

    @JsonProperty(value = "module_id")
    private UUID moduleId;

    @JsonProperty(value = "module_name")
    private String modulesName;

    @JsonProperty(value = "status")
    private String  status;

    @JsonProperty(value = "roles")
    private String  roles;

    @JsonProperty(value = "note")
    private String  note;

    @JsonProperty(value = "eta_date")
    private String  etaDate;

    @JsonProperty(value = "empname")
    private String empname;

    @JsonProperty(value = "project_name")
    private String projectName;

    @JsonProperty(value = "proj_id")
    private String projId;

    @JsonProperty(value = "emp_id")
    private String empId;

    @JsonProperty(value = "email")
    private String email;


}
