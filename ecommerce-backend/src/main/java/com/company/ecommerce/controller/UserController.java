package com.company.ecommerce.controller;

import com.company.ecommerce.config.SignUpResponseDto;
import com.company.ecommerce.dto.user.SignInDto;
import com.company.ecommerce.dto.user.SignInResponseDto;
import com.company.ecommerce.dto.user.SignupDto;
import com.company.ecommerce.dto.user.UserListDTO;
import com.company.ecommerce.exception.AuthenticationFailException;
import com.company.ecommerce.exception.CustomException;
import com.company.ecommerce.model.User;
import com.company.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public SignUpResponseDto Signup(@RequestBody SignupDto signupDto) throws CustomException {
        return userService.signUp(signupDto);
    }

    @PostMapping("/signIn")
    public SignInResponseDto SignIn(@RequestBody SignInDto signInDto) throws CustomException, AuthenticationFailException{
        return userService.signIn(signInDto);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getUsers(){
        List<User> allUsers = userService.listUser();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserListDTO>> getAllUsers(){
        List<UserListDTO> allUsers = userService.listUserAdmin();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
}
