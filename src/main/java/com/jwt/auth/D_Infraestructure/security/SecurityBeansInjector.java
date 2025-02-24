package com.jwt.auth.D_Infraestructure.security;

import com.jwt.auth.B_Use_Cases.Exception.ObjectNotFoundException;
import com.jwt.auth.D_Infraestructure.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Autor: Daniel Larin
 */
@Configuration
public class SecurityBeansInjector {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;
    private final String userNotFound;

    public SecurityBeansInjector(AuthenticationConfiguration authenticationConfiguration,
                                 UserRepository userRepository, @Value("${user.not.found}") String userNotFound) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.userRepository = userRepository;
        this.userNotFound = userNotFound;
    }

    /**
     * It handles authentication
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return this.authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * It defines concrete strategy, in this case for credentials
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        // Most common implementation
        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
        authenticationStrategy.setPasswordEncoder(passwordEncoder());
        authenticationStrategy.setUserDetailsService(userDetailsService());
        return authenticationStrategy;
    }

    /**
     * Manages Password
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Manages UserDetails
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new ObjectNotFoundException(userNotFound + " " + username));
        };
    }
}
