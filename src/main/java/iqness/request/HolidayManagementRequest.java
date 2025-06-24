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
public class HolidayManagementRequest {

    @NotNull()
    @JsonProperty("holiday")
    private String holidayDate;

    @NotNull()
    @JsonProperty("reason")
    private String  reason;
}
