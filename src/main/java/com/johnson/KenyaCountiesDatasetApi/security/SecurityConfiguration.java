package com.johnson.KenyaCountiesDatasetApi.security;

import com.johnson.KenyaCountiesDatasetApi.filters.JwtRequestFilter;
import com.johnson.KenyaCountiesDatasetApi.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/swagger-ui/**", "/api-docs/**").permitAll()

                .antMatchers("/users").hasRole("ADMIN")
                .antMatchers("/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/counties").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/counties/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/counties").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/counties/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/counties").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/counties/**").hasAnyRole("ADMIN", "USER")

                .antMatchers(HttpMethod.POST, "/constituencies").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/constituencies/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/constituencies").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/constituencies/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/constituencies").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/constituencies/**").hasAnyRole("ADMIN", "USER")

                .antMatchers(HttpMethod.POST, "/wards").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/wards/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/wards").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/wards/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/wards").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/wards/**").hasAnyRole("ADMIN", "USER")

                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
   }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
