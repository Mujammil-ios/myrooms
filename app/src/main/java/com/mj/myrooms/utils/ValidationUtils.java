package com.mj.myrooms.utils;

import android.app.Activity;
import com.google.android.material.textfield.TextInputLayout;
import com.mj.myrooms.R;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
    private static String TAG = "ValidationUtils";
    private static ValidationUtils instance;

    public static final String validate_phone = "^[0,2-9][0-9]{9}$"; // for canada only
    //    public static final String validate_postalcode_ca = "[ABCEGHJKLMNPRSTVXY|abceghjklmnprstvxy][0-9][ABCEGHJKLMNPRSTVWXYZ|abceghjklmnprstvwxyz] ?[0-9][ABCEGHJKLMNPRSTVWXYZ|abceghjklmnprstvwxyz][0-9]";
    public static final String validate_postalcode_ca = "(^[ABCEGHJKLMNPRSTVXY]\\d[ABCEGHJKLMNPRSTVWXYZ]$)|(^[ABCEGHJKLMNPRSTVXY]\\d[ABCEGHJKLMNPRSTVWXYZ]( )?\\d[ABCEGHJKLMNPRSTVWXYZ]\\d$)|(^\\d{5}(-\\d{4})?$)|(^\\d{3,6}?$)";
    public static final String validate_postalcode_us = "^\\d{5}([ \\-]\\d{4})?";

    private ValidationUtils() {
    }

    public static ValidationUtils getInstance() {
        if (instance == null) {
            instance = new ValidationUtils();
        }
        return instance;
    }

    /**
     * validate phone - for country canada
     *
     * @param value
     * @return
     */
    /*public static boolean isValidPhone(String value) {
        boolean isValid = false;
        CharSequence inputStr = value;
        Pattern pattern = Pattern.compile(validate_phone);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }*/

    /**
     * validate phone
     *
     * @param value
     * @return
     */
    public static boolean isValidPhone(String value) {
        /*boolean isValid = false;
        CharSequence inputStr = value;
        if (inputStr.length() < 6 || inputStr.length() > 13) {
            isValid = false;
        } else {
            isValid = true;
        }
        return isValid;*/

        return Patterns.PHONE.matcher(value).matches();
    }

    /**
     * validate postal code
     *
     * @param value
     * @return
     */
    public static boolean isValidPostalCode(String value, String validatePattern) {
        try {
            Pattern pattern = Pattern.compile(validatePattern);
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * postal code  - set max length
     *
     * @param view
     * @param maxLength
     */
    public static void setMaxLength(View view, int maxLength) {
        try {
            if (view instanceof EditText)
                ((EditText) view).setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
            else if (view instanceof TextView)
                ((TextView) view).setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * validate email
     *
     * @param value
     * @return
     */
    public static boolean isValidEmail(String value) {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches();
    }

    /**
     * validate password
     *
     * @param value
     * @return
     */
    public static boolean isValidPassword(String value) {
        if (value.length() < 8) {
            return false;
        }
        if (!value.matches(".*\\d.*")) {
            return false;
        }
        if (!value.matches(".*[A-Z].*")) {
            return false;
        }
        if (!value.matches(".*[a-z].*")) {
            return false;
        }
        if (!value.matches(".*[#?!@$%^&*-].*")) {
            return false;
        }
        return true;
    }

    /**
     * check empty field
     */
    public static class EmptyTextWatcher implements TextWatcher {
        private TextInputLayout textInputLayout;
        private EditText editText;
        private String errorMessage;

        public EmptyTextWatcher(TextInputLayout textInputLayout, EditText editText, String errorMessage) {
            this.textInputLayout = textInputLayout;
            this.editText = editText;
            this.errorMessage = errorMessage;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                textInputLayout.setError(errorMessage);
            } else {
                textInputLayout.setErrorEnabled(false);
            }
        }
    }

    /**
     * check empty field
     */
    public static class EmptyTextViewTextWatcher implements TextWatcher {
        private TextInputLayout textInputLayout;
        private TextView textView;
        private String errorMessage;

        public EmptyTextViewTextWatcher(TextInputLayout textInputLayout, TextView textView, String errorMessage) {
            this.textInputLayout = textInputLayout;
            this.textView = textView;
            this.errorMessage = errorMessage;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            if (TextUtils.isEmpty(textView.getText().toString().trim())) {
                textInputLayout.setError(errorMessage);
            } else {
                textInputLayout.setErrorEnabled(false);
            }
        }
    }

    /**
     * check email is valid or not
     */
    public static class EmailTextWatcher implements TextWatcher {
        private Activity mActivity;
        private TextInputLayout textInputLayout;
        private EditText editText;

        public EmailTextWatcher(Activity mActivity, TextInputLayout textInputLayout, EditText editText) {
            this.mActivity = mActivity;
            this.textInputLayout = textInputLayout;
            this.editText = editText;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                textInputLayout.setError(mActivity.getResources().getString(R.string.error_enter_email));
            }/* else if (!isValidEmail(editText.getText().toString().trim())) {
                textInputLayout.setError(Utility.getLocalizedString(mActivity.getResources().getString(R.string.error_enter_valid_email)));
            }*/ else {
                textInputLayout.setErrorEnabled(false);
            }
        }
    }

    /**
     * check phone valid or not
     */
    public static class PhoneTextWatcher implements TextWatcher {
        private Activity mActivity;
        private TextInputLayout textInputLayout;
        private EditText editText;

        public PhoneTextWatcher(Activity mActivity, TextInputLayout textInputLayout, EditText editText) {
            this.mActivity = mActivity;
            this.textInputLayout = textInputLayout;
            this.editText = editText;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            if (!TextUtils.isEmpty(editText.getText().toString().trim()) &&
                    editText.getText().toString().trim().substring(0, 1).equals("1")) {
                textInputLayout.setError(mActivity.getResources().getString(R.string.error_enter_valid_mobile_no));
            } else if (!isValidPhone(editText.getText().toString().trim())) {
                textInputLayout.setError(mActivity.getResources().getString(R.string.error_enter_valid_mobile_no));
            } else {
                textInputLayout.setErrorEnabled(false);
            }
        }
    }

    /**
     * check postal code digit valid or not
     */
    public static class PostalCodeLengthTextWatcher implements TextWatcher {
        private Activity mActivity;
        private EditText editText;
        private char space = ' ';

        public PostalCodeLengthTextWatcher(Activity mActivity, EditText editText) {
            this.mActivity = mActivity;
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                /*if (s.length() > 0) {
                    // Remove spacing char
                    if ((s.length() % 4) == 0 && !Character.isDigit(s.charAt(0))) {
                        final char c = s.charAt(s.length() - 1);
                        if (space == c) {
                            s.delete(s.length() - 1, s.length());
                        }
                    }
                    // Insert char where needed.
                    if (s.length() > 0 && (s.length() % 4) == 0 && !Character.isDigit(s.charAt(0))) {
                        if (TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                            s.insert(s.length() - 1, String.valueOf(space));
                        }
                    }
                    if (Character.isDigit(s.charAt(0))) {
                        setMaxLength(editText, 5);
                    } else if (s.toString().contains("" + space)) {
                        setMaxLength(editText, 7);
                    } else {
                        setMaxLength(editText, 6);
                    }
                }*/

                if (s.length() > 0) {
                    // Remove spacing char
                    if ((s.length() % 4) == 0 && !Character.isDigit(s.charAt(0))) {
                        final char c = s.charAt(s.length() - 1);
                        if (space == c) {
                            s.delete(s.length() - 1, s.length());
                        }
                    }
                    // Insert char where needed.
                    if (s.length() > 0 && (s.length() % 4) == 0 && !Character.isDigit(s.charAt(0))) {
                        if (TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                            s.insert(s.length() - 1, String.valueOf(space));
                        }
                    }
                    if (s.toString().contains("" + space)) {
                        setMaxLength(editText, 7);
                    } else {
                        setMaxLength(editText, 7);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
