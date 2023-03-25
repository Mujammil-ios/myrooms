package com.mj.myrooms;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;


import com.mj.myrooms.listener.OnCloseListener;
import com.mj.myrooms.utils.DialogUtils;
import com.mj.myrooms.utils.Utility;
import com.mj.myrooms.widgets.snakbar.TSnackbar;

import java.io.IOException;

import okhttp3.ResponseBody;

public abstract class BaseFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    private Dialog pdProgress;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * check internet connection and show error dialog if not available
     *
     * @param mActivity
     * @param showErrorDialog
     * @return
     */
    public boolean isNetworkAvailable(Activity mActivity, boolean showErrorDialog) {
        boolean networkStatus = false;
        if (Utility.isNetworkAvailable(mActivity)) {
            networkStatus = true;
        } else {
            // show error
            if (showErrorDialog) {
                DialogUtils.getInstance().showAlertDialog(mActivity,
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
    protected void showProgress(Activity mActivity) {
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
    protected void hideProgress() {
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
     * @param mActivity
     * @return
     */
    public View getRootView(Activity mActivity) {
        View rootview = mActivity.findViewById(R.id.view_root);
        if (rootview == null) {
            rootview = mActivity.getWindow().getDecorView().getRootView();
        }
        return rootview;
    }

    /**
     * get root view
     *
     * @param mActivity
     * @param rootview
     * @return
     */
    public View getRootView(Activity mActivity, View rootview) {
        if (rootview == null) {
            rootview = mActivity.getWindow().getDecorView().getRootView();
        }
        return rootview;
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param rootview
     * @param message
     */
    public void showSnackbarSuccess(Activity mActivity, View rootview, String message) {
        showSnackbarForDialog(rootview, message, ContextCompat.getColor(mActivity, R.color.green));
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param message
     * @param listener
     */
    public void showSnackbarSuccess(Activity mActivity, String message, OnCloseListener listener) {
        View rootview = getRootView(mActivity);
        showSnackbar(rootview, message, ContextCompat.getColor(mActivity, R.color.green), listener);
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param message
     */
    public void showSnackbarSuccess(Activity mActivity, String message) {
        View rootview = getRootView(mActivity);
        showSnackbar(rootview, message, ContextCompat.getColor(mActivity, R.color.green));
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param message
     */
    public void showSnackbarError(Activity mActivity, String message) {
        View rootview = getRootView(mActivity);
        showSnackbar(rootview, message, ContextCompat.getColor(mActivity, R.color.red));
    }

    /**
     * show snackbar
     *
     * @param mActivity
     * @param rootview
     * @param message
     */
    public void showSnackbarError(Activity mActivity, View rootview, String message) {
        showSnackbarForDialog(rootview, message, ContextCompat.getColor(mActivity, R.color.red));
    }

    /**
     * show snackbar
     *
     * @param rootview
     * @param message
     * @param color
     */
    public void showSnackbar(View rootview, String message, int color) {
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
     * @param rootview
     * @param message
     * @param color
     */
    public void showSnackbarForDialog(View rootview, String message, int color) {
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
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    /**
     * show snackbar
     *
     * @param rootview
     * @param message
     * @param color
     * @param listener
     */
    public void showSnackbar(View rootview, String message, int color, final OnCloseListener listener) {
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

//    /**
//     * show exception error
//     *
//     * @param mActivity
//     * @param responseBody
//     */
//    protected void showExceptionError(Activity mActivity, String responseBody) {
//        try {
//            ResponseModel response = (ResponseModel) Utility.jsonToPojo(responseBody, ResponseModel.class);
//            showSnackbarError(mActivity, response.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//            showExceptionError(mActivity);
//        }
//    }
//
//    /**
//     * show exception error
//     *
//     * @param mActivity
//     * @param responseBody
//     */
//    protected void showExceptionError(Activity mActivity, ResponseBody responseBody) {
//        try {
//            ResponseModel response = (ResponseModel) Utility.jsonToPojo(responseBody.string(), ResponseModel.class);
//            showSnackbarError(mActivity, response.getMessage());
//        } catch (IOException e) {
//            e.printStackTrace();
//            showExceptionError(mActivity);
//        } catch (Exception e) {
//            e.printStackTrace();
//            showExceptionError(mActivity);
//        }
//    }
}