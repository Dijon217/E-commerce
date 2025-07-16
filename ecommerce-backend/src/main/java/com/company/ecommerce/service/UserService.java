package com.company.ecommerce.service;

import com.company.ecommerce.config.SignUpResponseDto;
import com.company.ecommerce.controller.MessageStrings;
import com.company.ecommerce.dto.user.SignInDto;
import com.company.ecommerce.dto.user.SignInResponseDto;
import com.company.ecommerce.dto.user.SignupDto;
import com.company.ecommerce.dto.user.UserListDTO;
import com.company.ecommerce.exception.AuthenticationFailException;
import com.company.ecommerce.exception.CustomException;
import com.company.ecommerce.model.AuthenticationToken;
import com.company.ecommerce.model.User;
import com.company.ecommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public SignUpResponseDto signUp(SignupDto signupDto)  throws CustomException {
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
            throw new CustomException("User already exists");
        }

        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }

        User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(), encryptedPassword );
        try {
            userRepository.save(user);
            final AuthenticationToken authenticationToken = new AuthenticationToken(user);
            authenticationService.saveConfirmationToken(authenticationToken);
            return new SignUpResponseDto("success", "user created successfully");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }

    public SignInResponseDto signIn(SignInDto signInDto) throws AuthenticationFailException, CustomException {
        User user = userRepository.findByEmail(signInDto.getEmail());
        if(!Objects.nonNull(user)){
            throw new AuthenticationFailException("user not present");
        }
        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                throw  new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

        AuthenticationToken token = authenticationService.getToken(user);
        if(!Objects.nonNull(token)) {
            throw new CustomException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }
        return new SignInResponseDto("success", token.getToken(), user.getRole());
    }

    public List<User> listUser(){
        return userRepository.findAll();
    }

    public List<UserListDTO> listUserAdmin() {
        List<User> uList = listUser();
        List<UserListDTO> uListDTO = new ArrayList<>();

        for(User u: uList){
            uListDTO.add(new UserListDTO(u));
        }
        return uListDTO;
    }
}
