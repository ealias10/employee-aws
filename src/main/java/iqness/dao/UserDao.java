package iqness.dao;


import iqness.model.Employee;
import iqness.model.Users;
import iqness.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserDao {

    @Autowired
    UserRepository userRepository;

    public Users getUserByName(String name) {
        return userRepository.getUserByName(name);
    }


    public Users getUserByNameOrEmpId(String name,String empId)
    {
        return userRepository.getUserByNameOrEmpId(name,empId);
    }

    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    public Users getUserById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public boolean doesUserWithNameExistForUpdate(String name,String empId,UUID id)
    {
        return userRepository.doesUserWithNameExistForUpdate(name,empId,id);
    }

    public Page<Users> listUser(Pageable pageable) {
        return userRepository.findByActiveTrue(pageable);
    }

    public Page<Users> getUserListByName(String name, Pageable pageable) {
        return userRepository.getUserListByName(name, pageable);
    }
    public Page<Users> getUserListByEmployeeId(String name, Pageable pageable) {
        return userRepository.getUserListByEmployeeId(name, pageable);
    }

    public Page<Users> getUserListByNameOrEmployeeId(String name, Pageable pageable) {
        return userRepository.getUserListByNameOrEmployeeId(name, pageable);
    }
    public void deleteUserById(UUID id)
    {
        userRepository.deleteUserById(id);
    }

}
