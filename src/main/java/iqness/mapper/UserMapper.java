package iqness.mapper;


import iqness.model.Employee;
import iqness.model.Role;
import iqness.model.Users;
import iqness.request.UserCreateRequest;
import iqness.vo.EmployeeVO;
import iqness.vo.LoginVO;
import iqness.vo.UsersVO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static Users createUser(UserCreateRequest request, Role role, Employee existingEmployee) {
        return Users.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(role)
                .active(true)
                .employee(existingEmployee)
                .build();
    }
   public static Users updateUser(UserCreateRequest request, Role role,Users existingUser,Employee employee)
   {
       existingUser.setRole(role);
       existingUser.setUsername(request.getUsername());
       existingUser.setEmployee(employee);
       return existingUser;
   }
    public static UsersVO createUserVO(Users user) {
        return UsersVO.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .userRole(user.getRole().getName())
                .employeeId(user.getEmployee().getEmpId())
                .build();
    }

    public static LoginVO createLoginVO(String accessToken, String refreshToken, Users users) {
        return LoginVO.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

    public static List<UsersVO> createListOfUserVO(List<Users> usersList)
    {
        return usersList.stream()
                .map(
                        users ->
                                new UsersVO(
                                        users.getId(),
                                        users.getUsername(),
                                        users.getRole().getName(),
                                        users.getEmployee().getEmpId()
                                ))
                .collect(Collectors.toList());
    }
}
