package iqness.service;

import iqness.dao.EmployeeDao;
import iqness.dao.RoleDao;
import iqness.dao.UserDao;
import iqness.exception.*;
import iqness.mapper.UserMapper;
import iqness.model.Employee;
import iqness.model.Role;
import iqness.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import iqness.request.LoginRequest;
import iqness.request.RefreshTokenRequest;
import iqness.request.UserCreateRequest;
import iqness.request.UserPasswordResetRequest;
import iqness.utils.GenerateJWTToken;
import iqness.utils.Utility;
import iqness.vo.LoginVO;
import iqness.vo.PaginatedResponseVOAndCount;
import iqness.vo.UsersVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.relation.RoleNotFoundException;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@org.springframework.stereotype.Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    GenerateJWTToken generateJWTToken;
    @Value("${access.token.expiry.minutes}")
    private long accessTokenExpiry;

    @Value("${spring.security.jwt.secret}")
    private String jwtSecret;

    @Value("${refresh.token.expiry.minutes}")
    private String refreshTokenExpiry;

    @Autowired
    private EmployeeDao employeeDao;

    public UsersVO createUser(UserCreateRequest request) throws RoleNotFoundException, UsersExistsException, EmployeeNotFoundException {
        try {
            Users existingUser = userDao.getUserByNameOrEmpId(request.getUsername(),request.getEmployeeId());
            if (existingUser != null) {
                throw new UsersExistsException(request.getUsername());
            }
            Employee existingEmployee=employeeDao.getEmployeeByEmpId(request.getEmployeeId());
            if (existingEmployee == null) {
                throw new EmployeeNotFoundException(request.getEmployeeId());
            }
            Role role = roleDao.getRoleByName(request.getUserRole());
            if (role == null) {
                throw new RoleNotFoundException(request.getUserRole());
            }
            Users users = UserMapper.createUser(request, role,existingEmployee);
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            Users savedUser = userDao.saveUser(users);
            log.info("Create user successfully, Request: {}", request);
            return UserMapper.createUserVO(savedUser);
        } catch (Exception e) {
            log.error("Error while creating user,  Request: {}", request);
            throw e;
        }

    }

    public UsersVO resetPasswordByUser(UserPasswordResetRequest userResetRequest)
            throws UserNotFoundException {
        try {
            Users existingUser = userDao.getUserByName(userResetRequest.getUsername());
            if (existingUser == null || !passwordEncoder.matches(userResetRequest.getCurrentPassword(), existingUser.getPassword())) {
                throw new UserNotFoundException(userResetRequest.getUsername());
            }
            existingUser.setPassword(passwordEncoder.encode(userResetRequest.getNewPassword()));
            Users updateUser = userDao.saveUser(existingUser);
            log.info("Updated password successfully, User: {}", userResetRequest.getNewPassword());
            return UserMapper.createUserVO(updateUser);
        } catch (Exception e) {
            log.error("Error while reset password, userId: {}", userResetRequest.getNewPassword());
            throw e;
        }
    }

    public LoginVO loginUser(LoginRequest loginRequest) throws AuthenticationFailureException {
        try {
            Users users = userDao.getUserByName(loginRequest.getUsername());
            if (users == null || !passwordEncoder.matches(loginRequest.getPassword(), users.getPassword())) {
                throw new AuthenticationFailureException();
            }
            String accessToken = getAccessToken(users);
            String refreshToken = getRefreshToken();
            users.setRefreshToken(refreshToken);
            userDao.saveUser(users);
            log.info("User login successfully, Username: {}", loginRequest.getUsername());
            return UserMapper.createLoginVO(accessToken, refreshToken, users);
        } catch (Exception e) {
            log.error("Error while login, userName: {}", loginRequest.getUsername());
            throw e;
        }

    }

    private String getAccessToken(Users user) {
        Map<String, Object> claims = new HashMap<>();
        String role = user.getRole().getName();
        claims.put("sub", user.getId().toString());
        claims.put("name", user.getUsername());
        claims.put("role", role);
        return generateJWTToken.createJWTToken(accessTokenExpiry, claims);
    }

    public LoginVO refreshToken(RefreshTokenRequest refreshTokenRequest)
            throws UserNotFoundException, AuthenticationFailureException {
        String userIdFromToken = null;
        try {
            userIdFromToken = Utility.getUserId();
            UUID userId=UUID.fromString(userIdFromToken);
            Users existingUser = userDao.getUserById(userId);
            if (existingUser == null) {
                throw new UserNotFoundException(UUID.fromString(userIdFromToken));
            }
            if (existingUser.getRefreshToken() == null) {
                throw new AuthenticationFailureException();
            } else if (Boolean.FALSE.equals(
                    Utility.validateToken(refreshTokenRequest.getRefreshToken(), jwtSecret))
                    || !existingUser.getRefreshToken().equals(refreshTokenRequest.getRefreshToken())) {
                existingUser.setRefreshToken(null);
                userDao.saveUser(existingUser);
                throw new AuthenticationFailureException();
            }
            String accessToken = getAccessToken(existingUser);
            String refreshToken = getRefreshToken();
            log.info("User token successfully refreshed, userId: {}", userId);
            existingUser.setRefreshToken(refreshToken);
            userDao.saveUser(existingUser);
            return UserMapper.createLoginVO(accessToken, refreshToken, existingUser);
        } catch (Exception e) {
            log.error("Error while refresh token, userId: {}", userIdFromToken);
            throw e;
        }
    }

    private String getRefreshToken() {
        Map<String, Object> claims = new HashMap<>();
        return generateJWTToken.createJWTToken(Long.parseLong(refreshTokenExpiry), claims);
    }

    public UsersVO getUserByMe() throws UserNotFoundException {
        String userIdFromToken = null;
        userIdFromToken = Utility.getUserId();
        UUID userId=UUID.fromString(userIdFromToken);
        try {
            userIdFromToken = Utility.getUserId();
            Users existingUser = userDao.getUserById(userId);
            if (existingUser == null) {
                throw new UserNotFoundException(UUID.fromString(userIdFromToken));
            }
            log.info("Retrieved user details successfully");
            return UserMapper.createUserVO(existingUser);
        } catch (Exception e) {
            log.error("Error while fetching user details");
            throw e;
        }
    }
    public UsersVO getUserById(UUID userId) throws UserNotFoundException {
        try {
            Users existingUser = userDao.getUserById(userId);
            if (existingUser == null) {
                throw new UserNotFoundException(userId);
            }
            log.info("Retrieved user details successfully");
            return UserMapper.createUserVO(existingUser);
        } catch (Exception e) {
            log.error("Error while fetching user details");
            throw e;
        }
    }
    public UsersVO updateUser(UserCreateRequest request,UUID userId) throws UserNotFoundException, RoleNotFoundException, UsersExistsException, EmployeeNotFoundException {
        try {

            Users existingUser = userDao.getUserById(userId);
            if (existingUser == null) {
                throw new UserNotFoundException(userId);
            }
            boolean userWithNameExistForUpdate=userDao.doesUserWithNameExistForUpdate(request.getUsername(),request.getEmployeeId(),userId);
            if (userWithNameExistForUpdate) {
                throw new UsersExistsException(request.getEmployeeId(),request.getUsername());
            }
            Role role = roleDao.getRoleByName(request.getUserRole());
            if (role == null) {
                throw new RoleNotFoundException(request.getUserRole());
            }
            Employee existingEmployee=employeeDao.getEmployeeByEmpId(request.getEmployeeId());
            if (existingEmployee == null) {
                throw new EmployeeNotFoundException(request.getEmployeeId());
            }
            Users users = UserMapper.updateUser(request, role,existingUser,existingEmployee);
            Users savedUser = userDao.saveUser(users);
            log.info("Edit user successfully, Request: {}", request);
            return UserMapper.createUserVO(savedUser);
        } catch (Exception e) {
            log.error("Error while editing user,  Request: {}", request);
            throw e;
        }
    }

    public PaginatedResponseVOAndCount<UsersVO> listUser(Integer offset, Integer limit, String search,String filter) {
        try {
            Pageable pageable = PageRequest.of(offset - 1, limit);
            Page<Users> userList;
            if (search == null || search.trim() == "") {
                userList = userDao.listUser(pageable);
            } else {
                if(filter.equals("Name"))
                userList = userDao.getUserListByName(search.toUpperCase(), pageable);
                else if(filter.equals("Employee Id"))
                userList = userDao.getUserListByEmployeeId(search.toUpperCase(), pageable);
                else
                userList = userDao.getUserListByNameOrEmployeeId(search.toUpperCase(), pageable);
            }
            log.info("Retrieved user list successfully");
            var userVOList = UserMapper.createListOfUserVO(userList.toList());
            return new PaginatedResponseVOAndCount<>(
                    userList.getTotalElements(), userVOList);
        } catch (Exception e) {
            log.error("Error while Retrieved user list");
            throw e;
        }
    }
    public void deleteUser(UUID userId) throws UserNotFoundException {
        try {

            Users existingUsers = userDao.getUserById(userId);
            if (existingUsers == null) {
                throw new UserNotFoundException(userId);

            }
            userDao.deleteUserById(existingUsers.getId());
            log.info(
                    "Deleted user successfully, userId: {}", userId);
        } catch (Exception e) {
            log.error(
                    "Error while deleting user,userId: {} ", userId);
            throw e;
        }
    }

}
