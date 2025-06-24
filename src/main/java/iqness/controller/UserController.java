package iqness.controller;

import iqness.exception.*;
import iqness.request.LoginRequest;
import iqness.request.RefreshTokenRequest;
import iqness.request.UserCreateRequest;
import iqness.request.UserPasswordResetRequest;
import iqness.service.UserService;
import iqness.vo.LoginVO;
import iqness.vo.PaginatedResponseVOAndCount;
import iqness.vo.ResponseVO;
import iqness.vo.UsersVO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import javax.validation.constraints.Min;
import java.util.UUID;


@RestController
@Slf4j
@Validated
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ResponseVO<Object>> createUser(
            @RequestBody(required = true) UserCreateRequest request) throws RoleNotFoundException, UsersExistsException, EmployeeNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        UsersVO usersVO = userService.createUser(request);
        response.addData(usersVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ResponseVO<Object>> editUser(@PathVariable("userId") UUID userId,
            @RequestBody(required = true) UserCreateRequest request) throws RoleNotFoundException, UsersExistsException, EmployeeNotFoundException, UserNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        UsersVO usersVO = userService.updateUser(request,userId);
        response.addData(usersVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/reset-password")
    public ResponseEntity<ResponseVO<Object>> resetPasswordByUser(
            @RequestBody(required = true) UserPasswordResetRequest userResetRequest) throws UserNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        UsersVO usersVO = userService.resetPasswordByUser(userResetRequest);
        response.addData(usersVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseVO<Object>> loginUser(
            @RequestBody(required = true) LoginRequest loginRequest) throws AuthenticationFailureException {
        ResponseVO<Object> response = new ResponseVO<>();
        LoginVO loginVO = userService.loginUser(loginRequest);
        response.addData(loginVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseVO<Object>> refreshToken(
            @RequestBody(required = true) RefreshTokenRequest refreshTokenRequest) throws UserNotFoundException, AuthenticationFailureException {
        ResponseVO<Object> response = new ResponseVO<>();
        LoginVO loginVO = userService.refreshToken(refreshTokenRequest);
        response.addData(loginVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseVO<Object>> getUserByMe() throws UserNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        UsersVO usersVO = userService.getUserByMe();
        response.addData(usersVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{userId}/details")
    public ResponseEntity<ResponseVO<Object>> getUserById(@PathVariable("userId") UUID userId) throws UserNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        UsersVO usersVO = userService.getUserById(userId);
        response.addData(usersVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseVO<UsersVO>> listUser(@Parameter(description = "offset, start from 1")
                                                               @RequestParam(value = "offset", required = false, defaultValue = "1") @Min(1) Integer offset,
                                                               @Parameter(description = "limit") @RequestParam(value = "limit", required = false, defaultValue = "10") @Min(1) Integer limit,
                                                               @RequestParam(value = "search", required = false) String search,
                                                               @RequestParam(value = "filter", required = false) String filter) {
        ResponseVO<UsersVO> response = new ResponseVO<>();
        PaginatedResponseVOAndCount<UsersVO> paginatedResponseVOAndCount = userService.listUser(offset, limit, search,filter);
        response.addPaginatedDataList(paginatedResponseVOAndCount.getData(), paginatedResponseVOAndCount.getTotalCount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ResponseVO<Object>> deleteUser(@PathVariable("userId") UUID userId) throws UserNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        userService.deleteUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
