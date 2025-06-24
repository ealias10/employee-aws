package iqness.dao;

import iqness.model.Users;
import iqness.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {
    @InjectMocks
    UserDao userDao;
    @Mock
    UserRepository userRepository;
    private static final UUID ID = UUID.randomUUID();

    @Test
    void  getUserByNameTest() {
        Mockito.when(
                userRepository.getUserByName(
                        Mockito.anyString())).thenReturn(getUsers());
        var response =userDao.getUserByName("ealias");
        assertEquals(ID, response.getId());
    }
    private static Users getUsers() {
        return Users.builder().id(ID).build();
    }
    @Test
    void saveUserTest() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(getUsers());
        var response = userDao.saveUser(new Users());
        assertEquals(ID, response.getId());
    }
    @Test
    void getActiveUserByIdTest() {
        Mockito.when(userRepository.findByIdAndActiveTrue(Mockito.any()))
                .thenReturn(Optional.of(getUser()));
        var response = userDao.getUserById(ID);
        assertEquals(ID, response.getId());
    }
    private static Users getUser() {
        return Users.builder().id(ID).username("userName").build();
    }
}
