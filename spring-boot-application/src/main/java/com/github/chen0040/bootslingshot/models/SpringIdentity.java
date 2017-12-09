package com.github.chen0040.bootslingshot.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SpringIdentity {
    private String username;
    private boolean authenticated;
    private Map<String, String> tokenInfo = new HashMap<>();

    public SpringIdentity(String username, boolean authenticated) {
        this.username = username;
        this.authenticated = authenticated;
    }

    public SpringIdentity() {

    }

}
