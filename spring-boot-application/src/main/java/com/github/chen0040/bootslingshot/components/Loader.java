package com.github.chen0040.bootslingshot.components;


import com.github.chen0040.bootslingshot.models.SpringUser;
import com.github.chen0040.bootslingshot.models.SpringUserEntity;
import com.github.chen0040.bootslingshot.services.SpringUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;


/**
 * Created by chen0 on 2/6/2016.
 */
@Component
public class Loader implements ApplicationListener<ApplicationReadyEvent> {
   private static final Logger logger = LoggerFactory.getLogger(Loader.class);


   @Override public void onApplicationEvent(ApplicationReadyEvent e) {
      logger.info("Loader triggered at {}", e.getTimestamp());

      ApplicationContext context = e.getApplicationContext();
      logger.info("Run loader...");


      SpringUserService userService = context.getBean(SpringUserService.class);

      logger.info("Run users...");
      setupUsers(userService);
      setupAdmin(userService);

   }


   private Random random = new Random(new Date().getTime());

   private void setupAdmin(SpringUserService userService) {

      String username = "admin";
      logger.info("Setup {}", username);

      SpringUser admin;
      Optional<SpringUser> userOptional = userService.findUserByUsername(username);
      if(userOptional.isPresent()){
         admin = userOptional.get();
         String roles = admin.getRoles();
         if(!roles.contains("ROLE_ADMIN") || !roles.contains("ROLE_USER")) {
            admin.setRoles("ROLE_USER,ROLE_ADMIN");
         }
      } else {
         admin = new SpringUserEntity();
         admin.setUsername("admin");
         admin.setRoles("ROLE_USER,ROLE_ADMIN");
         admin.setPassword("admin");
         admin.setEmail("xs0040@gmail.com");
         admin.setToken(UUID.randomUUID().toString());
         admin.setEnabled(true);
      }

      userService.save(admin);
   }

   private void setupUsers(SpringUserService userService){
      String companyName = "demo";
      logger.info("Setup {}", companyName);



      String username = "demo" ;
      logger.info("Setup {}", username);

      SpringUser user;
      Optional<SpringUser> userOptional = userService.findUserByUsername(username);
      if(userOptional.isPresent()) {
         user = userOptional.get();
      } else {
         user = new SpringUserEntity();
         user.setEmail("xs0040@gmail.com");

         user.setUsername(username);
         user.setPassword(username);
         user.setToken(UUID.randomUUID().toString());
         user.setEnabled(true);
      }

      user.setRoles("ROLE_USER");
      userService.save(user);
   }


}
