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

public class ModulesUserUpdateRequest {
    @NotEmpty
    @JsonProperty("eta_date")
    private String etaDate;


    @NotEmpty
    @JsonProperty("note")
    private String note;

    @NotEmpty
    @JsonProperty("status")
    private String status;




}
