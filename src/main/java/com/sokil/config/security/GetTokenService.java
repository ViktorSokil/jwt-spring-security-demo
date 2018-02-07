package com.sokil.config.security;


import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetTokenService {

    @Value("${jwt.secret}")
    String key;


    @Autowired
    @Qualifier("authenticationService")
    private UserDetailsService userDetailsService;

    public String getToken(String username, String password) throws AuthenticationException {
        if (username == null || password == null)
            return null;
        User user = (User) userDetailsService.loadUserByUsername(username);
        Map<String, Object> tokenData = new HashMap<>();
        if (password.equals(user.getPassword())) {
            tokenData.put("clientType", "user");
            tokenData.put("username", user.getUsername());
            tokenData.put("password", user.getPassword());
            tokenData.put("token_create_date", new Date().getTime());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, 100);
            tokenData.put("token_expiration_date", calendar.getTime());
            JwtBuilder jwtBuilder = Jwts.builder();
            jwtBuilder.setExpiration(calendar.getTime());
            jwtBuilder.setClaims(tokenData);

            String token = jwtBuilder.signWith(SignatureAlgorithm.HS512, key).compact();
            return token;
        } else {
            throw new AuthenticationCredentialsNotFoundException("Authentication error");
        }
    }

    public User getUserByToken(String token) {
        Map<String, Object> tokenData = (Map) Jwts.parser()
                .setSigningKey(key)
                .parse(token)
                .getBody();
        User retVal = new User(
                tokenData.get("username").toString(),
                tokenData.get("password").toString(),
                new ArrayList<GrantedAuthority>());
        return retVal;
    }
}
