package iqness.model;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;
@Entity
@Table(name = "holiday_management")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class HolidayManagement  extends Audit {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;

    @Column(name = "holiday_date")
    private long holidayDate;

    @Column(name = "reason")
    private String  reason;

    @Column(name = "active")
    private Boolean active;

}
