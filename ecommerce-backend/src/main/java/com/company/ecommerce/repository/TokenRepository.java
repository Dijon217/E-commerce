package com.company.ecommerce.repository;

import com.company.ecommerce.model.AuthenticationToken;
import com.company.ecommerce.model.User;
import com.sun.security.jgss.AuthorizationDataEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findTokenByUser(User user);
    AuthenticationToken findTokenByToken(String token);
}
