package com.jwt.auth.B_Use_Cases.Interfaces;

import com.jwt.auth.C_Interface_Adapters.Controllers.dto.RegisterdUser;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.SaveUser;

public interface AuthenticationService {
    RegisterdUser registerOneCustomer(SaveUser newUser);
}
