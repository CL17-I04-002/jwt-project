package com.jwt.auth.B_Use_Cases.Interfaces;

import com.jwt.auth.A_Domain.Users;

import java.util.List;

public interface UserService {
    Users createUser(Users users);
    List<Users> findAllUsers();
    Users findUserById(Long id);
    Users updateUser(Long id, Users users);
    void deleteUser(Long id);
}
