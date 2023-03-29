package com.itmo.online.shop.config;

import com.itmo.online.shop.security.UserSecurityService;
import com.itmo.online.shop.util.SecurityUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

  private final UserSecurityService userSecurityService;

  public SecurityConfiguration(UserSecurityService userSecurityService) {
    this.userSecurityService = userSecurityService;
  }

  private static final String[] PUBLIC_MATCHERS = {
      "/css/**",
      "/js/**",
      "/image/**",
      "/",
      "/new-user",
      "/login",
      "/store",
      "/article-detail"
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors().disable()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers(PUBLIC_MATCHERS).permitAll()
        .antMatchers("/article/**").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login").permitAll()
        .failureUrl("/login?error")
        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
        .and()
        .rememberMe().key("aSecretKey");
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userSecurityService)
        .passwordEncoder(passwordEncoder())
        .and()
        .build();
  }

  private BCryptPasswordEncoder passwordEncoder() {
    return SecurityUtility.passwordEncoder();
  }
}