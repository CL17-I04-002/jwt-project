package com.jwt.auth.B_Use_Cases.Implementations;

import com.jwt.auth.A_Domain.Users;
import com.jwt.auth.A_Domain.util.Role;
import com.jwt.auth.B_Use_Cases.Exception.InvalidPasswordException;
import com.jwt.auth.B_Use_Cases.Exception.ObjectNotFoundException;
import com.jwt.auth.B_Use_Cases.Interfaces.UserService;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.SaveUser;
import com.jwt.auth.D_Infraestructure.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final String userNotFound;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository,
                          @Value("${user.not.found}") String userNotFound,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userNotFound = userNotFound;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users createUser(Users users) {
        passwordEncoder.encode(users.getPassword());
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
        userFound.setUsername(users.getUsername());
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

    /**
     * Creates new Users
     * @param newUser
     * @return Users
     */
    @Override
    public Users registerOneCustomer(SaveUser newUser) {
        
        validatePassword(newUser);
        
        Users users = Users.builder()
                .password(passwordEncoder.encode(newUser.getPassword()))
                .username(newUser.getUsername())
                .name(newUser.getName())
                .role(Role.ROLE_CUSTOMER)
                .build();
        return userRepository.save(users);
    }

    /**
     * Validates password and repeatPassword
     * @param dto
     */
    private void validatePassword(SaveUser dto) {
        if(!StringUtils.hasText(dto.getPassword()) || !StringUtils.hasText(dto.getRepeatedPassword())){
            throw new InvalidPasswordException("Password don't match");
        }
        if(!dto.getPassword().equals(dto.getRepeatedPassword())){
            throw new InvalidPasswordException("Password don't match");
        }
    }
}