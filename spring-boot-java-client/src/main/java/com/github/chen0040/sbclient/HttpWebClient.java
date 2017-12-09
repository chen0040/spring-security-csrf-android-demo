package com.github.chen0040.sbclient;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.Header;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by xschen on 3/10/16.
 */
public class HttpWebClient implements WebClient {
   private Map<String, Object> properties = new HashMap<>();
   private static final String DATA_ENCODING = "UTF-8";
   private String token = "";
   private String sessionId = "";

   private static final Logger logger = LoggerFactory.getLogger(HttpWebClient.class);


   public String getToken() {
      return token;
   }

   public String getSessionId() {
      return sessionId;
   }

   private static CloseableHttpClient buildClient() {

      int timeout = 10;
      RequestConfig config = RequestConfig.custom()
              .setSocketTimeout(timeout * 1000)
              .setCookieSpec(CookieSpecs.DEFAULT)
              .setConnectTimeout(timeout * 1000).build();


      return HttpClients.custom().setDefaultRequestConfig(config).build(); //builder.build();

   }


   public void add(String key, Object value) {
      properties.put(key, value);
   }


   public String post(final String url) {
      CloseableHttpClient httpClient = buildClient();
      String json = "";
      String body = JSON.toJSONString(properties);
      try {
         HttpPost request = new HttpPost(url);
         StringEntity params = new StringEntity(body);
         request.addHeader("content-type", "application/json");
         request.setEntity(params);
         CloseableHttpResponse result = httpClient.execute(request);
         if (result.getEntity() != null) {
            json = EntityUtils.toString(result.getEntity(), DATA_ENCODING);
         }
         result.close();
         httpClient.close();
      }
      catch (IOException ex) {
         json = ex.getMessage();
      }

      return json;
   }


   public String post(final String url, final Map<String, String> headers) {
      CloseableHttpClient httpClient = buildClient();
      String json = "";
      try {
         HttpPost request = new HttpPost(url);

         for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
         }

         CloseableHttpResponse result = httpClient.execute(request);
         if (result.getEntity() != null) {
            json = EntityUtils.toString(result.getEntity(), DATA_ENCODING);
         }
         result.close();
         httpClient.close();
      }
      catch (IOException ex) {
         json = ex.getMessage();
      }

