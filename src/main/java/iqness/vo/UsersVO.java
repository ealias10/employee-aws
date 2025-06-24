package iqness.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UsersVO {

    @JsonProperty(value = "user_id")
    private UUID userId;

    @JsonProperty(value = "user_name")
    private String userName;


    @JsonProperty(value = "user_role")
    private String userRole;



    @JsonProperty(value = "employee_id")
    private String employeeId;


}
