package com.jobs.layers.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }
    private final String[] WHITE_LIST_URL = {
            "/employee/register"
    };



@Bean
    static SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {



http

            .authorizeRequests().requestMatchers("/**").permitAll();

    //http.csrf(ServerHttpSecurity.CsrfSpec::disable);


    http.csrf((csrf) -> csrf.disable());

    return http.build();
    }


}
