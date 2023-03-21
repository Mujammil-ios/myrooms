package com.mj.myrooms.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.mj.myrooms.MainActivity;
import com.mj.myrooms.R;
import com.mj.myrooms.databinding.ActivityLoginBinding;
import com.mj.myrooms.utils.IntentUtils;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = LoginActivity.this;
    private ActivityLoginBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
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
                        MainActivity.class,
                        new Bundle(),
                        null);
            }
        });
    }

}