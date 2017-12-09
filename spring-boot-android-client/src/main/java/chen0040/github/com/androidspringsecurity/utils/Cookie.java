/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chen0040.github.com.androidspringsecurity.utils;

/**
 *
 * @author chen0469
 */
public class Cookie {
    private final String name;
    private final String value;
    
    public Cookie(String name, String value){
        this.name = name;
        this.value = value;
    }
    
    public String getName(){
        return name;
    }
    
    public String getValue(){
        return value;
    }
    
    @Override
    public String toString(){
        return name+"="+value;
    }
}
