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
public class LeaveManagementVO {

    @JsonProperty(value = "leave_management_id")
    private UUID leaveManagementId;

    @JsonProperty(value = "from_date")
    protected String fromDate;

    @JsonProperty(value = "to_date")
    protected String toDate;

    @JsonProperty(value = "reason")
    protected String  reason;

    @JsonProperty(value = "status")
    protected String status;

    @JsonProperty(value = "employee_name")
    private String employeeName;

    @JsonProperty(value = "empId")
    private String empId;

    @JsonProperty(value = "created_at")
    private String createdAt;

    @JsonProperty(value = "leave_approved")
    private Boolean leaveApproved;

    @JsonProperty(value = "button_color")
    private String buttonColor;

    @JsonProperty(value = "text_color")
    private String textColor;


}
