package com.github.chen0040.sbclient;

import java.util.HashMap;
import java.util.Map;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Map<String, String> getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(Map<String, String> tokenInfo) {
        this.tokenInfo = tokenInfo;
    }
}
