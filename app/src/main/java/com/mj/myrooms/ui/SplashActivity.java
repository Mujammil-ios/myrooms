package com.mj.myrooms.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.mj.myrooms.MainActivity;
import com.mj.myrooms.R;
import com.mj.myrooms.databinding.ActivitySplashBinding;
import com.mj.myrooms.utils.IntentUtils;

public class SplashActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = SplashActivity.this;
    private ActivitySplashBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        navigateToNext();

    }
    /**
     * navigate to next
     */
    private void navigateToNext() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = getIntent().getExtras();
                if (bundle == null) {
                    bundle = new Bundle();
                }
                IntentUtils.getInstance().navigateToNextActivity(mActivity,
                        MainActivity.class,
                        bundle,
                        true);
                finish();
            }
        },3000);
    }

}