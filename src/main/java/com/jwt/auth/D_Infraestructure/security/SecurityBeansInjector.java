package com.jwt.auth.D_Infraestructure.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@AllArgsConstructor
public class SecurityBeansInjector {

    private final AuthenticationConfiguration authenticationConfiguration;

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
        authenticationStrategy.setPasswordEncoder(null);
        authenticationStrategy.setUserDetailsService(null);
        return authenticationStrategy;
    }
}
