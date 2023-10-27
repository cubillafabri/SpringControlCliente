package com.cubito.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Value("${auth.role.basic}")
    String auth_role_basic;

    @Value("${auth.role.more}")
    String auth_role_more;


    
    @Autowired
    private UserDetailsService userDetailsService;
    
    /*@Autowired // Arg de manera automatica... Injectar de manera auto spring busca la implementacion..
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }*/
    
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrfCustomizer->csrfCustomizer.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/").hasAnyRole(auth_role_basic, auth_role_more) //login necesario para ver inicio...
                .requestMatchers("/editar/**", "/agregar/**", "/eliminar","/guardar/**").hasRole(auth_role_more)
                                        )
                .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .permitAll()
                        )
                //.formLogin(Customizer.withDefaults())
                //.logout(Customizer.withDefaults())
                .logout(logout -> logout.logoutUrl("/logout")
                .permitAll()
                       )
                //.exceptionHandling(excepH -> excepH.accessDeniedPage("/errores/403"))
                .build();

    }

    // Create 2 user + pass plain text

    /*@Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername(auth_admin).password(passwordEncoder().encode(auth_admin_pass)).roles(auth_role_more,auth_role_basic).build());
        manager.createUser(User.withUsername(auth_user).password(passwordEncoder().encode(auth_user_pass)).roles(auth_role_basic).build());
        return manager;
    }*/
    // PassEncoder

    @Bean // Tipo de encripcion what is a bean? permite a spring a usar de forma automatica.
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
