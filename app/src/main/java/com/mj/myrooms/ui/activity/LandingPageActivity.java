package com.mj.myrooms.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mj.myrooms.R;
import com.mj.myrooms.databinding.ActivityLandingPageBinding;
import com.mj.myrooms.databinding.ActivityLoginBinding;
import com.mj.myrooms.ui.owner.OwnerLandingActivity;
import com.mj.myrooms.utils.IntentUtils;

public class LandingPageActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = LandingPageActivity.this;

    private ActivityLandingPageBinding layoutBinding;
    private boolean isKeyboardTouch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_landing_page);
        findViewById(R.id.tv_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.getInstance().navigateToNextActivity(mActivity,
                        null,
                        OwnerLandingActivity.class,
                        new Bundle(),
                        null);
            }
        });


    }
}