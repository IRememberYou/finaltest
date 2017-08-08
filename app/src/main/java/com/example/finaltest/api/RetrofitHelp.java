package com.example.finaltest.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by pinan on 2017/7/12.
 */

public class RetrofitHelp {
    
    private static final String TAG = "RetrofitHelp";
    
    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
            .baseUrl("http://www.baidu.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
        
//        Api api = sRetrofit.create(Api.class);
//        api.getData().enqueue(new Callback<ApiModel>() {
//            @Override
//            public void onResponse(Call<ApiModel> call, Response<ApiModel> response) {
//                ApiModel body = response.body();
//                Log.d(TAG, "onResponse: " + response.message().toString());
//                Toast.makeText(MyApp.sContext, "message:" + response.message().toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<ApiModel> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + t.toString());
//            }
//        });
    }
    
    
}