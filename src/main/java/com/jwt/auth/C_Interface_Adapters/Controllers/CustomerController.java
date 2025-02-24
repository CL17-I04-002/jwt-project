package com.jwt.auth.C_Interface_Adapters.Controllers;

import com.jwt.auth.B_Use_Cases.Interfaces.AuthenticationService;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.RegisterdUser;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.SaveUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    private final AuthenticationService authenticationService;

    /**
     * This endpoint will register one user
     * @param newUser
     * @return
     */

    @PostMapping
    public ResponseEntity<RegisterdUser> registerOne(@RequestBody  @Valid SaveUser newUser){
        RegisterdUser registerdUser = authenticationService.registerOneCustomer(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerdUser);
    }
}
