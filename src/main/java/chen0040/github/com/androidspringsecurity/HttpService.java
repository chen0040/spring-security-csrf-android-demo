/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chen0040.github.com.androidspringsecurity;

import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import chen0040.github.com.androidspringsecurity.functions.Consumer;
import chen0040.github.com.androidspringsecurity.utils.Cookie;
import chen0040.github.com.androidspringsecurity.utils.HttpBinaryResult;
import chen0040.github.com.androidspringsecurity.utils.HttpResult;


/**
 *
 * @author chen0469
 */
public class HttpService {
    
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36";
    private static HttpService singleton;
    private static Logger logger = Logger.getLogger(String.valueOf(HttpService.class));
    
    public static HttpService getSingleton(){
        if(singleton==null){
            singleton = new HttpService();
        }
        return singleton;
    }
    
    private void getCookie(String urlAddress, Consumer<HttpResult> onCompletedHandler){
        String content = "";
        Exception error = null;
        
        try{
        URL url = new URL(urlAddress);
        URLConnection con = url.openConnection();
        
        con.connect();
        
        String headerName = null;
        for(int i=1; (headerName=con.getHeaderFieldKey(i))!=null; ++i){
            
        }
        }catch(Exception ex){
            error = ex;
            ScopeService.getSingleton().setLastException(error);
        }
    }
    
    public void getAsync(String urlAddress, List<Cookie> cookies, Consumer<HttpResult> onCompletedHandler){
        Exception error = null;
        String content = null;
        String cookie = null;
        int responseCode = 200;
        
        try{
            URL url = new URL(urlAddress);
            
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setRequestProperty("User-Agent", USER_AGENT);
            
             if(cookies != null && !cookies.isEmpty()){
                String ccc = format(cookies);
                con.setRequestProperty("Cookie", ccc);
            }
            
            con.connect();
            
            responseCode = con.getResponseCode();
            
            String headerName = null;
            for(int i=1; (headerName=con.getHeaderFieldKey(i))!=null; ++i){
                if(headerName.equals("Set-Cookie")){
                    cookie = con.getHeaderField(i);
                }
            }

            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = in.readLine())!=null){
                response.append(line);
            }
            in.close();

