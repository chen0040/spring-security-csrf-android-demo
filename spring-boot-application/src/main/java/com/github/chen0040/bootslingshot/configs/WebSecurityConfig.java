package com.github.chen0040.bootslingshot.configs;

import com.github.chen0040.bootslingshot.components.SpringAuthenticationSuccessHandler;
import com.github.chen0040.bootslingshot.services.SpringUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


/**
 * Created by xschen on 15/10/2016.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private SpringUserDetailService userDetailService;


   @Autowired
   private SpringAuthenticationSuccessHandler authenticationSuccessHandler;


   @Override
   protected void configure(HttpSecurity http) throws Exception {




      http
              .authorizeRequests()

              .antMatchers("/js/client/**").hasAnyRole("USER", "ADMIN")
              .antMatchers("/js/admin/**").hasAnyRole("ADMIN")
              .antMatchers("/html/**").hasAnyRole("USER", "ADMIN")
              .antMatchers("/erp/login-api-json").permitAll()
              .antMatchers("/css/**").permitAll()
              .antMatchers("/jslib/**").permitAll()
              .antMatchers("/webjars/**").permitAll()
              .antMatchers("/bundle/**").permitAll()
              .antMatchers("/locales").permitAll()
              .antMatchers("/locales/**").permitAll()
              .anyRequest().authenticated()
              .and()
              .formLogin()
              .loginPage("/login")
              .defaultSuccessUrl("/home")
              .successHandler(authenticationSuccessHandler)
              .permitAll()
              .and()
              .logout()
              .permitAll()
              .and()
              .csrf()
              .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
   }

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {


      auth.userDetailsService(userDetailService)
              .passwordEncoder(new BCryptPasswordEncoder());
   }
}
