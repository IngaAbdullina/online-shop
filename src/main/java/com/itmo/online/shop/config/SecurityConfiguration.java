package com.itmo.online.shop.config;

import com.itmo.online.shop.config.authentication.ShopAuthenticationManager;
import com.itmo.online.shop.service.impl.UserSecurityService;
import com.itmo.online.shop.util.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserSecurityService userSecurityService;

  private BCryptPasswordEncoder passwordEncoder() {
    return SecurityUtility.passwordEncoder();
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

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers(PUBLIC_MATCHERS).permitAll()
        .antMatchers("/article/**").hasRole("ADMIN")
        .anyRequest().authenticated();

    http
        .csrf().disable().cors().disable()
        .formLogin().failureUrl("/login?error")
        .loginPage("/login").permitAll()
        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
        .and()
        .rememberMe().key("aSecretKey");
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
  }
}
