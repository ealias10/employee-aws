package iqness.model;

import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Users extends Audit {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "active")
    private Boolean active;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ToString.Exclude
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", referencedColumnName = "id")
    @ToString.Exclude
    private Employee employee;

}
