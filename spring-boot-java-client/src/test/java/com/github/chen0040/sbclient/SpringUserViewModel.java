package com.github.chen0040.sbclient;


import java.util.Date;


/**
 * Created by xschen on 16/10/2016.
 */
public class SpringUserViewModel {

   private long id;

   private long companyId = -1;

   private String username = "";
   private String password = "";

   private String email = "";
   private String roles = "ROLE_USER";

   private String firstName = "";
   private String lastName = "";


   private long createdBy;

   private long lastUpdatedBy;

   private Date createdTime;

   private Date updatedTime;

   private boolean enabled = true;

   private String token = "";


   public SpringUserViewModel() {

   }


   public boolean isSuperUser() {
      return roles.contains("ROLE_ADMIN");
   }

   public boolean isValid(){
      return !StringUtils.isEmpty(username);
   }

   public void setCreatedTime(Date createdTime) {
      if(createdTime != null) {
         this.createdTime = new Date(createdTime.getTime());
      } else {
         this.createdTime = null;
      }
   }


   public Date getCreatedTime() {
      if(createdTime != null){
         return new Date(createdTime.getTime());
      }
      return null;
   }



   public void setUpdatedTime(Date updatedTime) {
      if(updatedTime != null) {
         this.updatedTime = new Date(updatedTime.getTime());
      } else {
         this.updatedTime =null;
      }
   }


   public Date getUpdatedTime() {
      if(updatedTime != null){
         return new Date(updatedTime.getTime());
      }
      return null;
   }


   public long getId() {
      return id;
   }


   public void setId(long id) {
      this.id = id;
   }


   public String getUsername() {
      return username;
   }


   public void setUsername(String username) {
      this.username = username;
   }


   public String getPassword() {
      return password;
   }


   public void setPassword(String password) {
      this.password = password;
   }


   public String getEmail() {
      return email;
   }


   public void setEmail(String email) {
      this.email = email;
   }


   public String getRoles() {
      return roles;
   }


   public void setRoles(String roles) {
      this.roles = roles;
   }


   public String getFirstName() {
      return firstName;
   }


   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }


   public String getLastName() {
      return lastName;
   }


   public void setLastName(String lastName) {
      this.lastName = lastName;
   }


   public long getCreatedBy() {
      return createdBy;
   }


   public void setCreatedBy(long createdBy) {
      this.createdBy = createdBy;
   }


   public long getLastUpdatedBy() {
      return lastUpdatedBy;
   }


   public void setLastUpdatedBy(long lastUpdatedBy) {
      this.lastUpdatedBy = lastUpdatedBy;
   }

   public void setEnabled(boolean enabled) {this.enabled = enabled;}

   public boolean isEnabled() { return enabled; }

   public String getToken() { return token; }

   public void setToken(String token) { this.token = token; }
}
