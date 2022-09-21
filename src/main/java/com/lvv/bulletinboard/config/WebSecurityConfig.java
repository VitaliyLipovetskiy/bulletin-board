package com.lvv.bulletinboard.config;

import com.lvv.bulletinboard.model.User;
import com.lvv.bulletinboard.repository.UserRepository;
import com.lvv.bulletinboard.web.AuthorizedUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

/**
 * @author Vitalii Lypovetskyi
 */
@Configuration
@Slf4j
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            log.debug("Authenticating '{}'", email);
            Optional<User> optionalUser = userRepository.getByEmail(email);
            return new AuthorizedUser(optionalUser.orElseThrow(
                    () -> new UsernameNotFoundException("User '" + email + "' was not found")));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/users").anonymous()
                .antMatchers(HttpMethod.GET, "/api/users/**").authenticated()
                .antMatchers("/api/ads/**").authenticated()
                .antMatchers("/api/board/**").authenticated()
                .antMatchers("/swagger-ui.html").anonymous()//.authenticated()
                .antMatchers("/swagger-resources/**").anonymous()//.authenticated()
                .anyRequest()
                .permitAll()
                .and().httpBasic()
                .and().build();
    }
}
