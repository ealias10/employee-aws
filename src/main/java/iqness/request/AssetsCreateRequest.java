package iqness.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import iqness.model.enums.Type;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AssetsCreateRequest {

    @NotNull()
    @JsonProperty("serial_no")
    private String serialNo;

    @NotNull()
    @JsonProperty("warranty_end_date")
    private String warrantyEndDate;

    @NotNull()
    @JsonProperty("model")
    private String model;

    @NotNull()
    @JsonProperty("brand")
    private String brand;

    @NotNull()
    @JsonProperty("allocated_to")
    protected long allocatedTo;

    @NotNull()
    @JsonProperty("type")
    private Type type;

    @NotNull()
    @JsonProperty("purchase_day")
    private String purchaseDay;


}
