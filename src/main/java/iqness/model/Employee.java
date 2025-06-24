package iqness.model;

import java.util.UUID;
import javax.persistence.*;

import iqness.model.enums.Gender;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Employee extends Audit {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;


    @Column(name = "name")
    private String name;

    @Column(name = "emp_id")
    private String empId;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "experience")
    private Integer experience;


    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "phone_number")
    private String phoneNumber;


    @Column(name = "active")
    private Boolean active;

    @Column(name = "email")
    private String email;


    @Column(name = "blood_group")
    private String bloodGroup;


    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;


    @Column(name = "address")
    private String address;

    @Column(name = "age")
    private Integer age;

    @Column(name = "pin")
    private Integer pin;


}
