package iqness.model;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;
@Entity
@Table(name = "leave_management")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LeaveManagement extends Audit {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;

    @Column(name = "from_date")
    private long fromDate;

    @Column(name = "to_date")
    private long toDate;

    @Column(name = "reason")
    private String  reason;

    @Column(name = "status")
    private Boolean status;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", referencedColumnName = "id")
    @ToString.Exclude
    private Employee employee;


    @Column(name = "active")
    private Boolean active;

    @Column(name = "leave_apply_date")
    private long leaveApplyDate;

}
