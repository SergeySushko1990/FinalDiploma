package com.example.finalproject.Security;

import com.example.finalproject.Services.UsersServices;
import io.netty.util.internal.NoOpTypeParameterMatcher;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UsersServices usersServices;


    @Override
    protected void configure(HttpSecurity http) throws Exception{
            http.httpBasic()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/registration").permitAll()
                    .antMatchers("/users", "/friends").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/admin").hasRole("ADMIN")
                    .and()
                    .csrf().disable()
                    .formLogin().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
            auth.userDetailsService(usersServices)
                    .passwordEncoder(getPasswordEncoder());
        }

        @Bean
    public PasswordEncoder getPasswordEncoder(){
            return new BCryptPasswordEncoder();
        }
}
