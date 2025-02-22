package com.jwt.auth.C_Interface_Adapters.Controllers;

import com.jwt.auth.A_Domain.Users;
import com.jwt.auth.B_Use_Cases.Interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable(required = false) Long id){
        if(Objects.isNull(id)) return ResponseEntity.ok(userService.findAllUsers());
        else return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody Users users){
        userService.createUser(users);
        return ResponseEntity.status(HttpStatus.CREATED).body("User was created");
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable() Long id, @RequestBody Users users){
        userService.updateUser(id, users);
        return ResponseEntity.status(HttpStatus.CREATED).body("User was updated");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable() Long id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User was removed");
    }
}
