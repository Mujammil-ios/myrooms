package com.mj.myrooms;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.mj.myrooms.listener.OnCloseListener;
import com.mj.myrooms.object.wrapper.GetRoleResponse;
import com.mj.myrooms.ui.activity.LoginActivity;
import com.mj.myrooms.utils.DialogUtils;
import com.mj.myrooms.utils.IntentUtils;
import com.mj.myrooms.utils.PreferenceUtils;
import com.mj.myrooms.utils.Utility;
import com.mj.myrooms.widgets.snakbar.TSnackbar;

import java.io.IOException;

import okhttp3.ResponseBody;

public abstract class BaseAppCompatActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = BaseAppCompatActivity.this;

    private Dialog pdProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * check internet connection and show error dialog if not available
     *
     * @param mContext
     * @param showErrorDialog
     * @return
     */
    public boolean isNetworkAvailable(Context mContext, boolean showErrorDialog) {
        boolean networkStatus = false;
        if (Utility.isNetworkAvailable(mContext)) {
            networkStatus = true;
        } else {
            // show error
            if (showErrorDialog) {
                DialogUtils.getInstance().showAlertDialog(mContext,
                        getResources().getString(R.string.app_name),
                        getResources().getString(R.string.error_no_internet));
            }
        }

        return networkStatus;
    }

    /**
     * show progress
     *
     * @param mActivity
     */
    public void showProgress(Activity mActivity) {
        try {
            if (pdProgress == null) {
                pdProgress = DialogUtils.getInstance().createProgressDialog(mActivity);
            }
            pdProgress.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * hide progress
     */
    public void hideProgress() {
        try {
            if (pdProgress != null && pdProgress.isShowing()) {
                pdProgress.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get root view
     *
     * @return
     */
    public View getRootView() {
        View rootview = findViewById(R.id.view_root);
        if (rootview == null) {
            rootview = getWindow().getDecorView().getRootView();
        }
        return rootview;
    }

    /**
     * get root view
     *
     * @param rootview
     * @return
     */
    public View getRootView(View rootview) {
        if (rootview == null) {
            rootview = getWindow().getDecorView().getRootView();
        }
        return rootview;
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param message
     * @param listener
     */
    public void showSnackbarSuccess(Activity mActivity, String message, OnCloseListener listener) {
        View rootview = getRootView();
        showSnackbar(mActivity, rootview, message, ContextCompat.getColor(mActivity, R.color.green), listener);
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param message
     */
    public void showSnackbarSuccess(Activity mActivity, String message) {
        View rootview = getRootView();
        showSnackbar(mActivity, rootview, message, ContextCompat.getColor(mActivity, R.color.green));
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param rootview
     * @param message
     */
    public void showSnackbarSuccess(Activity mActivity, View rootview, String message) {
        showSnackbarForDialog(mActivity, rootview, message, ContextCompat.getColor(mActivity, R.color.green));
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param rootview
     * @param message
     */
    public void showSnackbarError(Activity mActivity, View rootview, String message) {
        showSnackbarForDialog(mActivity, rootview, message, ContextCompat.getColor(mActivity, R.color.red));
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param message
     */
    public void showSnackbarError(Activity mActivity, String message) {
        View rootview = getRootView();
        showSnackbar(mActivity, rootview, message, ContextCompat.getColor(mActivity, R.color.red));
    }


    /**
     * show snackbar
     *
     * @param mActivity
     * @param rootview
     * @param message
     * @param color
     */
    public void showSnackbarForDialog(Activity mActivity, View rootview, String message, int color) {
        try {
            TSnackbar snackbar = TSnackbar.make(rootview, message, TSnackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(color);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
            params.gravity = Gravity.CENTER | Gravity.TOP;
            snackbarView.setLayoutParams(params);
            snackbar.setMaxWidth(rootview.getWidth());
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param rootview
     * @param message
     * @param color
     */
    public void showSnackbar(Activity mActivity, View rootview, String message, int color) {
        try {
            TSnackbar snackbar = TSnackbar.make(rootview, message, TSnackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(color);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();
            params.gravity = Gravity.CENTER | Gravity.TOP;
            snackbarView.setLayoutParams(params);
            snackbar.setMaxWidth(rootview.getWidth());
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param rootview
     * @param message
     * @param color
     * @param listener
     */
    public void showSnackbar(Activity mActivity, View rootview, String message, int color, final OnCloseListener listener) {
        try {
            TSnackbar snackbar = TSnackbar.make(rootview, message, TSnackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(color);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();
            params.gravity = Gravity.CENTER | Gravity.TOP;
            snackbarView.setLayoutParams(params);
            snackbar.setMaxWidth(rootview.getWidth());
            snackbar.setCallback(new TSnackbar.Callback() {
                @Override
                public void onDismissed(TSnackbar snackbar, int event) {
                    super.onDismissed(snackbar, event);
                    listener.onClose();
                }
            });
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    /**
     * show exception error
     *
     * @param mActivity
     */
    protected void showExceptionError(Activity mActivity) {
        try {
            showSnackbarError(mActivity, getResources().getString(R.string.standard_error_408));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * show exception error
     *
     * @param mActivity
     * @param responseBody
     */
    protected void showExceptionError(Activity mActivity,String responseBody) {
        try {
            GetRoleResponse response = (GetRoleResponse) Utility.jsonToPojo(responseBody, GetRoleResponse.class);
            showSnackbarError(mActivity, response.getResponceMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showExceptionError(mActivity);
        }
    }

    /**
     * show exception error
     *
     * @param mActivity
     * @param responseBody
     */
    protected void showExceptionError(Activity mActivity, ResponseBody responseBody) {
        try {
            GetRoleResponse response = (GetRoleResponse) Utility.jsonToPojo(responseBody.string(), GetRoleResponse.class);
            showSnackbarError(mActivity, response.getResponceMessage());
        } catch (IOException e) {
            e.printStackTrace();
            showExceptionError(mActivity);
        } catch (Exception e) {
            e.printStackTrace();
            showExceptionError(mActivity);
        }
    }

    /**
     * logout
     *
     * @param mActivity
     */
    public void logout(Activity mActivity) {
        try {
            DialogUtils.getInstance().showCustomYesNoAlertDialog(mActivity, false,
                    mActivity.getResources().getString(R.string.msg_confirm_logout),
                    mActivity.getResources().getString(R.string.logout),
                    mActivity.getResources().getString(R.string.cancel),
                    new DialogUtils.OnDialogOkCancelButtonClickListener() {
                        @Override
                        public void onOkButtonClick() {
                            PreferenceUtils.getInstance().clearData();
                            IntentUtils.getInstance().navigateToNextActivity(mActivity,
                                    LoginActivity.class,
                                    new Bundle(),
                                    true);
                        }

                        @Override
                        public void onCancelButtonClick() {
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void onClick(View v);
}
