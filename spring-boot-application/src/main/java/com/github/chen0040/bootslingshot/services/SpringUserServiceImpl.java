package com.github.chen0040.bootslingshot.services;


import com.github.chen0040.bootslingshot.models.SpringUser;
import com.github.chen0040.bootslingshot.models.SpringUserEntity;
import com.github.chen0040.bootslingshot.repositories.SpringUserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Created by xschen on 16/10/2016.
 * Service that provides the handling for user and subscription
 */
@Service
public class SpringUserServiceImpl implements SpringUserService {

   @Autowired SpringUserEntityRepository userEntityRepository;

   @Override public boolean isUserAuthenticated(String username, String password) {

      username = username.toLowerCase();

      Optional<SpringUser> userOptional = findUserByUsername(username);
      if(userOptional.isPresent()){
         PasswordEncoder encoder = createPasswordEncoder();
         return encoder.matches(password, userOptional.get().getPassword());
      }
      return false;
   }


   @Override public Optional<SpringUser> findUserByUsername(String name) {
      name = name.toLowerCase();

      List<SpringUserEntity> users = userEntityRepository.findByUsername(name);
      if(users.isEmpty()) {
         return Optional.empty();
      }
      return Optional.of(users.get(0));
   }



   @Override public List<SpringUser> findUserByEmail(String email) {
      email = email.toLowerCase();
      return userEntityRepository.findByEmail(email).stream().collect(Collectors.toList());
   }


   @Override public Optional<SpringUser> findUserByToken(String token) {
      SpringUserEntity user = userEntityRepository.findFirstByToken(token);
      if(user == null) {
         return Optional.empty();
      } else {
         return Optional.of(user);
      }
   }





   @Transactional
   @Override public SpringUser save(SpringUser user) {
      String username = user.getUsername().toLowerCase();
      user.setUsername(username);

      Optional<SpringUser> userOptional = findUserByUsername(user.getUsername());
      if(userOptional.isPresent()){
         SpringUser existingUser = userOptional.get();
         String oldPassword = existingUser.getPassword();
         existingUser.copyProfile(user);

         if(!oldPassword.equals(existingUser.getPassword())) {
            existingUser.setPassword(encryptPassword(existingUser.getPassword()));
         }
         return userEntityRepository.save(entity(existingUser));
      } else {
         user.setCreatedTime(new Date());
         user.setPassword(encryptPassword(user.getPassword()));
         return userEntityRepository.save(entity(user));
      }
   }

   private SpringUserEntity entity(SpringUser user) {
      return new SpringUserEntity(user);
   }




   public static String encryptPassword(String rawPassword) {

      PasswordEncoder passwordEncoder = createPasswordEncoder();
      return passwordEncoder.encode(rawPassword);

   }


   @Override public List<SpringUser> findAll() {
      List<SpringUser> result = new ArrayList<>();
      for(SpringUser user : userEntityRepository.findAll()){
         result.add(user);
      }
      return result;
   }


   @Override public Page<SpringUserEntity> findByPage(int pageIndex, int pageSize) {
      return userEntityRepository.findAll(new PageRequest(pageIndex, pageSize));
   }


   @Override public Page<SpringUserEntity> findByPage(int pageIndex, int pageSize, Sort sort) {
      return userEntityRepository.findAll(new PageRequest(pageIndex, pageSize, sort));
   }


   @Override public long countUsers() {
      return userEntityRepository.count();
   }


   @Override public long countPages(int pageSize) {
      return (long)Math.ceil((double)countUsers() / pageSize);
   }


   @Override public Optional<SpringUser> findUserById(long userId) {
      SpringUser user = userEntityRepository.findOne(userId);
      if(user == null) {
         return Optional.empty();
      } else {
         return Optional.of(user);
      }
   }


   @Override public SpringUser deleteUserById(long userId) {
      SpringUser user = userEntityRepository.findOne(userId);
      userEntityRepository.delete(userId);
      return user;
   }




   static PasswordEncoder createPasswordEncoder() {
      return new BCryptPasswordEncoder();
   }


}
