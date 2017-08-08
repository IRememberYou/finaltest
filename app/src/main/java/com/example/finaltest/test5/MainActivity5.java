package com.example.finaltest.test5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.finaltest.R;
import com.example.finaltest.api.Api;
import com.example.finaltest.api.RetrofitHelp;
import com.example.finaltest.app.BaseActivity;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pinan on 2017/7/12.
 */

public class MainActivity5 extends BaseActivity {
    @Bind(R.id.tv_msg)
    TextView msg;
    private ApiModel mApiModel;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Api api = RetrofitHelp.getRetrofit().create(Api.class);
        
        CompositeSubscription compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(api.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<ApiModel>() {
                @Override
                public void onCompleted() {
                    if (mApiModel != null) {
                        msg.setText(mApiModel.toString());
                    }
                }
                
                @Override
                public void onError(Throwable e) {
                    msg.setText(e.toString());
                }
                
                @Override
                public void onNext(ApiModel apiModel) {
                    mApiModel = apiModel;
                }
            })
        );
    }
}
