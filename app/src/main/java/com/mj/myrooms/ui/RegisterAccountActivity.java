package com.mj.myrooms.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mj.myrooms.R;
import com.mj.myrooms.databinding.ActivityLoginBinding;
import com.mj.myrooms.databinding.ActivityRegisterAccountBinding;
import com.mj.myrooms.utils.IntentUtils;

public class RegisterAccountActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = RegisterAccountActivity.this;
    private ActivityRegisterAccountBinding layoutBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_account);
        initListener();
    }
    /**
     * initialize listener
     */
    private void initListener() {
        layoutBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.getInstance().navigateToNextActivity(mActivity,
                        null,
                        OtpActivity.class,
                        new Bundle(),
                        null);
            }
        });
    }
}