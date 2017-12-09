package chen0040.github.com.androidspringsecurity;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.sbclient.SpringBootClient;
import com.github.chen0040.sbclient.SpringIdentity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;


/**
 * Created by xschen on 12/6/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class SpringBootClientUnitTest extends AndroidLogContext {



   @Test
   public void testLogin(){
      SpringBootClient client = new SpringBootClient();

      SpringIdentity identity = client.login("http://localhost:8080/erp/login-api-json", "admin", "admin");

      System.out.println(JSON.toJSONString(identity, SerializerFeature.PrettyFormat));
      System.out.println(client.getSecured("http://localhost:8080/users/get-account"));
   }


}
