package com.mj.myrooms.ui.activity;

import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.google.gson.JsonObject;
import com.mj.myrooms.BaseAppCompatActivity;
import com.mj.myrooms.MainActivity;
import com.mj.myrooms.R;
import com.mj.myrooms.constant.ApiConstant;
import com.mj.myrooms.constant.Constant;
import com.mj.myrooms.databinding.ActivitySplashBinding;
import com.mj.myrooms.object.core.LoginResponse;
import com.mj.myrooms.services.APIClient;
import com.mj.myrooms.utils.IntentUtils;
import com.mj.myrooms.utils.PreferenceUtils;
import com.mj.myrooms.utils.Utility;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseAppCompatActivity implements View.OnClickListener  {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = SplashActivity.this;
    private ActivitySplashBinding layoutBinding;
    private boolean isKeyboardTouch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initToolbar();
        initListener();
        if (Utility.isLoggedIn()) {
            request_login();
        } else {
            navigateToNext();
        }
    }


}