      return json;
   }

   public String post(final String url, final String body, final Map<String, String> headers) {
      CloseableHttpClient httpClient = buildClient();
      String json = "";
      try {
         HttpPost request = new HttpPost(url);

         StringEntity params = new StringEntity(body);

         request.setEntity(params);

         for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
         }

         CloseableHttpResponse result = httpClient.execute(request);

         Header header = result.getFirstHeader("Set-Cookie");
         if(header != null) {
            logger.info("Set-Cookie: {}", header.getValue());
            String[] parts = header.getValue().split(";");
            for(String part : parts) {
               String[] pair = part.split("=");
               String pair_name = pair[0];
               String pair_value = "";
               if(pair.length > 1) {
                  pair_value = pair[1];
               }
               if(pair_name.equals("XSRF-TOKEN")) {
                  token = pair_value;
                  logger.info("POST XSRF-TOKEN: {}", pair_value);
                  break;
               } else if(pair_name.equals("JSESSIONID")) {
                  sessionId = pair_value;
                  logger.info("POST JSESSIONID: {}", sessionId);
               }
            }
         }

         if (result.getEntity() != null) {
            json = EntityUtils.toString(result.getEntity(), DATA_ENCODING);
         }

         result.close();
         httpClient.close();
      }
      catch (IOException ex) {
         json = ex.getMessage();
      }

      return json;
   }


   public static String post(final String url, Object requestBody) {
      CloseableHttpClient httpClient = buildClient();
      String json = "";
      try {
         HttpPost request = new HttpPost(url);

         StringEntity params = new StringEntity(JSON.toJSONString(requestBody, SerializerFeature.BrowserCompatible));
         params.setContentType("application/json");

         request.setEntity(params);

         CloseableHttpResponse result = httpClient.execute(request);
         if (result.getEntity() != null) {
            json = EntityUtils.toString(result.getEntity(), DATA_ENCODING);
         }
         result.close();
         httpClient.close();
      }
      catch (IOException ex) {
         json = ex.getMessage();
         logger.error("Failed to post", ex);
      }

      return json;
   }


   public String delete(final String url, final Map<String, String> headers) {

      CloseableHttpClient httpClient = buildClient();
      String json = "";
      try {
         HttpDelete request = new HttpDelete(url);

         for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
         }

         CloseableHttpResponse response = httpClient.execute(request);
         if (response.getEntity() != null) {
            json = EntityUtils.toString(response.getEntity(), DATA_ENCODING);
         }
         response.close();
         httpClient.close();
      }
      catch (IOException ex) {
         json = ex.getMessage();
      }
      return json;
   }


   public String delete(final String url) {

      CloseableHttpClient httpClient = buildClient();
      String json = "";
      try {
         HttpDelete request = new HttpDelete(url);
         request.addHeader("content-type", "application/json");
         CloseableHttpResponse response = httpClient.execute(request);
         if (response.getEntity() != null) {
            json = EntityUtils.toString(response.getEntity(), DATA_ENCODING);
         }
         response.close();
         httpClient.close();
      }
      catch (IOException ex) {
         json = ex.getMessage();
      }
      return json;
   }



   public static String getJson(final String url) {
      String json = "";
      try {
         CloseableHttpClient httpClient = buildClient();
         HttpGet request = new HttpGet(url);
         request.addHeader("content-type", "application/json");
         CloseableHttpResponse response = httpClient.execute(request);
         if (response.getEntity() != null) {
            json = EntityUtils.toString(response.getEntity(), DATA_ENCODING);
         }
      }
      catch (Exception ex2) {
         json = ex2.getMessage();
      }

      return json;
   }

   @Override
   public String get(final String url) throws Exception {

      CloseableHttpClient httpClient = buildClient();

      RequestConfig globalConfig = RequestConfig.custom()
              .setCookieSpec(CookieSpecs.DEFAULT).build();
      RequestConfig localConfig = RequestConfig.copy(globalConfig)
              .setCookieSpec(CookieSpecs.STANDARD)
              .build();

      HttpGet request = new HttpGet(url);
      request.setConfig(localConfig);

      CloseableHttpResponse response = httpClient.execute(request);


      Header header = response.getFirstHeader("Set-Cookie");
      if(header != null) {
         String[] parts = header.getValue().split(";");
         for(String part : parts) {
            String[] pair = part.split("=");
            String pair_name = pair[0];
            String pair_value = "";
            if(pair.length > 1) pair_value = pair[1];
            if(pair_name.equals("XSRF-TOKEN")) {
               token = pair_value;
               logger.info("GET XSRF-TOKEN: {}", pair_value);
               break;
            }
         }
      }
      if (response.getEntity() != null) {
         return EntityUtils.toString(response.getEntity(), DATA_ENCODING);
      }



      return null;
   }

   @Override
   public String get(final String url, final Map<String, String> headers) {
      String json = "";
      try {
         CloseableHttpClient httpClient = buildClient();
         HttpGet request = new HttpGet(url);
         for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
         }
         CloseableHttpResponse response = httpClient.execute(request);
         if (response.getEntity() != null) {
            json = EntityUtils.toString(response.getEntity(), DATA_ENCODING);
         }
      }
      catch (Exception ex2) {
         json = ex2.getMessage();
      }

      return json;
   }

   public static boolean isAlive(String ipAddress, int port) {
      try (Socket s = new Socket(ipAddress, port)) {
         return true;
      } catch (IOException ex) {

      }
      return false;
   }
}
