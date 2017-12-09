package com.github.chen0040.sbclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.testng.annotations.Test;

public class SpringBootClientUnitTest {
    @Test
    public void testLogin() {
        SpringBootClient client = new SpringBootClient();
        SpringIdentity identity = client.login("http://localhost:8080/erp/login-api-json", "admin", "admin");

        System.out.println(JSON.toJSONString(identity, SerializerFeature.PrettyFormat));
        System.out.println(client.getSecured("http://localhost:8080/users/get-account"));
    }
}
