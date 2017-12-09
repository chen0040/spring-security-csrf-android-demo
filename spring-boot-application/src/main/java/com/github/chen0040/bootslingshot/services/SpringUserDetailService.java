package com.github.chen0040.bootslingshot.services;

import com.github.chen0040.bootslingshot.models.SpringUser;
import com.github.chen0040.bootslingshot.models.SpringUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Created by xschen on 16/10/2016.
 */
@Service
public class SpringUserDetailService implements UserDetailsService {

   private static final Logger logger = LoggerFactory.getLogger(SpringUserDetailService.class);

   @Autowired
   private SpringUserService springUserService;

   @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<SpringUser> userOptional = springUserService.findUserByUsername(username);

      logger.info("loading user with username = {}", username);

      if(!userOptional.isPresent()){
         logger.warn("System does not find a user with username: {}", username);
         return null;
      }

      SpringUser user = userOptional.get();

      String roles = getRoles(user);

      return new SpringUserDetails(user, roles);
   }

   private String getRoles(SpringUser user) {
      if(user.getUsername().equalsIgnoreCase("admin")) {
         return user.getRoles() + ",ROLE_ROOT";
      }

      if(user.isEnabled()){
         return user.getRoles();
      }
      else {
         return user.getRoles() + ",ROLE_DISABLED";
      }
   }
}
