package iqness.model;
import iqness.model.enums.Type;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "assets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Assets extends Audit {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;


    @Column(name = "warranty_end_date")
    private long warrantyEndDate;

    @Column(name = "purchase_date")
    private long purchaseDay;

    @Column(name = "serial_no")
    private String serialNo;

    @Column(name = "model")
    private String model;

    @Column(name = "brand")
    private String brand;

    @JoinColumn(name = "allocated_to")
    private long allocatedTo;


    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name="activeallocations")
    private Boolean activeAllocations;

    @Column(name = "active")
    private Boolean active;

}
