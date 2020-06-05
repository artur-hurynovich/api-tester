package com.hurynovich.api_tester.security.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_ANT_PATTERNS = {"/api/authentication/registration",
            "/api/authentication/login"};

    private final SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> jwtConfigurer;

    public SecurityConfiguration(final @NonNull @Qualifier("jwtConfigurer") SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final @NonNull HttpSecurity http) throws Exception {
        http.
                httpBasic().disable().
                csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().
                authorizeRequests().
                antMatchers(PUBLIC_ANT_PATTERNS).permitAll().
                anyRequest().authenticated().
                and().
                apply(jwtConfigurer);
    }

}
