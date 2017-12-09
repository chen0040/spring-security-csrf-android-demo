package com.github.chen0040.bootslingshot.components;

import com.github.chen0040.bootslingshot.models.SpringUser;
import com.github.chen0040.bootslingshot.models.SpringUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;


/**
 * Created by xschen on 21/12/2016.
 */
@Component
public class SpringAuthentication {

   private static final Logger logger = LoggerFactory.getLogger(SpringAuthentication.class);

   public boolean hasRole(Authentication authentication, String role) {
      Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
      for(GrantedAuthority authority : authorities) {
         String authorityRole = authority.getAuthority();
         if(authorityRole.equals(role)){
            return true;
         }
      }
      return false;
   }

   public SpringUserDetails getCurrentUser() {
      return (SpringUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   }

   public String getUsername(){
      SpringUserDetails user = getCurrentUser();
      return user.getUsername();
   }


   public long getUserId(){
      SpringUserDetails user = getCurrentUser();
      return user.getUserId();
   }


   public boolean isSuperUser() {
      return getCurrentUser().isSuperUser();
   }


   public SpringUser getUser() {
      return getCurrentUser().getUser();
   }

   public boolean hasRole(String role) {
      Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
      for(GrantedAuthority authority : authorities) {
         String authorityRole = authority.getAuthority();
         logger.info("role: {}", authorityRole);
         if(authorityRole.equals(role)){
            return true;
         }
      }
      return false;
   }

   public boolean isLoggedIn(){
      return SecurityContextHolder.getContext().getAuthentication() != null &&
              SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
              //when Anonymous Authentication is enabled
              !(SecurityContextHolder.getContext().getAuthentication()
                      instanceof AnonymousAuthenticationToken);
   }



}
