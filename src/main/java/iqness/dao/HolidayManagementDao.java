package iqness.dao;
import iqness.model.Assets;
import iqness.model.Employee;
import iqness.model.HolidayManagement;
import iqness.repository.HolidayManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class HolidayManagementDao {

    @Autowired
    HolidayManagementRepository holidayManagementRepository;

    public HolidayManagement getHolidayByDate(long date)
    {
        return holidayManagementRepository.getHolidayByDate(date);
    }

    public HolidayManagement getUpdateHolidayByDate(UUID id, long date)
    {
        return holidayManagementRepository.getUpdateHolidayByDate(id,date);
    }
    public HolidayManagement getHolidayById(UUID id)
    {
        return holidayManagementRepository.findByIdAndActiveTrue(id).orElse(null);
    }


    public Page<HolidayManagement> listHoliday(Pageable pageable) {
        return holidayManagementRepository.findByActiveTrue(pageable);
    }

    public HolidayManagement save(HolidayManagement entity)
    {
        return holidayManagementRepository.save(entity);
    }
}
