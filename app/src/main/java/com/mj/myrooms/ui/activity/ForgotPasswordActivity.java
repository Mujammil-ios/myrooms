package com.mj.myrooms.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
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
import com.mj.myrooms.R;
import com.mj.myrooms.constant.ApiConstant;
import com.mj.myrooms.constant.BundleConstant;
import com.mj.myrooms.constant.Constant;
import com.mj.myrooms.databinding.ActivityForgotPasswordBinding;
import com.mj.myrooms.services.APIClient;
import com.mj.myrooms.utils.IntentUtils;
import com.mj.myrooms.utils.Utility;
import com.mj.myrooms.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = ForgotPasswordActivity.this;
    private ActivityForgotPasswordBinding layoutBinding;

    private boolean isKeyboardTouch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);

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
            case R.id.home:
                Utility.hideSoftKeyboard(mActivity);
                onBackPressed();
                break;

            case R.id.btn_submit:
                Utility.hideSoftKeyboard(mActivity);
                if (isValidate()) {
//                    request_forgotPassword();
                }
                break;
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
        layoutBinding.etMobile.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Utility.hideSoftKeyboard(mActivity);
                    if (isValidate()) {
//                        request_forgotPassword();
                    }
                }
                return false;
            }
        });

        layoutBinding.btnSubmit.setOnClickListener(this);
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

        if (TextUtils.isEmpty(layoutBinding.etMobile.getText().toString().trim())) {
            showSnackbarError(mActivity, getString(R.string.error_enter_mobile_no));
            return false;
        }

        return isValidate;
    }

    /**
     * API call - forgot password
     */
    /*public void request_forgotPassword() {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }

        // add request data
        Map<String, String> data = new HashMap<>();
        try {
            data.put(Constant.email, layoutBinding.etEmail.getText().toString());
            data.put(Constant.mobileno, layoutBinding.etMobile.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface_server_user().post(
                APIClient.RQ_FORGOT_PASSWORD,
                data);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {
                    ResponseModel response = (ResponseModel) Utility.jsonToPojo(json.body().toString(), ResponseModel.class);
                    if (json.isSuccessful() && response.getStatus() == ApiConstant.STATUS_SUCCESS) {
                        // sent user object
                        UserModel object_user = new UserModel();
                        object_user.setEmail(layoutBinding.etEmail.getText().toString());
                        object_user.setMobilenumber(layoutBinding.etMobile.getText().toString());

                        Bundle bundle = new Bundle();
                        bundle.putParcelable(BundleConstant.EXTRA_USER, object_user);

                        // navigate to otp
                        IntentUtils.getInstance().navigateToNextActivity(mActivity,
                                null,
                                OtpActivity.class,
                                bundle,
                                null);
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
    }*/
}
