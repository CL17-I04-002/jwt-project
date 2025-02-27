package com.jwt.auth.C_Interface_Adapters.Controllers;

import com.jwt.auth.B_Use_Cases.Interfaces.AuthenticationService;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.auth.AuthenticationRequest;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.auth.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Signs in a user and returning a jwt
     * @param authenticationRequest
     * @return ResponseEntity<AuthenticationResponse>
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest){
        AuthenticationResponse rsp = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(rsp);

    }
}
