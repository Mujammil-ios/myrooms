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

    /**
     * initialize toolbar
     */
    private void initToolbar() {

    }

    /**
     * initialize listener
     */
    private void initListener() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        if (isKeyboardTouch) {
            isKeyboardTouch = false;
            return;
        }
        Utility.hideSoftKeyboard(mActivity, layoutBinding.getRoot());
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        isKeyboardTouch = true;
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * after login/registration save data and navigate
     *
     * @param object_user
     */
    private void saveData(LoginResponse object_user) {
        PreferenceUtils.getInstance().setAuthToken(object_user.getResponceData().getCreatedAt());
        PreferenceUtils.getInstance().setUser(object_user.getResponceData());
        PreferenceUtils.getInstance().setLoggedIn(true);

        navigateToNext();
    }
    /**
     * API call - login
     */
    public void request_login() {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }

        // add request data
        Map<String, String> data = new HashMap<>();
        try {
            data.put(Constant.email, PreferenceUtils.getInstance().getUsername());
            data.put(Constant.password, PreferenceUtils.getInstance().getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call call = APIClient.appInterface_server_user().post(
                APIClient.RQ_LOGIN,
                data);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                try {
                    LoginResponse response = (LoginResponse) Utility.jsonToPojo(json.body().toString(), LoginResponse.class);
                    if (json.isSuccessful() && response.getResponceCode() == ApiConstant.STATUS_SUCCESS) {
                        saveData(response);
                        Log.d("SPLASH", "NEXT RESPONSE" +response.toString());
                        showSnackbarSuccess(mActivity,response.getResponceMessage());
                    } else {
                        showExceptionError(mActivity, json.body().toString());
                        navigateToNext();
                    }
                } catch (Exception e) {
                    showExceptionError(mActivity, json.errorBody());
                    navigateToNext();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                showExceptionError(mActivity);
                navigateToNext();
            }
        });
    }
    /**
     * navigate to next
     */
    private void navigateToNext() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Utility.isLoggedIn()) {
                    Bundle bundle = getIntent().getExtras();
                    if (bundle == null) {
                        bundle = new Bundle();
                    }
                    switch (PreferenceUtils.getInstance().getUser().getRoleId()) {
                        case Constant.user_type_customer:
                            IntentUtils.getInstance().navigateToNextActivity(mActivity,
                                    MainActivity.class,
                                    bundle,
                                    true);
                            finish();
                            break;

                        case Constant.user_type_owner:
                            IntentUtils.getInstance().navigateToNextActivity(mActivity,
                                    MainActivity.class,
                                    bundle,
                                    true);
                            finish();
                            break;
                        }
                    } else {
                    IntentUtils.getInstance().navigateToNextActivity(mActivity,
                            null,
                            LoginActivity.class,
                            new Bundle(),
                            null);
                    finish();
                }
            }
        }, Utility.isLoggedIn() ? 0 : Constant.DELAY_SPLASH);
    }

}