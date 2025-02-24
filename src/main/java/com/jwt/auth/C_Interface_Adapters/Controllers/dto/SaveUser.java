package com.jwt.auth.C_Interface_Adapters.Controllers.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
/**
 * @Autor: This class returns this object when creating a users
 */
public class SaveUser implements Serializable {
    @Size(min = 6)
    private String name;
    @Size(min = 6)
    private String username;
    @Size(min = 8)
    private String password;
    @Size(min = 8)
    private String repeatedPassword;
}
