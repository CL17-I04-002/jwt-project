package com.jwt.auth.B_Use_Cases.Implementations;

import com.jwt.auth.A_Domain.Users;
import com.jwt.auth.B_Use_Cases.Exception.ObjectNotFoundException;
import com.jwt.auth.B_Use_Cases.Interfaces.UserService;
import com.jwt.auth.D_Infraestructure.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final String userNotFound;

    public UserServiceImp(UserRepository userRepository, @Value("${user.not.found}") String userNotFound) {
        this.userRepository = userRepository;
        this.userNotFound = userNotFound;
    }

    @Override
    public Users createUser(Users users) {
        return userRepository.save(users);
    }

    @Override
    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(userNotFound));
    }

    @Override
    public Users updateUser(Long id, Users users) {
        Users userFound = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(userNotFound));
        userFound.setName(users.getName());
        userFound.setNickname(users.getNickname());
        userFound.setPassword(users.getPassword());
        userFound.setEmail(users.getEmail());
        userFound.setEnabled(users.getEnabled());
        return userRepository.save(userFound);
    }

    @Override
    public void deleteUser(Long id) {
        Users userFound =  userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(userNotFound));
        userRepository.delete(userFound);
    }
}