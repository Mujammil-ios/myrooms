package com.mj.myrooms.ui.activity;

import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mj.myrooms.BaseAppCompatActivity;
import com.mj.myrooms.MainActivity;
import com.mj.myrooms.R;
import com.mj.myrooms.constant.ApiConstant;
import com.mj.myrooms.constant.Constant;
import com.mj.myrooms.databinding.ActivityLoginBinding;
import com.mj.myrooms.object.core.LoginResponse;
import com.mj.myrooms.object.core.ResponceData;
import com.mj.myrooms.services.APIClient;
import com.mj.myrooms.utils.IntentUtils;
import com.mj.myrooms.utils.PreferenceUtils;
import com.mj.myrooms.utils.Utility;
import com.mj.myrooms.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = LoginActivity.this;
    private ActivityLoginBinding layoutBinding;
    private boolean isKeyboardTouch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        initToolbar();
        initListener();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentUtils.getInstance().transitionPrevious(mActivity);
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
        switch (v.getId()) {

            case R.id.btn_submit:
                Utility.hideSoftKeyboard(mActivity);
                if (isValidate()) {
                    request_login();
                }
                break;

            case R.id.tv_forgot_password:
                IntentUtils.getInstance().navigateToNextActivity(mActivity,
                        null,
                        RegisterRoleActivity.class,
                        new Bundle(),
                        null);
                break;

            case R.id.tv_sign_up:
                IntentUtils.getInstance().navigateToNextActivity(mActivity,
                        null,
                        RegisterRoleActivity.class,
                        new Bundle(),
                        null);
                break;
        }
    }

    /**
     * navigate to dashboard
     */
    private void navigateToDashboard() {
        ResponceData userDetails = PreferenceUtils.getInstance().getUser();
        if (Constant.user_type_customer == userDetails.getRoleId()) {
            IntentUtils.getInstance().navigateToNextActivity(mActivity,
                    MainActivity.class,
                    new Bundle(),
                    true);
        } else if (Constant.user_type_owner == userDetails.getRoleId()) {
            IntentUtils.getInstance().navigateToNextActivity(mActivity,
                    MainActivity.class,
                    new Bundle(),
                    true);
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
        layoutBinding.etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Utility.hideSoftKeyboard(mActivity);
                    if (isValidate()) {
                        request_login();
                    }
                }
                return false;
            }
        });

        layoutBinding.btnSubmit.setOnClickListener(this);
        layoutBinding.tvForgotPassword.setOnClickListener(this);
        layoutBinding.tvSignUp.setOnClickListener(this);
    }

    /**
     * after login/registration save data and navigate
     *
     * @param object_user
     */
    private void saveData(LoginResponse object_user) {
        PreferenceUtils.getInstance().setUser(object_user.getResponceData());
        PreferenceUtils.getInstance().setUserCredential(
                layoutBinding.etPhoneNumber.getText().toString(),
                layoutBinding.etPassword.getText().toString());
        PreferenceUtils.getInstance().setLoggedIn(true);
        Bundle bundle = new Bundle();
            navigateToDashboard();
    }

    /**
     * check validation
     *
     * @return
     */
    private boolean isValidate() {
        boolean isValidate = true;

        if (TextUtils.isEmpty(layoutBinding.etPhoneNumber.getText().toString().trim())) {
            showSnackbarError(mActivity, getString(R.string.error_enter_email));
            return false;
        } else if (!ValidationUtils.getInstance().isValidEmail(layoutBinding.etPhoneNumber.getText().toString().trim())) {
            showSnackbarError(mActivity, getString(R.string.error_enter_valid_email));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.etPassword.getText().toString().trim())) {
            showSnackbarError(mActivity, getString(R.string.error_enter_password));
            return false;
        }

        return isValidate;
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
            data.put(Constant.email, layoutBinding.etPhoneNumber.getText().toString());
            data.put(Constant.password, layoutBinding.etPassword.getText().toString());
            data.put(Constant.device_type, Constant.const_device_type);
            data.put(Constant.device_token, PreferenceUtils.getInstance().getFcmToken());
            data.put(Constant.timezone, TimeZone.getDefault().getID());
            data.put(Constant.usertype, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface_server_user().post(
                APIClient.RQ_LOGIN,
                data);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {
                    LoginResponse response = (LoginResponse) Utility.jsonToPojo(json.body().toString(), LoginResponse.class);
                    if (json.isSuccessful() && response.getResponceCode() == ApiConstant.STATUS_SUCCESS) {
                        saveData(response);
                    } else {
                        showExceptionError(mActivity, json.body().toString());
                    }
                } catch (Exception e) {
                    showExceptionError(mActivity, json.errorBody());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                hideProgress();
                showExceptionError(mActivity);
            }
        });
    }
}