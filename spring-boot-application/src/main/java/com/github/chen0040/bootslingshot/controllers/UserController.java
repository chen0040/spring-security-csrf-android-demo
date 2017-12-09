package com.github.chen0040.bootslingshot.controllers;

import com.github.chen0040.bootslingshot.components.SpringAuthentication;
import com.github.chen0040.bootslingshot.models.SpringUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private SpringAuthentication springAuthentication;

    @RequestMapping(value = "/users/get-account", method = RequestMethod.GET)
    public @ResponseBody
    SpringUser getAccount() {
        return springAuthentication.getUser();
    }

}
