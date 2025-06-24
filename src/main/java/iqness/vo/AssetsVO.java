package iqness.vo;
import com.fasterxml.jackson.annotation.JsonProperty;
import iqness.model.enums.Type;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AssetsVO {


    @JsonProperty(value = "assets_id")
    private UUID assetsId;

    @JsonProperty(value = "warranty_end_date")
    private String warrantyEndDate;

    @JsonProperty(value = "serial_no")
    private String serialNo;

    @JsonProperty(value = "model")
    private String model;

    @JsonProperty(value = "brand")
    private String brand;

    @JsonProperty(value = "allocated_to")
    private String allocatedTo;

    @JsonProperty(value = "type")
    private String type;


    @JsonProperty(value = "does_allocations")
    private String does_allocations;

    @JsonProperty(value = "allocations")
    private String allocations;


    @JsonProperty(value = "assets_list")
    private List<String> assetsList;

    @JsonProperty(value = "purchase_day")
    private String purchaseDay;


}
