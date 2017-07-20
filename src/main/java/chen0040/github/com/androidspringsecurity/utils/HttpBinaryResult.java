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
public class HttpBinaryResult {

    private final Exception exception;
    private final byte[] content;
    private final int responseCode;

    public CookieContainer getCookie() {
        return cookie;
    }
    
    public int getResponseCode(){
        return responseCode;
    }
    
    private CookieContainer cookie = new CookieContainer();
    
    public HttpBinaryResult(byte[] content, String cookies, int responseCode, Exception exception){
        this.content = content;
        this.exception = exception;
        this.responseCode = responseCode;
        this.cookie=new CookieContainer(cookies);
    }

    public Exception getException() {
        return exception;
    }

    public byte[] getContent() {
        return content;
    }
}
