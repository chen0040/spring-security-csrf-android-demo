package com.github.chen0040.bootslingshot.controllers;

import com.github.chen0040.bootslingshot.components.SpringRequestHelper;
import com.github.chen0040.bootslingshot.models.LoginObj;
import com.github.chen0040.bootslingshot.models.SpringIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class WebApiController {

    @Autowired
    private SpringRequestHelper requestHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value="/erp/login-api-json", method = RequestMethod.GET)
    public Map<String, String> getLoginApiJson(HttpServletRequest request) {
        return requestHelper.getTokenInfo(request);
    }

    @RequestMapping(value="/erp/login-api-json", method = RequestMethod.POST)
    public SpringIdentity postLoginApiJson(@RequestBody LoginObj loginObj, HttpServletRequest request) {

        String username = loginObj.getUsername();
        String password = loginObj.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if does not exists
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        SpringIdentity identity = new SpringIdentity();
        identity.setAuthenticated(authentication.isAuthenticated());
        identity.setUsername(username);

        return identity;
    }
}
