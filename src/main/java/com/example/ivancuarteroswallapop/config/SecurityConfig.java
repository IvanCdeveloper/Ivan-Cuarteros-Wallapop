package com.example.ivancuarteroswallapop.config;

import com.example.ivancuarteroswallapop.entity.Usuario;
import com.example.ivancuarteroswallapop.repository.UsuarioRepository;
import com.example.ivancuarteroswallapop.service.UserDetailsServiceImpl;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {




    private final UserDetailsServiceImpl userDetailsService; // Repositorio para crear UserDetailsServiceImpl

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http

        .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/anuncios", "/login" , "/register", "/css/**", "/js/**", "/imagesAnuncios/**", "/anuncio-list/**").permitAll()
                .requestMatchers("/usuarios/*").authenticated()
                .anyRequest().permitAll()
        )
                .httpBasic(Customizer.withDefaults())
                .formLogin(form ->
                        form.loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error"))
                .rememberMe((remember) -> remember
                                .key("uniqueAndSecret")
                                .tokenValiditySeconds(5 * 24 * 60 * 60)
                        )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                        );



        return http.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }







    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }



}
