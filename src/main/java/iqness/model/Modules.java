package iqness.model;

import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;
@Entity
@Table(name = "modules")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Modules extends Audit {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;


    @Column(name = "module_name")
    private String modulesName;

    @Column(name = "status")
    private String status;

    @Column(name = "roles")
    private String roles;

    @Column(name = "note")
    private String note;

    @Column(name = "eta_date")
    private long etaDate;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ToString.Exclude
    private Projects projects;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ToString.Exclude
    private Employee employee;


}
