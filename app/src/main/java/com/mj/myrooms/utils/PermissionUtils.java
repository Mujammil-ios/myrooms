package com.mj.myrooms.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import com.mj.myrooms.R;
import com.mj.myrooms.constant.Constant;

public class PermissionUtils {
    // permission result code
    public static final int REQUEST_LOCATION_SETTING = 1000;
    public static final int RESULTCODE_PERMISSION_LOCATION = 1001;
    public static final int RESULTCODE_PERMISSION_CAMERA = 1002;
    public static final int RESULTCODE_PERMISSION_STORAGE = 1003;
    public static final int RESULTCODE_PERMISSION_CONTACT = 1004;
    public static final int RESULTCODE_PERMISSION_CALl = 1005;
    // permission
    private static final String PERM_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String PERM_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String PERM_CAMERA = Manifest.permission.CAMERA;
    private static final String PERM_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String PERM_READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    private static final String PERM_CALL_PHONE = Manifest.permission.CALL_PHONE;
    private static String TAG = "PermissionUtils";
    private static PermissionUtils instance;

    private PermissionUtils() {
    }

    public static PermissionUtils getInstance() {
        if (instance == null) {
            instance = new PermissionUtils();
        }
        return instance;
    }

    /**
     * navigate to app setting - enable permission
     *
     * @param mActivity
     * @param message
     * @param requestCode
     */
    public void navigateToSettingPermissionDialog(final Activity mActivity, String message, final int requestCode) {
        DialogUtils.getInstance().showCustomYesNoAlertDialog(mActivity, false,
                message,
                mActivity.getString(R.string.go_to_setting),
                mActivity.getString(R.string.no),
                new DialogUtils.OnDialogOkCancelButtonClickListener() {
                    @Override
                    public void onOkButtonClick() {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts(Constant.const_package, mActivity.getPackageName(), null);
                        intent.setData(uri);
                        mActivity.startActivityForResult(intent, requestCode);
                    }

                    @Override
                    public void onCancelButtonClick() {

                    }
                });
    }

    /**
     * check location permission is granted, or not
     *
     * @param mActivity
     * @return
     */
    public boolean checkLocationPermission(Activity mActivity) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mActivity, PERM_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkLocationPermission(Activity mActivity, Fragment fragment, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mActivity, PERM_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_COARSE_LOCATION, PERM_FINE_LOCATION}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_COARSE_LOCATION, PERM_FINE_LOCATION}, requestCode);
            }
            return false;
        }
    }

    public boolean checkLocationPermissionNeverAskAgain(Activity mActivity) {
        return checkLocationPermissionNeverAskAgain(mActivity, null);
    }

    public boolean checkLocationPermissionNeverAskAgain(Activity mActivity, Fragment mFragment) {
        if (mFragment != null) {
            return mFragment.shouldShowRequestPermissionRationale(PERM_COARSE_LOCATION)
                    && mFragment.shouldShowRequestPermissionRationale(PERM_FINE_LOCATION);
        } else {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_COARSE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_FINE_LOCATION);
        }
    }

    /**
     * check camera permission is granted, ask if not
     *
     * @param mActivity
     * @param fragment
     * @param requestCode
     * @return
     */
    public boolean checkCameraPermission(Activity mActivity, Fragment fragment, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mActivity, PERM_WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_CAMERA, PERM_WRITE_EXTERNAL_STORAGE}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_CAMERA, PERM_WRITE_EXTERNAL_STORAGE}, requestCode);
            }
            return false;
        }
    }

    public boolean checkCameraPermissionNeverAskAgain(Activity mActivity) {
        return checkCameraPermissionNeverAskAgain(mActivity, null);
    }

    public boolean checkCameraPermissionNeverAskAgain(Activity mActivity, Fragment mFragment) {
        if (mFragment != null) {
            return mFragment.shouldShowRequestPermissionRationale(PERM_CAMERA)
                    && mFragment.shouldShowRequestPermissionRationale(PERM_WRITE_EXTERNAL_STORAGE);
        } else {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_CAMERA)
                    && ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * check storage permission is granted, ask if not
     *
     * @param mActivity
     * @param fragment
     * @param requestCode
     * @return
     */
    public boolean checkStoragePermission(Activity mActivity, Fragment fragment, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_WRITE_EXTERNAL_STORAGE}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_WRITE_EXTERNAL_STORAGE}, requestCode);
            }
            return false;
        }
    }

    public boolean checkStoragePermissionNeverAskAgain(Activity mActivity) {
        return checkStoragePermissionNeverAskAgain(mActivity, null);
    }

    public boolean checkStoragePermissionNeverAskAgain(Activity mActivity, Fragment mFragment) {
        if (mFragment != null) {
            return mFragment.shouldShowRequestPermissionRationale(PERM_WRITE_EXTERNAL_STORAGE);
        } else {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * check contact permission is granted, ask if not
     *
     * @param mActivity
     * @param fragment
     * @param requestCode
     * @return
     */
    public boolean checkContactsPermission(Activity mActivity, Fragment fragment, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_READ_CONTACTS}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_READ_CONTACTS}, requestCode);
            }
            return false;
        }

        /*if (ContextCompat.checkSelfPermission(mActivity, PERM_READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_READ_CONTACTS}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_READ_CONTACTS}, requestCode);
            }
            return false;
        } else {
            return true;
        }*/
    }

    public boolean checkContactsPermissionNeverAskAgain(Activity mActivity) {
        return checkContactsPermissionNeverAskAgain(mActivity, null);
    }

    public boolean checkContactsPermissionNeverAskAgain(Activity mActivity, Fragment mFragment) {
        if (mFragment != null) {
            return mFragment.shouldShowRequestPermissionRationale(PERM_READ_CONTACTS);
        } else {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_READ_CONTACTS);
        }
    }

    /**
     * check call permission is granted, ask if not
     *
     * @param mActivity
     * @param fragment
     * @param requestCode
     * @return
     */
    public boolean checkCallPermission(Activity mActivity, Fragment fragment, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, PERM_CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_CALL_PHONE}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_CALL_PHONE}, requestCode);
            }
            return false;
        }

        /*if (ContextCompat.checkSelfPermission(mActivity, PERM_CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{PERM_CALL_PHONE}, requestCode);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{PERM_CALL_PHONE}, requestCode);
            }
            return false;
        } else {
            return true;
        }*/
    }

    public boolean checkCallPermissionNeverAskAgain(Activity mActivity) {
        return checkCallPermissionNeverAskAgain(mActivity, null);
    }

    public boolean checkCallPermissionNeverAskAgain(Activity mActivity, Fragment mFragment) {
        if (mFragment != null) {
            return mFragment.shouldShowRequestPermissionRationale(PERM_CALL_PHONE);
        } else {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, PERM_CALL_PHONE);
        }
    }
}