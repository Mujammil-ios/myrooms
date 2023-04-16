package com.mj.myrooms.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;

import com.mj.myrooms.R;
import com.mj.myrooms.databinding.ActivityLoginBinding;

public class LandingPageActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = LandingPageActivity.this;

    private ActivityLoginBinding layoutBinding;
    private boolean isKeyboardTouch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_landing_page);

    }
}