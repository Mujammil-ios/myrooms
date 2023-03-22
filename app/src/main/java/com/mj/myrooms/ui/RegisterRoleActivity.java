package com.mj.myrooms.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mj.myrooms.R;
import com.mj.myrooms.databinding.ActivityRegisterRoleBinding;
import com.mj.myrooms.utils.IntentUtils;


public class RegisterRoleActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = RegisterRoleActivity.this;
    private ActivityRegisterRoleBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_role);
        layoutBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.getInstance().navigateToNextActivity(mActivity, null,LoginActivity.class,new Bundle(), null);
            }
        });

    }
}