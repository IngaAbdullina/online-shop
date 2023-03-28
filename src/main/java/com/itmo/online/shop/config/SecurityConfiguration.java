package com.itmo.online.shop.config;

import com.itmo.online.shop.config.authentication.ShopAuthenticationManager;
import com.itmo.online.shop.util.SecurityUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

  private final ShopAuthenticationManager authManager;

  public SecurityConfiguration(ShopAuthenticationManager authManager) {
    this.authManager = authManager;
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
        .formLogin().failureUrl("/login?error")
        .loginPage("/login").permitAll()
        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
        .and()
        .rememberMe().key("aSecretKey")
//				.and()
//				.exceptionHandling().accessDeniedHandler(accessDeniedHandler())	// todo
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .parentAuthenticationManager(authManager)
        .build();
  }

//  @Bean
//  public AccessDeniedHandler accessDeniedHandler() {
//    return new T4FactorAccessDeniedHandler();
//  }

  // https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
  private BCryptPasswordEncoder passwordEncoder() {
    return SecurityUtility.passwordEncoder();
  }
}