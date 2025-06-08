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

    // save the confirmation token
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    // get token of the User
    public AuthenticationToken getToken(User user) {
        return tokenRepository.findTokenByUser(user);
    }

    // get User from the token
    public User getUser(String token) {
        String theRealToken = token.trim();
        AuthenticationToken authenticationToken = tokenRepository.findTokenByToken(theRealToken);
        if (authenticationToken == null) {
            System.out.println("Token not found: " + theRealToken);
            return null;
        }
        if (authenticationToken.getUser() == null) {
            System.out.println("Token exists, but no user associated with token: " + theRealToken);
            return null;
        }
        return authenticationToken.getUser();
    }

    // check if the token is valid
    public void authenticate(String token) throws AuthenticationFailException {
        if (!Objects.nonNull(token)) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }
        if (!Objects.nonNull(getUser(token))) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_VALID);
        }
    }
}
