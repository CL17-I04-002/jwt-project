package com.jwt.auth.D_Infraestructure.security;

import com.jwt.auth.D_Infraestructure.security.filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
/**
 * @Autor: Daniel Larin
 */
public class HttpSecurityConfig {

    private final AuthenticationProvider authProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthorizationManager<RequestAuthorizationContext> authorizationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authReqConfig -> {
                    // We're only add public paths
                    /*authReqConfig.requestMatchers(HttpMethod.POST,"/customers").permitAll();
                    authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
                    authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate").permitAll();
                    authReqConfig.anyRequest().authenticated();*/
                    authReqConfig.anyRequest().access(authorizationManager);
                })
                .build();
    }
}
