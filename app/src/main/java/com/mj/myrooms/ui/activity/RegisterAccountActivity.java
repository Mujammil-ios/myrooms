package com.mj.myrooms.ui.activity;

import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mj.myrooms.BaseAppCompatActivity;
import com.mj.myrooms.MainActivity;
import com.mj.myrooms.R;
import com.mj.myrooms.constant.ApiConstant;
import com.mj.myrooms.constant.Constant;
import com.mj.myrooms.databinding.ActivityRegisterAccountBinding;
import com.mj.myrooms.listener.OnCloseListener;
import com.mj.myrooms.object.core.CreateUserResponse;
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

public class RegisterAccountActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = RegisterAccountActivity.this;
    private ActivityRegisterAccountBinding layoutBinding;
    private boolean isKeyboardTouch = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_account);
        Bundle bundle = getIntent().getExtras();
        String stuff =bundle.getString("user_type");
        Log.d(TAG, "VALUE " + " " + stuff);
        Toast.makeText(this, "Value " + stuff, Toast.LENGTH_SHORT).show();

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
                IntentUtils.getInstance().navigateToNextActivity(mActivity,
                        null,
                        LandingPageActivity.class,
                        new Bundle(),
                        null);
                break;
//                Utility.hideSoftKeyboard(mActivity);
//                if (isValidate()) {
//                    request_login();
//                }
//                break;

            case R.id.tv_sign_in:
                IntentUtils.getInstance().navigateToNextActivity(mActivity,
                        null,
                        LoginActivity.class,
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
        layoutBinding.tvSignUp.setOnClickListener(this);
    }

    /**
     * check validation
     *
     * @return
     */
    private boolean isValidate() {
        boolean isValidate = true;

        if (TextUtils.isEmpty(layoutBinding.etEmail.getText().toString().trim())) {
            showSnackbarError(mActivity, getString(R.string.error_enter_email));
            return false;
        } else if (!ValidationUtils.getInstance().isValidEmail(layoutBinding.etEmail.getText().toString().trim())) {
            showSnackbarError(mActivity, getString(R.string.error_enter_valid_email));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.etPassword.getText().toString().trim())) {
            showSnackbarError(mActivity, getString(R.string.error_enter_password));
            return false;
        } else if (!ValidationUtils.getInstance().isValidPassword(layoutBinding.etPassword.getText().toString().trim())) {
            showSnackbarError(mActivity, getString(R.string.error_enter_valid_password_hint));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.etConfirmPassword.getText().toString().trim())) {
            showSnackbarError(mActivity, getString(R.string.error_enter_confirm_password));
            return false;
        } else if (!layoutBinding.etConfirmPassword.getText().toString().trim().equals(layoutBinding.etPassword.getText().toString().trim())) {
            showSnackbarError(mActivity, getString(R.string.error_password_does_not_match));
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
            data.put(Constant.email, layoutBinding.etFullname.getText().toString());
            data.put(Constant.password, layoutBinding.etPhone.getText().toString());
            data.put(Constant.password, layoutBinding.etEmail.getText().toString());
            data.put(Constant.password, layoutBinding.etPassword.getText().toString());
            data.put(Constant.password, layoutBinding.etConfirmPassword.getText().toString());
            data.put(Constant.device_type, Constant.const_device_type);
            data.put(Constant.device_token, PreferenceUtils.getInstance().getFcmToken());
            data.put(Constant.timezone, TimeZone.getDefault().getID());
            data.put(Constant.usertype, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface_server_user().post(
                APIClient.RQ_CREATE_USER,
                data);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {

//                    Solve HEre
                    CreateUserResponse response = (CreateUserResponse) Utility.jsonToPojo(json.body().toString(), CreateUserResponse.class);
                    if (json.isSuccessful() && response.getResponceCode() == ApiConstant.STATUS_SUCCESS) {
                        PreferenceUtils.getInstance().setUser(response.getResponceData());
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(getString(R.string.isFromRegistration), true);
                        showSnackbarSuccess(mActivity, response.getResponceMessage(), new OnCloseListener() {
                            @Override
                            public void onClose() {
                                IntentUtils.getInstance().navigateToNextActivity(mActivity,
                                        OtpActivity.class,
                                        bundle,
                                        true);
                            }
                        });                    } else {
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