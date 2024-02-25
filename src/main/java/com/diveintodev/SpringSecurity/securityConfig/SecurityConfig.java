package com.diveintodev.SpringSecurity.securityConfig;

import com.diveintodev.SpringSecurity.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    /** boot 3.1**/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

       return http.csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(auth -> auth.requestMatchers("/user/add").
                       permitAll().requestMatchers("/user/getUser/**").authenticated())
               .httpBasic(Customizer.withDefaults()).build();

   }

    /** till boot 3.0**/
   /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/add").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/user/getUser/**").fullyAuthenticated()
                .and().formLogin()
                .and().build();
    }
   */
    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }


}
