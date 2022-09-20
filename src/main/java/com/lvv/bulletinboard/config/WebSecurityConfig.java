package com.lvv.bulletinboard.config;

import com.lvv.bulletinboard.model.Role;
import com.lvv.bulletinboard.model.User;
import com.lvv.bulletinboard.repositiry.UserRepository;
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
//                .antMatchers(HttpMethod.POST,"/api/admin").anonymous()
                .antMatchers(HttpMethod.POST,"/api/users").anonymous()
//                .antMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
//                .antMatchers("/api/profile/**").hasRole(Role.USER.name())
//                .antMatchers(HttpMethod.POST, "/api/restaurants/**").hasRole(Role.ADMIN.name())
//                .antMatchers(HttpMethod.PUT, "/api/restaurants/**").hasRole(Role.ADMIN.name())
//                .antMatchers(HttpMethod.DELETE, "/api/restaurants/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/api/users/**").authenticated()
//                .antMatchers("/api/voting").hasRole(Role.USER.name())
//                .antMatchers("/api/profile").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
//                .antMatchers("/api/**").anonymous()//.authenticated()
                .anyRequest()
                .permitAll()
                .and().httpBasic()
                .and().build();
    }


}
