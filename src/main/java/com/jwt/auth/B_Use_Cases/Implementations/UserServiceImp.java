package com.jwt.auth.B_Use_Cases.Implementations;

import com.jwt.auth.A_Domain.security.Role;
import com.jwt.auth.A_Domain.security.Users;
import com.jwt.auth.A_Domain.util.RoleEnum;
import com.jwt.auth.B_Use_Cases.Exception.InvalidPasswordException;
import com.jwt.auth.B_Use_Cases.Exception.ObjectNotFoundException;
import com.jwt.auth.B_Use_Cases.Interfaces.RoleService;
import com.jwt.auth.B_Use_Cases.Interfaces.UserService;
import com.jwt.auth.C_Interface_Adapters.Controllers.dto.SaveUser;
import com.jwt.auth.D_Infraestructure.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final String userNotFound;
    private final String roleNotFound;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImp(UserRepository userRepository,
                          @Value("${user.not.found}") String userNotFound,
                          PasswordEncoder passwordEncoder, @Value("${role.not.found}") String roleNotFound,
                          RoleService roleService) {
        this.userRepository = userRepository;
        this.userNotFound = userNotFound;
        this.passwordEncoder = passwordEncoder;
        this.roleNotFound = roleNotFound;
        this.roleService = roleService;
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
        
        Users users = new Users();
                users.setPassword(passwordEncoder.encode(newUser.getPassword()));
                users.setUsername(newUser.getUsername());
                users.setName(newUser.getName());
                Role defaultRole = roleService.findDefaultRole()
                        .orElseThrow(() -> new ObjectNotFoundException(roleNotFound));
                users.setRole(defaultRole);

        return userRepository.save(users);
    }

    @Override
    public Optional<Users> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
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