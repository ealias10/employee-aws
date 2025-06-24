package iqness.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EmployeeVO {


    @JsonProperty(value = "name")
    private String name
            ;
    @JsonProperty(value = "employee_id")
    private UUID employeeId;

    @JsonProperty(value = "empId")
    private String empId;

    @JsonProperty(value = "qualification")
    private String qualification;

    @JsonProperty(value = "experience")
    private Integer experience;

    @JsonProperty(value = "photourl")
    private String photoUrl;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "blood_group")
    private String bloodGroup;

    @JsonProperty(value = "address")
    private String address;


    @JsonProperty(value = "pin")
    private Integer pin;

    @JsonProperty(value = "age")
    private Integer age;

    @JsonProperty(value = "gender")
    private String gender;

    @JsonProperty(value = "assets_allocated")
    private List<AssetsAllocationsVO> assetsAllocated;

    @JsonProperty(value = "created_at")
    private String createdAt;

    @JsonProperty(value = "created_by")
    private String createdBy;

    @JsonProperty(value = "modified_at")
    private String modifiedAt;

    @JsonProperty(value = "modified_by")
    private String modifiedBy;

}
