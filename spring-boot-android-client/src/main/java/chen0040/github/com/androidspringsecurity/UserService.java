/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chen0040.github.com.androidspringsecurity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import chen0040.github.com.androidspringsecurity.functions.Consumer;
import chen0040.github.com.androidspringsecurity.utils.Cookie;
import chen0040.github.com.androidspringsecurity.utils.CookieContainer;
import chen0040.github.com.androidspringsecurity.utils.HttpResult;

/**
 *
 * @author chen0469
 */
public class UserService {

    private static Logger logger = Logger.getLogger(String.valueOf(UserService.class));

    private static UserService singleton = null;
    private String csrfToken;
    private String csrfParameterName;
    private String csrfHeader;
    private Cookie jSessionCookie;
    
    private String getAjaxUrl(){
        return UrlService.getSingleton().getUrlAddress("api/ajax");
    }
    
    private String getLoginUrl(){
        return UrlService.getSingleton().getUrlAddress("login");
    }
    
    private String getLogoutUrl(){
        return UrlService.getSingleton().getUrlAddress("logout");
    }
    
    public String getToken(){
        return csrfToken;
    }
    
    public Cookie getJSessionCookie(){
        return jSessionCookie;
    }
    
    public static UserService getSingleton(){
        if(singleton == null){
            singleton = new UserService();
        }
        return singleton;
    }
    
    private void readCsrfToken(String content){
        try {
            JSONObject obj = new JSONObject(content);
            this.csrfToken = obj.getString("_csrf.token");
            this.csrfHeader = obj.getString("_csrf.header");
            this.csrfParameterName = obj.getString("_csrf.parameterName");
        }catch(JSONException ex){

        }
    }
    
    private void readJSessionCookie(CookieContainer cc){
        if(cc.containsKey("JSESSIONID")){
            jSessionCookie = cc.get("JSESSIONID");
        }
    }
    
    private void ping(final Consumer<Boolean> onPingCompleted){
         HttpService.getSingleton().getAsync(getAjaxUrl(), null,
                 new Consumer<HttpResult>() {
                     @Override
                     public void apply(HttpResult response) {
                         if(response.getException()==null){
                             readCsrfToken(response.getContent());
                             readJSessionCookie(response.getCookie());
                             onPingCompleted.apply(Boolean.TRUE);
                         }
                         else{
                             response.getException().printStackTrace();
                             ScopeService.getSingleton().setLastException(response.getException());
                             onPingCompleted.apply(Boolean.FALSE);
                         }
                     }
                 });
    }

    public void login(final String username, final String password, final Consumer<Boolean> onLoginCompleted){
        ping(new Consumer<Boolean>() {
            @Override
            public void apply(Boolean success) {
                if (success) {
                    _login(username, password, onLoginCompleted);
                } else {
                    onLoginCompleted.apply(Boolean.FALSE);
                }
            }
        });
    }

    public void logout(){
        this.logout(null);
    }
    
    public void logout(final Consumer<Boolean> onLogoutCompleted){
        Map<String, String> authparams =new HashMap<String, String>();
        List<Cookie> cookies = new ArrayList<Cookie>();
       
        this.addSecurityInfo(authparams, cookies);
        HttpService.getSingleton().postAsync(getLogoutUrl(), authparams, cookies, new Consumer<HttpResult>() {
            @Override
            public void apply(HttpResult response) {
                if(response.getException() != null){
                    if(onLogoutCompleted != null) onLogoutCompleted.apply(Boolean.FALSE);
                }else {
                    if(onLogoutCompleted != null) onLogoutCompleted.apply(Boolean.TRUE);
                }
            }
        });
        
    }
    
    public void addSecurityInfo(Map<String, String> authparams, List<Cookie> cookies){
        authparams.put("_csrf", this.csrfToken);
        cookies.add(jSessionCookie);
    }
    
    private void _login(String username, String password, final Consumer<Boolean> onLoginCompleted){
        Map<String, String> authparams =new HashMap<String, String>();
        authparams.put("username", username);
        authparams.put("password", password);
        authparams.put("ajax", "true");
       
        List<Cookie> cookies = new ArrayList<Cookie>();
       
        this.addSecurityInfo(authparams, cookies);
        

        HttpService.getSingleton().postAsync(getLoginUrl(), authparams, cookies, new Consumer<HttpResult>() {
            @Override
            public void apply(HttpResult response) {
                if(response.getException() != null){
                    onLoginCompleted.apply(Boolean.FALSE);
                }else if(response.getContent().startsWith("AJAX-LOGIN-SUCCESS")){

                    String[] parts = response.getContent().split(";");
                    UserService.this.csrfToken = parts[1];
                    UserService.this.readJSessionCookie(response.getCookie());
                    onLoginCompleted.apply(Boolean.TRUE);
                }else {
                    ScopeService.getSingleton().setLastException(new Exception(response.getContent()));
                    onLoginCompleted.apply(Boolean.FALSE);
                }
            }
        });
    }
}

