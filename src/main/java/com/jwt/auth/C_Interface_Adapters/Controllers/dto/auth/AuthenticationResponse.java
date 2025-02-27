package com.jwt.auth.C_Interface_Adapters.Controllers.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
/**
 * @Autor: Daniel Larin
 * Returns JWT
 */
public class AuthenticationResponse implements Serializable {
    private String jwt;
}
