package com.example.webproject.companymanagement.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.webproject.companymanagement.domain.app_user.models.Role;
import com.example.webproject.companymanagement.domain.app_user.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.example.webproject.companymanagement.security.filter.constants.SecurityConstants.*;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        var auth = getAuthentication(request,response);
        if (auth != null)
            SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(@NotNull HttpServletRequest request,@NotNull HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HEADER_STRING);
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
             try {
                 String token = authorizationHeader.substring(TOKEN_PREFIX.length());
                 Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
                 JWTVerifier verifier = JWT.require(algorithm).build();
                 DecodedJWT decodedJWT = verifier.verify(token);
                 String username = decodedJWT.getSubject();
                 String role = decodedJWT.getClaim("role").asString();
                 Long id = decodedJWT.getClaim("id").asLong();
                 User user = User
                         .builder()
                         .username(username)
                         .id(id)
                         .role(Role.builder().name(role).build())
                         .build();
                 return new UsernamePasswordAuthenticationToken(user,
                         null,
                         List.of(new SimpleGrantedAuthority(role)));
             }catch (Exception e){
                 response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid token");
             }
        }
           return null;
    }

}
