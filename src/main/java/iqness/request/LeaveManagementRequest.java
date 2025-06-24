package iqness.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LeaveManagementRequest {

    @NotNull()
    @JsonProperty("from_date")
    private String fromDate;

    @NotNull()
    @JsonProperty("to_date")
    private String toDate;

    @NotNull()
    @JsonProperty("reason")
    private String reason;


}
