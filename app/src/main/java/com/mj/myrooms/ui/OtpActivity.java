package com.mj.myrooms.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mj.myrooms.R;
import com.mj.myrooms.databinding.ActivityOtpBindingImpl;
import com.mj.myrooms.databinding.ActivitySplashBinding;
import com.mj.myrooms.utils.IntentUtils;

public class OtpActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = OtpActivity.this;
    private ActivityOtpBindingImpl layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
    }

}