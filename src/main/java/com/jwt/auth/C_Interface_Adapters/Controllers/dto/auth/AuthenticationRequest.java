package com.jwt.auth.C_Interface_Adapters.Controllers.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
/**
 * @Autor: Daniel Larin
 * It works to send username and pass and then validating them to return a JWT
 */
public class AuthenticationRequest implements Serializable {
    private String username;
    private String password;
}
