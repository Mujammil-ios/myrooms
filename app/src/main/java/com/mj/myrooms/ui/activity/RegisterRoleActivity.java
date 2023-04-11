package com.mj.myrooms.ui.activity;

import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.google.gson.JsonObject;
import com.mj.myrooms.BaseAppCompatActivity;
import com.mj.myrooms.R;
import com.mj.myrooms.constant.ApiConstant;
import com.mj.myrooms.constant.BundleConstant;
import com.mj.myrooms.constant.Constant;
import com.mj.myrooms.databinding.ActivityRegisterRoleBinding;
import com.mj.myrooms.object.wrapper.GetRoleResponse;
import com.mj.myrooms.services.APIClient;
import com.mj.myrooms.utils.IntentUtils;
import com.mj.myrooms.utils.PreferenceUtils;
import com.mj.myrooms.utils.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterRoleActivity extends BaseAppCompatActivity  implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = RegisterRoleActivity.this;
    private ActivityRegisterRoleBinding layoutBinding;
    boolean isClicked = true;
    private boolean isKeyboardTouch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_role);
        initToolbar();
        initListener();
        request_updateProfile();

    }

    /**
     * initialize toolbar
     */
    private void initToolbar() {

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
        Bundle bundle;
        switch (v.getId()) {

            case R.id.btn_submit:
                Utility.hideSoftKeyboard(mActivity);

                if (isValidate()) {
                    bundle = new Bundle();
                    if (layoutBinding.tvAsCoustomer.isClickable() == isClicked) {
                        bundle.putInt(BundleConstant.EXTRA_USER_TYPE, Constant.user_type_customer);
                    } else if (layoutBinding.tvAsOwner.isClickable() == isClicked) {
                        bundle.putInt(BundleConstant.EXTRA_USER_TYPE, Constant.user_type_owner);
                    } /*else if (layoutBinding.rbDriver.isChecked()) {
                        bundle.putInt(BundleConstant.EXTRA_USER_TYPE, Constant.user_type_driver);
                    }*/
                    IntentUtils.getInstance().navigateToNextActivity(mActivity,
                            null,
                            RegisterAccountActivity.class,
                            bundle,
                            null);
                }
                break;
        }
    }


    /**
     * initialize listener
     */
    private void initListener() {
        layoutBinding.btnSubmit.setOnClickListener(this);
    }

    /**
     * check validation
     *
     * @return
     */
    private boolean isValidate() {
        boolean isValidate = true;

        if (layoutBinding.tvAsOwner.isClickable() == !isClicked) {
            showSnackbarError(mActivity, getResources().getString(R.string.error_select_user_type));
            return false;
        }else if (layoutBinding.tvAsCoustomer.isClickable() == !isClicked){
            showSnackbarError(mActivity, getResources().getString(R.string.error_select_user_type));
            return false;
        }
        return isValidate;

    }
    /**
     * API call - update profile
     */
    public void request_updateProfile() {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }


        showProgress(mActivity);
        Call call = APIClient.appInterface_server_user().get(
                APIClient.RQ_GET_ROLE);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {
                    GetRoleResponse response = (GetRoleResponse) Utility.jsonToPojo(json.body().toString(), GetRoleResponse.class);
                    if (json.isSuccessful() && response.getResponceCode() == ApiConstant.STATUS_SUCCESS) {
                        PreferenceUtils.getInstance().setFirstTime(true);
//                        PreferenceUtils.getInstance().setUser(response.getUser());
//                        object_user = response.getUser();
                        showSnackbarSuccess(mActivity, layoutBinding.getRoot(), response.getResponceMessage());
                    } else {
                        hideProgress();
                        showExceptionError(mActivity, json.body().toString());
                    }
                } catch (Exception e) {
                    hideProgress();
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