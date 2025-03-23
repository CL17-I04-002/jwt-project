package com.jwt.auth.D_Infraestructure.security.filter;

import com.jwt.auth.A_Domain.security.Users;
import com.jwt.auth.B_Use_Cases.Exception.ObjectNotFoundException;
import com.jwt.auth.B_Use_Cases.Interfaces.JwtService;
import com.jwt.auth.B_Use_Cases.Interfaces.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    private final String userNotFound;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService,
                                   @Value("${user.not.found}") String userNotFound) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.userNotFound = userNotFound;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Gets Auth call header
        String authorizationHeader = request.getHeader("Authorization");
        if(!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        //2. Gets token from head
        String jwt = authorizationHeader.split(" ")[1];

        //3. Gets subj/user from jwt, it validates token format, signature y issuedat
        String username = jwtService.extractUsername(jwt);

        //4. Sets auth obj within security context holder
        Users users = userService.findOneByUsername(username).orElseThrow(() -> new ObjectNotFoundException(userNotFound + ": " + username));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username,
                null,
                users.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //5. Executes filter's registry
        filterChain.doFilter(request, response);
    }
}
