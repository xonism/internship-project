package com.twoday.internshipwarehouse.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        AntPathRequestMatcher[] publicEndpoints = new AntPathRequestMatcher[]{
                new AntPathRequestMatcher("/login"),
                new AntPathRequestMatcher("/status")
        };

        httpSecurity.authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(publicEndpoints).permitAll()
                                .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .logout(withDefaults())
                .formLogin(withDefaults())
                .httpBasic(withDefaults());

        return httpSecurity.build();
    }
}
