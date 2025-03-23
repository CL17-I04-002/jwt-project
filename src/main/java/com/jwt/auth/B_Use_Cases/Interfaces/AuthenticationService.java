package com.jwt.auth.B_Use_Cases.Interfaces;

import com.jwt.auth.A_Domain.security.Users;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.RegisterdUser;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.SaveUser;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.auth.AuthenticationRequest;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.auth.AuthenticationResponse;
import jakarta.validation.Valid;

public interface AuthenticationService {
    RegisterdUser registerOneCustomer(SaveUser newUser);

    AuthenticationResponse login(@Valid AuthenticationRequest authenticationRequest);

    boolean validateToken(String jwt);
    Users findLoggedInUser();
}
