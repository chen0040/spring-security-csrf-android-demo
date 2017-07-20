/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chen0040.github.com.androidspringsecurity.utils;

import java.util.HashMap;

/**
 *
 * @author chen0469
 */
public class CookieContainer extends HashMap<String, Cookie>{
    public CookieContainer(String cookies){
        if(cookies==null) return;
        String[] cookieList = cookies.split(";");
        for(String cookieString : cookieList){
            String[] cookieComps = cookieString.split("=");
            if(cookieComps.length >= 2){
                Cookie cookie = new Cookie(cookieComps[0].trim(), cookieComps[1].trim());
            this.put(cookie.getName(), cookie);
            }
            
        }
    }
    
    public CookieContainer(){
        
    }
}
