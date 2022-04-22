package com.example.webproject.companymanagement.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.webproject.companymanagement.domain.app_user.models.User;
import com.example.webproject.companymanagement.security.detials.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.webproject.companymanagement.security.filter.constants.SecurityConstants.SECRET;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword(),
                    new ArrayList<>());
            return authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        } catch (Throwable e) {
            throw new RuntimeException("Failed to verify authentication check cause", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String access_token = generateToken(authResult);
        Map<String,String> token = new HashMap<>();
        token.put("access_token",access_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),token);
    }

    private String generateToken(Authentication auth) {
        User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
        String token = JWT.create().withSubject(auth.getName())
                .withClaim("id",user.getId())
                .withClaim("username",user.getUsername())
                .withClaim("role",user.getRole().getName())
                .sign(algorithm);
        return token;

    }
}
