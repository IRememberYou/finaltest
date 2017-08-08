package com.example.finaltest;

import com.example.finaltest.test7.Bean;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by pinan on 2017/7/14.
 */

public class MyTest {
    private static String baseUrl = "http://172.16.32.233:8080/war/text.do";
    public static void main(String[] args) {
        
        URL url = null;
        try {
            url = new URL(baseUrl);
            
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            String s = InputStream2Str(inputStream);
            Gson gson = new Gson();
            Bean bean = gson.fromJson(s, Bean.class);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static String InputStream2Str(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        is.close();
        String result = baos.toString();
        baos.close();
        
        return result;
    }
    
    
}
