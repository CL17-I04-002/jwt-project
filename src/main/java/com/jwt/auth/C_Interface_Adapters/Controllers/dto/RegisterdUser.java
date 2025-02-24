package com.jwt.auth.C_Interface_Adapters.Controllers.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
/**
 * @Autor: Daniel Larín
 * This class sends basic information in each request, it doesn't necessary sends Users object
 */
public class RegisterdUser implements Serializable {
    private Long id;
    private String username;
    private String name;
    private String role;
    private String jwt;
}
