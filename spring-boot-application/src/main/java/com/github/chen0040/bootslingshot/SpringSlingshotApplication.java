package com.github.chen0040.bootslingshot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Created by xschen on 13/9/2017.
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSlingshotApplication {
   public static void main(String[] args) {
      SpringApplication.run(SpringSlingshotApplication.class, args);
   }
}
