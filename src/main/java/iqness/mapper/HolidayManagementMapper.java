package iqness.mapper;

import iqness.model.Assets;
import iqness.model.HolidayManagement;
import iqness.request.HolidayManagementRequest;
import iqness.vo.AssetsVO;
import iqness.vo.HolidayManagementVO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HolidayManagementMapper {
    public  static HolidayManagement createHolidayManagement(HolidayManagementRequest request,long holiday)
    {
        return
                HolidayManagement.builder()
                        .active(true)
                        .holidayDate(holiday)
                        .reason(request.getReason())
                        .build();
    }
    public  static HolidayManagementVO createHolidayManagementVO(HolidayManagement request)
    {
        return
                HolidayManagementVO.builder()
                        .holidayDate(getLongConverToDate(request.getHolidayDate()))
                        .reason(request.getReason())
                        .holidayManagementId(request.getId())
                        .build();
    }

    public static HolidayManagement updateHolidayManagement(HolidayManagementRequest request,HolidayManagement holidayManagement,long holiday)
    {
         holidayManagement.setHolidayDate(holiday);
         holidayManagement.setReason(request.getReason());
         return holidayManagement;
    }

    public static List<HolidayManagementVO> createListOfHolidayManagementVO(List<HolidayManagement> holidayList)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return holidayList.stream()
                .map(
                        holiday ->
                                new HolidayManagementVO(
                                        holiday.getId(),
                                        sdf.format(new Date(holiday.getHolidayDate())),
                                        holiday.getReason()

                                ))
                .collect(Collectors.toList());

    }
    public static String getLongConverToDate(Long value)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(value));
    }
}
