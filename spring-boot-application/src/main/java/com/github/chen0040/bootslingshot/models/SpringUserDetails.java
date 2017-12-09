package com.github.chen0040.bootslingshot.models;


import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;


/**
 * Created by xschen on 11/12/2016.
 */
public class SpringUserDetails extends User {

   private SpringUser user;

   public SpringUserDetails(SpringUser user, String roles) {
      super(user.getUsername(), user.getPassword(), AuthorityUtils
              .commaSeparatedStringToAuthorityList(roles));

      this.user = user;
   }





   public long getUserId() { return user.getId(); }


   public String getEmail() {
      return user.getEmail();
   }

   public boolean isSuperUser() { return user.isSuperUser(); }


   public SpringUser getUser() {
      return user;
   }



}
