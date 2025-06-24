package iqness.model;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "assets_allocations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AssetsAllocations extends Audit {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", referencedColumnName = "id")
    @ToString.Exclude
    private Employee employee;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "assets_id", referencedColumnName = "id")
    @ToString.Exclude
    private Assets assets;

    @JoinColumn(name = "allocated_from")
    private long allocatedFrom;

    @JoinColumn(name = "allocated_to")
    private long allocatedTo;


}
