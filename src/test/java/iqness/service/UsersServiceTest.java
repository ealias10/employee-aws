package iqness.service;

import iqness.dao.RoleDao;
import iqness.dao.UserDao;
import iqness.exception.*;
import iqness.model.Role;
import iqness.model.Users;
import iqness.request.LoginRequest;
import iqness.request.UserCreateRequest;
import iqness.request.UserPasswordResetRequest;
import iqness.utils.GenerateJWTToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.relation.RoleNotFoundException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {
    @Mock
    UserDao userDao;
    private static final UUID ID = UUID.randomUUID();
    @InjectMocks
    UserService userService;
    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleDao roleDao;

    @Mock
    GenerateJWTToken generateJWTToken;
    @Test
    void resetPasswordByUserNotFoundExceptionTest() {
        Users users=null;
        when(userDao.getUserByName(Mockito.anyString()))
                .thenReturn(users);
        assertThrows(UserNotFoundException.class, () -> userService.resetPasswordByUser(createUserPasswordResetRequest()));
    }

    public UserCreateRequest createUserRequest() {
        return UserCreateRequest .builder().password("password").username("ealias").build();
    }
    void createUserByRoleNotFoundExceptionTest() {
        Role role=null;
        when(roleDao.getRoleByName(Mockito.any()))
                .thenReturn(role);
        assertThrows(
                RoleNotFoundException.class, () -> userService.createUser(createUserRequest()));
    }
    @Test
    void createUserUserExistsTest() {
        when(userDao.getUserByName(Mockito.anyString()))
                .thenReturn(createUser());
        assertThrows(UsersExistsException.class, () -> userService.createUser(createUserRequest()));
    }
    @Test
    void loginUserByAuthenticationFailureExceptionTest()
    {
        LoginRequest request =
                LoginRequest.builder().password("password").username("ealias").build();
        when(userDao.getUserByName(Mockito.anyString()))
                .thenReturn(createUser());
        assertThrows(AuthenticationFailureException.class, () -> userService.loginUser(request));
    }
    @Test
    void createUserUserTest() throws RoleNotFoundException, UsersExistsException, EmployeeNotFoundException {
        Mockito.when(roleDao.getRoleByName(Mockito.any())).thenReturn(getRole());
        Users users=createUser();
        Mockito.when(
                        userDao.saveUser(Mockito.any()))
                .thenReturn(users);
        var request = createUserRequest();
        var response = userService.createUser(request);
        assertEquals("ealias", response.getUserName());
    }
    public Role getRole()
    {
        return Role.builder()
                .name("ADMIN")
                .build();
    }
    public Users createUser()
    {
        return
                Users.builder()
                        .id(ID)
                        .username("ealias")
                        .password("password")
                        .role(getRole())
                        .build();
    }
    public UserPasswordResetRequest createUserPasswordResetRequest()
    {
        return UserPasswordResetRequest.builder()
                .currentPassword("123")
                .username("ealias")
                .currentPassword("epg")
                .build();
    }
}
