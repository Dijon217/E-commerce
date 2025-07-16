package com.company.ecommerce.service;

import com.company.ecommerce.controller.MessageStrings;
import com.company.ecommerce.exception.AuthenticationFailException;
import com.company.ecommerce.model.AuthenticationToken;
import com.company.ecommerce.model.User;
import com.company.ecommerce.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    private TokenRepository tokenRepository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findTokenByUser(user);
    }

    public User getUser(String token) {
        if(token == null || token.trim().isEmpty()){
            return null;
        }
        AuthenticationToken authenticationToken = tokenRepository.findTokenByToken(token.trim());
        return authenticationToken.getUser();
    }

    public void authenticate(String token) throws AuthenticationFailException {
        if(token == null || token.trim().isEmpty()){
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }

        User user = getUser(token);
        if(user == null){
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_VALID);
        }
    }
}
