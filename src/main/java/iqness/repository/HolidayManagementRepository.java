package iqness.repository;

import iqness.model.Employee;
import iqness.model.HolidayManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface HolidayManagementRepository extends JpaRepository<HolidayManagement, UUID> {
    @Query("select h from HolidayManagement h where h.active='true' and h.holidayDate=:date ")
    HolidayManagement getHolidayByDate(@Param("date")long date);
    @Query("select h from HolidayManagement h where h.active='true' and h.id<>:id and h.holidayDate=:date ")
    HolidayManagement getUpdateHolidayByDate(@Param("id")UUID id,@Param("date")long date);

    Optional<HolidayManagement> findByIdAndActiveTrue(UUID id);

    Page<HolidayManagement> findByActiveTrue(Pageable pageable);
}
