package com.jwt.auth.B_Use_Cases.Implementations;

import com.jwt.auth.A_Domain.Users;
import com.jwt.auth.B_Use_Cases.Interfaces.AuthenticationService;
import com.jwt.auth.B_Use_Cases.Interfaces.JwtService;
import com.jwt.auth.B_Use_Cases.Interfaces.UserService;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.RegisterdUser;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.SaveUser;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.auth.AuthenticationRequest;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.auth.AuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Stores a user in db, then it creates a JWT
     * @param newUser
     * @return
     */
    @Override
    public RegisterdUser registerOneCustomer(SaveUser newUser) {
        Users users = userService.registerOneCustomer(newUser);
        RegisterdUser userDto = new RegisterdUser();
        userDto.setId(users.getId());
        userDto.setName(users.getName());
        userDto.setUsername(users.getUsername());
        userDto.setRole(users.getRole().name());

        String jwt = jwtService.generateToken(users, generateExtraClaims(users));
        userDto.setJwt(jwt);
        return userDto;
    }

    /**
     * It uses most common implementation which is UsernamePasswordAuthenticationToken, authenticates current user
     * after that it retrieves current user to generate JWT
     * @param authRequest
     * @return AuthenticationResponse
     */
    @Override
    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        );
        authenticationManager.authenticate(authentication);
        UserDetails users = userService.findOneByUsername(authRequest.getUsername()).get();
        String jwt = jwtService.generateToken(users, generateExtraClaims((Users) users));
        AuthenticationResponse authRsp = new AuthenticationResponse();
        authRsp.setJwt(jwt);

        return authRsp;

    }

    @Override
    public boolean validateToken(String jwt) {
        try{
            jwtService.extractUsername(jwt);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    /**
     * Extracts extra claims like: name, role and authorities
     * @param users
     * @return
     */
    private Map<String, Object> generateExtraClaims(Users users) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", users.getName());
        extraClaims.put("role", users.getRole().name());
        extraClaims.put("authorities", users.getAuthorities());
        return extraClaims;
    }
}
