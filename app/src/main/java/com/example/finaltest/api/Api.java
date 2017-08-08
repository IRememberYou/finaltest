package com.example.finaltest.api;

import com.example.finaltest.test5.ApiModel;

import retrofit2.http.GET;
import rx.Observable;

public interface Api {
    @GET("http://www.oschina.net/action/apiv2/banner?catalog=3")
    Observable<ApiModel> getData();
}