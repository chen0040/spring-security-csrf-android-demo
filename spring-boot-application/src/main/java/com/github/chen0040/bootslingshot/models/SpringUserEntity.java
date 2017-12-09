package com.github.chen0040.bootslingshot.models;


import com.github.chen0040.bootslingshot.utils.StringUtils;

import javax.persistence.*;
import java.util.Date;


/**
 * Created by xschen on 16/10/2016.
 */
@Entity
@Table(name = "spring_users", indexes = {
        @Index(name="usernameIndex", columnList = "username", unique = true),
        @Index(name="userTokenIndex", columnList = "token", unique = false),
        @Index(name="userEmailIndex", columnList = "email", unique = false)
})
public class SpringUserEntity implements SpringUser {
   @Id
   @GeneratedValue
   private long id;

   private long companyId = -1;

   private String username = "";
   private String password = "";

   private String email = "";
   private String roles = "ROLE_USER";

   private String firstName = "";
   private String lastName = "";

   private boolean enabled = true;

   private long createdBy;

   private long lastUpdatedBy;

   @Temporal(TemporalType.TIMESTAMP)
   private Date createdTime;

   @Temporal(TemporalType.TIMESTAMP)
   private Date updatedTime;

   private String token = "";

   public SpringUserEntity(SpringUser user) {
      copy(user);
   }

   public SpringUserEntity() {

   }


   @Override public boolean isSuperUser() {
      return roles.contains("ROLE_ADMIN");
   }

   @Override public boolean isValid(){
      return !StringUtils.isEmpty(username);
   }

   @Override public void setCreatedTime(Date createdTime) {
      if(createdTime != null) {
         this.createdTime = new Date(createdTime.getTime());
      } else {
         this.createdTime = null;
      }
   }


   @Override public Date getCreatedTime() {
      if(createdTime != null){
         return new Date(createdTime.getTime());
      }
      return null;
   }



   @Override public void setUpdatedTime(Date updatedTime) {
      if(updatedTime != null) {
         this.updatedTime = new Date(updatedTime.getTime());
      } else {
         this.updatedTime =null;
      }
   }


   @Override public Date getUpdatedTime() {
      if(updatedTime != null){
         return new Date(updatedTime.getTime());
      }
      return null;
   }


   @Override public long getId() {
      return id;
   }


   @Override public void setId(long id) {
      this.id = id;
   }


   @Override public String getUsername() {
      return username;
   }


   @Override public void setUsername(String username) {
      this.username = username;
   }


   @Override public String getPassword() {
      return password;
   }


   @Override public void setPassword(String password) {
      this.password = password;
   }


   @Override public String getEmail() {
      return email;
   }


   @Override public void setEmail(String email) {
      this.email = email;
   }


   @Override public String getRoles() {
      return roles;
   }


   @Override public void setRoles(String roles) {
      this.roles = roles;
   }


   @Override public String getFirstName() {
      return firstName;
   }


   @Override public void setFirstName(String firstName) {
      this.firstName = firstName;
   }


   @Override public String getLastName() {
      return lastName;
   }


   @Override public void setLastName(String lastName) {
      this.lastName = lastName;
   }


   @Override public long getCreatedBy() {
      return createdBy;
   }


   @Override public void setCreatedBy(long createdBy) {
      this.createdBy = createdBy;
   }


   @Override public long getLastUpdatedBy() {
      return lastUpdatedBy;
   }


   @Override public void setLastUpdatedBy(long lastUpdatedBy) {
      this.lastUpdatedBy = lastUpdatedBy;
   }


   @Override public void setEnabled(boolean enabled) { this.enabled = enabled; }

   @Override public boolean isEnabled() { return enabled; }

   @Override public void setToken(String token) { this.token = token; }

   @Override public String getToken()  { return this.token; }

}
