package iqness.service;

import iqness.dao.HolidayManagementDao;
import iqness.dao.LeaveManagementDao;
import iqness.exception.EmployeeNotFoundException;
import iqness.exception.HolidayManagementExistsException;
import iqness.exception.HolidayManagementNotFountException;
import iqness.exception.LeaveManagementExistsException;
import iqness.mapper.EmployeeMapper;
import iqness.mapper.HolidayManagementMapper;
import iqness.mapper.LeaveManagementMapper;
import iqness.model.Employee;
import iqness.model.HolidayManagement;
import iqness.model.LeaveManagement;
import iqness.request.HolidayManagementRequest;
import iqness.utils.Utility;
import iqness.vo.EmployeeVO;
import iqness.vo.HolidayManagementVO;
import iqness.vo.PaginatedResponseVOAndCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static java.util.UUID.fromString;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class HolidayManagementService {

    @Autowired
    private HolidayManagementDao holidayManagementDao;

   public HolidayManagementVO createHoliday(HolidayManagementRequest request) throws HolidayManagementExistsException, ParseException {
       try {
           HolidayManagement existingHolidayManagement = holidayManagementDao.getHolidayByDate(getDate(request.getHolidayDate()));
           if (existingHolidayManagement != null) {
               throw new HolidayManagementExistsException(request.getHolidayDate());
           }
           HolidayManagement createHolidayManagement= HolidayManagementMapper.createHolidayManagement(request,getDate(request.getHolidayDate()));
           HolidayManagement holidayManagementSave=holidayManagementDao.save(createHolidayManagement);
           return HolidayManagementMapper.createHolidayManagementVO(holidayManagementSave);
       } catch (Exception e) {
           log.error("Error while creating holiday,  Request: {}", request);
           throw e;
       }
   }

    public HolidayManagementVO updateHolidayById(UUID holidayId, HolidayManagementRequest request) throws HolidayManagementExistsException, ParseException, HolidayManagementNotFountException {
        try {
            HolidayManagement existingHolidayManagement=holidayManagementDao.getHolidayById(holidayId);
            if(existingHolidayManagement==null)
            {
                throw new HolidayManagementNotFountException(holidayId);
            }
            HolidayManagement doesHolidayExistDateForUpdate = holidayManagementDao.getUpdateHolidayByDate(holidayId,getDate(request.getHolidayDate()));
            if (doesHolidayExistDateForUpdate != null) {
                throw new HolidayManagementExistsException(request.getHolidayDate());
            }
            HolidayManagement updateHolidayManagement= HolidayManagementMapper.updateHolidayManagement(request,existingHolidayManagement,getDate(request.getHolidayDate()));
            HolidayManagement holidayManagementSave=holidayManagementDao.save(updateHolidayManagement);
            return HolidayManagementMapper.createHolidayManagementVO(holidayManagementSave);
        } catch (Exception e) {
            log.error("Error while creating holiday,  Request: {}", request);
            throw e;
        }
    }

    public HolidayManagementVO getHoliDayById(UUID holidayId) throws HolidayManagementNotFountException {
        try {
            HolidayManagement existingHolidayManagement = holidayManagementDao.getHolidayById(holidayId);
            if (existingHolidayManagement == null) {
                throw new HolidayManagementNotFountException(holidayId);
            }
            return HolidayManagementMapper.createHolidayManagementVO(existingHolidayManagement);
        }catch (Exception e) {
            log.error("Error while getHoliday By Id,  Request: {}", holidayId);
            throw e;
        }

    }

    public PaginatedResponseVOAndCount<HolidayManagementVO> listHoliday(Integer offset, Integer limit, String search) {
        try {
            Pageable pageable = PageRequest.of(offset - 1, limit);
            Page<HolidayManagement> holidayManagementsList;
            if (search == null || search.trim() == "") {
                holidayManagementsList = holidayManagementDao.listHoliday(pageable);
            } else {
                holidayManagementsList = holidayManagementDao.listHoliday(pageable);
            }
            log.info("Retrieved holiday list successfully");
            var holidayManagementVOList = HolidayManagementMapper.createListOfHolidayManagementVO(holidayManagementsList.toList());
            return new PaginatedResponseVOAndCount<>(
                    holidayManagementsList.getTotalElements(), holidayManagementVOList);
        } catch (Exception e) {
            log.error("Error while Retrieved holiday list");
            throw e;
        }
    }

    public void deleteHoliday(UUID holidayId) throws HolidayManagementNotFountException {
        try {
            HolidayManagement existingHolidayManagement = holidayManagementDao.getHolidayById(holidayId);
            if (existingHolidayManagement == null) {
                throw new HolidayManagementNotFountException(holidayId);
            }
            existingHolidayManagement.setActive(false);
            HolidayManagement delete=holidayManagementDao.save(existingHolidayManagement);
        }catch (Exception e) {
            log.error("Error while delete Holiday By Id,  Request: {}", holidayId);
            throw e;
        }
    }


    private Long getDate(String parameterDate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parameterDate);
        return  date.getTime();
    }
}
