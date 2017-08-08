package com.example.finaltest.test7;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finaltest.R;
import com.example.finaltest.app.BaseFragment;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by pinan on 2017/7/24.
 */

public class MyFragment extends BaseFragment {
    
    private String mFlag;
//    private String baseUrl = "http://172.16.32.233:8080/";
    private String baseUrl = "http://172.16.32.233:8080/war/text.do";
    
    public static MyFragment newIntance(String flag) {
        Bundle bundle = new Bundle();
        bundle.putString(constan.FlagFragment, flag);
        return new MyFragment();
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFlag = getArguments().getString(constan.FlagFragment);
        }
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            URL url = new URL(baseUrl);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            String s = InputStream2Str(inputStream);
            Gson gson = new Gson();
            Bean bean = gson.fromJson(s, Bean.class);
            
    
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.my_fragment, container, false);
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    
    public String InputStream2Str(InputStream is) throws IOException {
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
