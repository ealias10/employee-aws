package iqness.vo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.JoinColumn;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AssetsAllocationsVO {

    @JsonProperty(value = "assetsallocations_id")
    private UUID assetsallocationsId;


    @JsonProperty(value = "serial_no")
    private String serialNo;

    @JsonProperty(value = "empId")
    private String empId;

    @JsonProperty(value = "empname")
    private String empname;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "model")
    private String model;

    @JsonProperty(value = "brand")
    private String brand;

    @JsonProperty(value = "allocated_from")
    private String allocatedFrom;

    @JsonProperty(value = "allocated_to")
    private String allocatedTo;

}
