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
public class UserCreateRequest {
    @NotEmpty
    @JsonProperty(value = "user_name")
    private String username;

    @NotEmpty
    @JsonProperty(value = "password")
    private String password;

    @NotEmpty
    @JsonProperty(value = "user_role")
    private String userRole;

    @NotEmpty
    @JsonProperty(value = "employee_id")
    private String employeeId;


}

