package iqness.model;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;
@Entity
@Table(name = "projects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Projects extends Audit {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;

    @Column(name = "eta_date")
    private long etaDate;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "language")
    private String  language;

    @Column(name = "status")
    private String status;

    @Column(name = "proj_id")
    private String projId;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ToString.Exclude
    private Employee employee;

}