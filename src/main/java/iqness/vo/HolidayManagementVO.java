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
public class HolidayManagementVO {

    @JsonProperty(value = "holiday_management_id")
    private UUID holidayManagementId;

    @JsonProperty(value = "holiday")
    private String holidayDate;

    @JsonProperty(value = "reason")
    private String  reason;

}
