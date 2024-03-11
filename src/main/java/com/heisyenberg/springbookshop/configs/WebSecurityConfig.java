package com.heisyenberg.springbookshop.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService,
                             PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers("/addBook", "/editBook/*", "/deleteBook/*")
                .hasRole("ADMIN")
                .antMatchers("/", "/books", "/book/*", "/search", "/error",
                        "/css/*", "/resources/**", "/images/**", "/logo.png")
                .permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .formLogin().loginPage("/login").usernameParameter("email")
                .defaultSuccessUrl("/books").permitAll()
                .and().exceptionHandling().accessDeniedPage("/access-denied")
                .and().logout().permitAll().logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