            content = response.toString();
        }catch(Exception ex){
            error=ex;
        }
        
        if(onCompletedHandler != null){
            onCompletedHandler.apply(new HttpResult(content, cookie, responseCode, error));
        }
    }
    
    public void getBinaryAsync(String urlAddress, Map<String, String> params, List<Cookie> cookies, Consumer<HttpBinaryResult> onCompletedHandler){
        Exception error = null;
        byte[] content = null;
        String cookie = null;
        int responseCode = 200;
        
        try{
            
            if(params.size() > 0){
                urlAddress+=("?"+encodeParams(params));
            }
            
            URL url = new URL(urlAddress);
            
            
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setRequestProperty("User-Agent", USER_AGENT);
            
             if(cookies != null && !cookies.isEmpty()){
                String ccc = format(cookies);
                con.setRequestProperty("Cookie", ccc);
            }
            
            con.connect();
            
            responseCode = con.getResponseCode();
            
            String headerName = null;
            for(int i=1; (headerName=con.getHeaderFieldKey(i))!=null; ++i){
                if(headerName.equals("Set-Cookie")){
                    cookie = con.getHeaderField(i);
                }
            }

            
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            InputStream in = con.getInputStream();
            byte[] buffer = new byte[4096];
            int n= -1;
            while((n = in.read(buffer)) != -1){
                if (n > 0){
                    output.write(buffer, 0, n);
                }
            }
           
            in.close();
            
            output.flush();
            content = output.toByteArray();
            
            output.close();
            
            
            
            
        }catch(Exception ex){
            error=ex;
        }
        
        if(onCompletedHandler != null){
            onCompletedHandler.apply(new HttpBinaryResult(content, cookie, responseCode, error));
        }
    }
    
    private static String encodeParams(Map<String, String> params) throws Exception {
        StringBuilder sb = new StringBuilder();
        
        boolean first=true;
        for (String paramname : params.keySet()) {
            if(first){
                first=false;
            }else {
                sb.append("&");
            }
            String paramvalue = params.get(paramname);
            sb.append(paramname).append("=").append(encode(paramvalue));
        }
        
        return sb.toString();
    }
    
    public static String encode(String text) throws Exception{
        return URLEncoder.encode(text, "UTF-8");
    }
    
    private static String format(List<Cookie> cookies){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < cookies.size(); ++i){
            if(i > 0){
                sb.append(";");
            }
            sb.append(cookies.toString());
        }
        return sb.toString();
    }

    public void postBinary(String urlAddress, Map<String, String> params, String attachmentName, File file, String fileMimeType, List<Cookie> cookies, Consumer<HttpResult> onPostCompleted) {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        InputStream inputStream = null;

        String twoHyphens = "--";
        String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
        String lineEnd = "\r\n";

        String cookie2 = "";

        String content = "";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        Exception error = null;

        int responseCode = -1;


        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            URL url = new URL(urlAddress);
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);


            if(cookies != null && !cookies.isEmpty()){
                String ccc = format(cookies);
                connection.setRequestProperty("Cookie", ccc);
            }

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"" + attachmentName + "\"; filename=\"" + file.getName() + "\"" + lineEnd);
            outputStream.writeBytes("Content-Type: " + fileMimeType + lineEnd);
            outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);

            outputStream.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            outputStream.writeBytes(lineEnd);

            // Upload POST Data
            Iterator<String> keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.get(key);

                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
                outputStream.writeBytes("Content-Type: text/plain" + lineEnd);
                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(value);
                outputStream.writeBytes(lineEnd);
            }

            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            responseCode = connection.getResponseCode();

            if (200 != responseCode) {
                logger.severe("Failed to upload code:" + connection.getResponseCode() + " " + connection.getResponseMessage());
            }

            inputStream = connection.getInputStream();

            String headerName = null;
            for(int i=1; (headerName=connection.getHeaderFieldKey(i))!=null; ++i){
                if(headerName.equals("Set-Cookie")){
                    cookie2 = connection.getHeaderField(i);
                    break;
                }
            }

            content = this.convertStreamToString(inputStream);

            fileInputStream.close();
            inputStream.close();
            outputStream.flush();
            outputStream.close();


        } catch (Exception e) {
            error = e;
            logger.log(Level.SEVERE, "postBinary() failed", e);
        }

        if(onPostCompleted != null){
            ScopeService.getSingleton().setLastException(error);
            onPostCompleted.apply(new HttpResult(content, cookie2, responseCode, error));
        }

    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    public void postBinaryAsync(String urlAddress, Map<String, String> params, String attachmentName, File file, List<Cookie> cookies, Consumer<HttpResult> onPostCompleted){
        String content = null;
        Exception error = null;
        String cookie2 = null;
        int responseCode = 200;
        
        String crlf = "\r\n";
        
        String boundary = "===" + System.currentTimeMillis() + "===";
        
        try{

            URL url = new URL(urlAddress);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            
            con.setRequestMethod("POST");
            con.setUseCaches(false);
            con.setDoOutput(true);    
            con.setDoInput(true);
            
            //con.setRequestProperty("Connection", "Keep-Alive");
            //con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            con.setRequestProperty("User-Agent", USER_AGENT);
           
            String urlParameters = encodeParams(params);
            
            
            if(cookies != null && !cookies.isEmpty()){
                String ccc = format(cookies);
                con.setRequestProperty("Cookie", ccc);
            }
            
			
            String charset ="UTF-8";
            
            con.connect();
            
            OutputStream outputStream = con.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
            
            for(String paramname : params.keySet()){
                addFormField(writer, paramname, params.get(paramname), boundary, crlf, charset);
            }
            
            this.addFilePart(outputStream, writer, attachmentName, file, boundary, crlf, charset);
            
            writer.append(crlf).flush();
            writer.append("--" + boundary + "--").append(crlf);
            
            writer.flush();
            writer.close();
            
            responseCode = con.getResponseCode();
            
            String headerName = null;
            for(int i=1; (headerName=con.getHeaderFieldKey(i))!=null; ++i){
                if(headerName.equals("Set-Cookie")){
                    cookie2 = con.getHeaderField(i);
                    break;
                }
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            
            String line;
            StringBuilder response = new StringBuilder();
            
            while((line = reader.readLine())!=null){
                response.append(line);
            }
            
            reader.close();
            
            content = response.toString();
            
            
        }catch(Exception ex){
            error = ex;
        }
        
        if(onPostCompleted != null){
            ScopeService.getSingleton().setLastException(error);
            onPostCompleted.apply(new HttpResult(content, cookie2, responseCode, error));
        }
    }


    public void postAsync(String urlAddress, Map<String, String> params, List<Cookie> cookies, Consumer<HttpResult> onPostCompleted){
        String content = null;
        Exception error = null;
        String cookie2 = null;
        int responseCode = 200;
        
        try{
            URL url = new URL(urlAddress);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
           
            String urlParameters = encodeParams(params);
            
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
            if(cookies != null && !cookies.isEmpty()){
                String ccc = format(cookies);
                con.setRequestProperty("Cookie", ccc);
            }
            
			
            con.setDoOutput(true);    
            
            con.connect();
            
            DataOutputStream writer = new DataOutputStream(con.getOutputStream());
            writer.writeBytes(urlParameters);
            writer.flush();
            writer.close();
            
            responseCode = con.getResponseCode();
            
            String headerName = null;
            for(int i=1; (headerName=con.getHeaderFieldKey(i))!=null; ++i){
                if(headerName.equals("Set-Cookie")){
                    cookie2 = con.getHeaderField(i);
                    break;
                }
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            
            String line;
            StringBuilder response = new StringBuilder();
            
            while((line = reader.readLine())!=null){
                response.append(line);
            }
            
            reader.close();
            
            content = response.toString();
            
            
        }catch(Exception ex){
            error = ex;
        }
        
        if(onPostCompleted != null){
            ScopeService.getSingleton().setLastException(error);
            onPostCompleted.apply(new HttpResult(content, cookie2, responseCode, error));
        }
    }

    private void addFormField(PrintWriter writer, String paramname, String paramvalue, String boundary, String LINE_FEED, String charset) throws Exception{
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + paramname + "\"")
                .append(LINE_FEED);
        writer.append("Content-Type: text/plain; charset=" + charset).append(
                LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(encode(paramvalue)).append(LINE_FEED);
        writer.flush();
    }
    
    public void addFilePart(OutputStream outputStream, PrintWriter writer, String fieldName, File uploadFile, String boundary, String LINE_FEED, String charset)
            throws IOException {
        String fileName = uploadFile.getName();
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append(
                "Content-Disposition: form-data; name=\"" + fieldName
                        + "\"; filename=\"" + fileName + "\"")
                .append(LINE_FEED);
        writer.append(
                "Content-Type: "
                        + URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();
 
        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();
         
        writer.append(LINE_FEED);
        writer.flush();    
    }


}
