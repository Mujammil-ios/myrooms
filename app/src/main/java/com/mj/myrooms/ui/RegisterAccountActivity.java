package com.mj.myrooms.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;

import com.mj.myrooms.R;
import com.mj.myrooms.databinding.ActivityLoginBinding;
import com.mj.myrooms.databinding.ActivityRegisterAccountBinding;

public class RegisterAccountActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = RegisterAccountActivity.this;
    private ActivityRegisterAccountBinding layoutBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_account);

    }
}