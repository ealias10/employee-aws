package iqness.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import iqness.model.enums.Gender;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EmployeeCreateRequest {
    @NotNull()
    @JsonProperty("empname")
    private String employeeName;

    @NotNull()
    @JsonProperty("qualification")
    private String qualification;

    @NotNull()
    @JsonProperty("experience")
    private Integer experience;

    @NotNull()
    @JsonProperty("phone_number")
    private String phone_number;

    @NotNull()
    @JsonProperty("email")
    private String email;

    @NotNull()
    @JsonProperty("blood_group")
    private String bloodGroup;

    @NotNull()
    @JsonProperty("address")
    private String address;


    @NotNull()
    @JsonProperty("pin")
    private Integer pin;

    @NotNull()
    @JsonProperty("age")
    private Integer age;

    @NotNull()
    @JsonProperty(value = "gender")
    private Gender gender;


}
