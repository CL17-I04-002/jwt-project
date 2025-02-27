package com.jwt.auth.B_Use_Cases.Interfaces;

import com.jwt.auth.A_Domain.Users;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.SaveUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Users createUser(Users users);
    List<Users> findAllUsers();
    Users findUserById(Long id);
    Users updateUser(Long id, Users users);
    void deleteUser(Long id);

    Users registerOneCustomer(SaveUser newUser);

    Optional<Users> findOneByUsername(String username);
}
