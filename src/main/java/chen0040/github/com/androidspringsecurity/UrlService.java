/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chen0040.github.com.androidspringsecurity;

/**
 *
 * @author chen0469
 */
public class UrlService {
    private static UrlService singleton = null;
    private String baseUrl = "localhost:8080";
    
    public String getUrlAddress(String relativeAddr){
        return baseUrl +"/"+relativeAddr;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static synchronized UrlService getSingleton(){
        if(singleton==null){
            singleton = new UrlService();
        }
        return singleton;
    }
}
