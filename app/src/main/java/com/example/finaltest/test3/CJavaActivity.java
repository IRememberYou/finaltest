package com.example.finaltest.test3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finaltest.R;
import com.example.finaltest.app.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pinan on 2017/7/10.
 */

public class CJavaActivity extends BaseActivity {
    @Bind(R.id.et_a)
    EditText etA;
    @Bind(R.id.et_b)
    EditText etB;
    @Bind(R.id.tv_result)
    TextView result;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cjava);
    }
    
    @OnClick(R.id.calculate)
    void calculate() {
        int a = str2int(etA);
        int b = str2int(etB);
        result.setText(maxFormJNI(a, b) + "");
    }
    
    private int str2int(EditText et) {
        String strNum = et.getText().toString().trim();
        return Integer.parseInt(strNum);
    }
    
    public static native int maxFormJNI(int a, int b);
}
