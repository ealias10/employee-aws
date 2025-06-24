package iqness.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AssetsAllocationsCreateRequest {

    @NotEmpty
    @JsonProperty(value = "employee_id")
    private String employeeId;

    @NotNull()
    @JsonProperty("serial_no")
    private String serialNo;

}
