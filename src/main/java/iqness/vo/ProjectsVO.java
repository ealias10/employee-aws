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
public class ProjectsVO {

    @JsonProperty(value= "project_id")
    private UUID projectId;

    @JsonProperty(value = "proj_id")
    private String projId;

    @JsonProperty(value = "project_name")
    private String projectName;

    @JsonProperty(value = "status")
    private String  status;

//    @JsonProperty(value = "roles")
//    private String  roles;
//
//    @JsonProperty(value = "note")
//    private String  note;

    @JsonProperty(value = "eta_date")
    private String  etaDate;

    @JsonProperty(value = "empname")
    private String empname;

    @JsonProperty(value = "language")
    private String language;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "empId")
    private String empId;



}
