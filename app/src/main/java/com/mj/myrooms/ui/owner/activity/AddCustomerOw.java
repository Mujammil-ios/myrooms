package com.mj.myrooms.ui.owner.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.google.gson.JsonObject;
import com.mj.myrooms.BaseAppCompatActivity;
import com.mj.myrooms.R;
import com.mj.myrooms.constant.Constant;
import com.mj.myrooms.databinding.ActivityAddCustomerOwBinding;
import com.mj.myrooms.databinding.DialogDropdownBinding;
import com.mj.myrooms.services.APIClient;
import com.mj.myrooms.ui.owner.OwnerLandingActivity;
import com.mj.myrooms.utils.DialogUtils;
import com.mj.myrooms.utils.FileUtils;
import com.mj.myrooms.utils.IntentUtils;
import com.mj.myrooms.utils.PermissionUtils;
import com.mj.myrooms.utils.PreferenceUtils;
import com.mj.myrooms.utils.Utility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCustomerOw extends BaseAppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ActivityAddCustomerOwBinding layoutBinding;
    private final Activity mActivity = AddCustomerOw.this;
//    private ArrayList<CountryModel> list_country;
//    private ArrayList<CountryModel> list_country_billing;
//    private CountryAdapter adapter_country;
//    private DropdownAdapter adapter_gender;
//
//    private StateAdapter stateAdapter;
//    private ArrayList<StateModel> list_state;
//    private ArrayList<StateModel> list_state_billing;
    int flag = 0;

//    private CityListAdapter adapter_city;
//    private ArrayList<CityListModel> list_city;
//    private ArrayList<CityListModel> list_city_billing;
    String tvCompanyComposition, selectMSME;
    private String logo_filePath = "", gstdocument_filePath = "", tanDocument_filePath = "", panDocument_filePath = "";
    private boolean isFromRegistration = false;
    private boolean isFromEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_customer_ow);
        // get data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(getString(R.string.isFromRegistration))) {
                isFromRegistration = extras.getBoolean(getString(R.string.isFromRegistration), false);
            }
        }
        initToolbar();
//        setData();
        initListener();
//        initSpinner();
//        request_getCountry();
    }

    private void initSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.tv_msme);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_MSME, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinner_gst_status = (Spinner) findViewById(R.id.tv_gst_status_billing);
        ArrayAdapter<CharSequence> adapter_gst_status = ArrayAdapter.createFromResource(this,
                R.array.array_gst_status, android.R.layout.simple_spinner_item);
        adapter_gst_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gst_status.setAdapter(adapter_gst_status);
        spinner_gst_status.setOnItemSelectedListener(this);
    }

    /**
     * initialize toolbar
     */
    private void initToolbar() {
        layoutBinding.layoutToolbar.tvTitle.setText(getResources().getString(R.string.create_company));
        layoutBinding.layoutToolbar.home.setVisibility(View.VISIBLE);
    }

    public void itemClicked(View v) {
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked()) {
            layoutBinding.etContactName.setText(layoutBinding.etContactNameAdmin.getText().toString());
            layoutBinding.etContactno.setText(layoutBinding.etContactnoAdmin.getText().toString());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                Utility.hideSoftKeyboard(mActivity);
                onBackPressed();
                break;
            case  R.id.ll_dob:
                Utility.hideSoftKeyboard(mActivity);
                DialogUtils.getInstance().showBirthDatePickerDialog(mActivity, layoutBinding.etDob);
                break;
            case R.id.btn_save_company:
                Utility.hideSoftKeyboard(mActivity);
                if (isValidate()) {
                    // request_addCompany();
                    // showSnackbarSuccess(mActivity, layoutBinding.getRoot(), "Success");
                }
//                request_addCompany();
                break;
            case R.id.tv_country:
                Utility.hideSoftKeyboard(mActivity);
//                dialog_country();
                break;
            case R.id.tv_state:
                Utility.hideSoftKeyboard(mActivity);
//                dialog_state();
                break;
            case R.id.tv_city:
                Utility.hideSoftKeyboard(mActivity);
//                dialog_city();
                break;
            case R.id.tv_country_billing:
                Utility.hideSoftKeyboard(mActivity);
//                dialog_country_billing();
                break;
            case R.id.tv_state_billing:
                Utility.hideSoftKeyboard(mActivity);
//                dialog_state_billing();
                break;
            case R.id.tv_city_billing:
                Utility.hideSoftKeyboard(mActivity);
//                dialog_city_billing();
                break;
            case R.id.company_logo_btn:
                Utility.hideSoftKeyboard(mActivity);
                flag = 1;
                openGallery();
                break;
            case R.id.gst_document_btn:
                Utility.hideSoftKeyboard(mActivity);
                flag = 2;
                openGallery();
                break;
            case R.id.tan_document_btn:
                Utility.hideSoftKeyboard(mActivity);
                flag = 3;
                openGallery();
                break;
            case R.id.pan_document_btn:
                Utility.hideSoftKeyboard(mActivity);
                flag = 4;
                openGallery();
                break;

            case R.id.ll_start_date:
                Utility.hideSoftKeyboard(mActivity);
                DialogUtils.getInstance().showBirthDatePickerDialog(mActivity, layoutBinding.etStartDate);
                break;
            case R.id.ll_end_date:
                Utility.hideSoftKeyboard(mActivity);
                DialogUtils.getInstance().showBirthDatePickerDialog(mActivity, layoutBinding.etEndDate);
                break;
        }
    }

    /**
     * initialize listener
     */
    private void initListener() {
        layoutBinding.layoutToolbar.home.setOnClickListener(this);
        layoutBinding.btnSaveCompany.setOnClickListener(this);
        layoutBinding.tvCountry.setOnClickListener(this);
        layoutBinding.llDob.setOnClickListener(this);
        layoutBinding.tvState.setOnClickListener(this);
        layoutBinding.tvCity.setOnClickListener(this);
        layoutBinding.tvCountry.setOnClickListener(this);
        layoutBinding.tvState.setOnClickListener(this);
        layoutBinding.llStartDate.setOnClickListener(this);
        layoutBinding.llEndDate.setOnClickListener(this);
        layoutBinding.tvCity.setOnClickListener(this);
        layoutBinding.companyLogoBtn.setOnClickListener(this);
        layoutBinding.gstDocumentBtn.setOnClickListener(this);
    }


    /*private void dialog_country() {
        try {
            final Dialog dialog = DialogUtils.getInstance().createDialog(mActivity);
            final DialogDropdownBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.dialog_dropdown, null, false);
            dialog.setContentView(dialogBinding.getRoot());

            // title
            dialogBinding.layoutToolbar.tvTitle.setText(getResources().getString(R.string.country));

            dialogBinding.etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    if (adapter_country != null) {
                        adapter_country.getFilter().filter(charSequence.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            if (list_country != null && list_country.size() > 0) {
                dialogBinding.etSearch.setVisibility(View.VISIBLE);
                dialogBinding.rvDropdown.setVisibility(View.VISIBLE);
                dialogBinding.tvNoData.setVisibility(View.GONE);

                fillAdapter_country(dialog, dialogBinding);
            } else {
                dialogBinding.etSearch.setVisibility(View.GONE);
                dialogBinding.rvDropdown.setVisibility(View.GONE);
                dialogBinding.tvNoData.setVisibility(View.VISIBLE);
            }

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * fill country list into adapter
     */
    /*private void fillAdapter_country(Dialog dialog, DialogDropdownBinding dialogBinding) {
        adapter_country = new CountryAdapter(mActivity);
        adapter_country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                layoutBinding.tvCountry.setText(list_country.get(position).getName());
                layoutBinding.tvCountry.setTag(list_country.get(position).getId());
                request_getState();
                dialog.dismiss();
            }
        });
        adapter_country.doRefresh(list_country);

        if (dialogBinding.rvDropdown.getAdapter() == null) {
            dialogBinding.rvDropdown.setHasFixedSize(false);
            dialogBinding.rvDropdown.setLayoutManager(new LinearLayoutManager(mActivity));
            dialogBinding.rvDropdown.setAdapter(adapter_country);
        }
    }*/

    /*private void dialog_country_billing() {
        try {
            final Dialog dialog = DialogUtils.getInstance().createDialog(mActivity);
            final DialogDropdownBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.dialog_dropdown, null, false);
            dialog.setContentView(dialogBinding.getRoot());

            // title
            dialogBinding.layoutToolbar.tvTitle.setText(getResources().getString(R.string.country));

            dialogBinding.etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    if (adapter_country != null) {
                        adapter_country.getFilter().filter(charSequence.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            if (list_country_billing != null && list_country_billing.size() > 0) {
                dialogBinding.etSearch.setVisibility(View.VISIBLE);
                dialogBinding.rvDropdown.setVisibility(View.VISIBLE);
                dialogBinding.tvNoData.setVisibility(View.GONE);

                fillAdapter_country_billing(dialog, dialogBinding);
            } else {
                dialogBinding.etSearch.setVisibility(View.GONE);
                dialogBinding.rvDropdown.setVisibility(View.GONE);
                dialogBinding.tvNoData.setVisibility(View.VISIBLE);
            }

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * fill country list into adapter
     */
    /*private void fillAdapter_country_billing(Dialog dialog, DialogDropdownBinding dialogBinding) {
        adapter_country = new CountryAdapter(mActivity);
        adapter_country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                layoutBinding.tvCountryBilling.setText(list_country_billing.get(position).getName());
                layoutBinding.tvCountryBilling.setTag(list_country_billing.get(position).getId());
                request_getState_billing();
                dialog.dismiss();
            }
        });
        adapter_country.doRefresh(list_country_billing);

        if (dialogBinding.rvDropdown.getAdapter() == null) {
            dialogBinding.rvDropdown.setHasFixedSize(false);
            dialogBinding.rvDropdown.setLayoutManager(new LinearLayoutManager(mActivity));
            dialogBinding.rvDropdown.setAdapter(adapter_country);
        }
    }*/


    /*private void dialog_state() {
        try {
            final Dialog dialog = DialogUtils.getInstance().createDialog(mActivity);
            final DialogDropdownBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.dialog_dropdown, null, false);
            dialog.setContentView(dialogBinding.getRoot());

            // title
            dialogBinding.layoutToolbar.tvTitle.setText(getResources().getString(R.string.state));

            dialogBinding.etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    if (stateAdapter != null) {
                        stateAdapter.getFilter().filter(charSequence.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            if (list_state != null && list_state.size() > 0) {
                dialogBinding.etSearch.setVisibility(View.VISIBLE);
                dialogBinding.rvDropdown.setVisibility(View.VISIBLE);
                dialogBinding.tvNoData.setVisibility(View.GONE);

                fillAdapter_state(dialog, dialogBinding);
            } else {
                dialogBinding.etSearch.setVisibility(View.GONE);
                dialogBinding.rvDropdown.setVisibility(View.GONE);
                dialogBinding.tvNoData.setVisibility(View.VISIBLE);
            }

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*private void dialog_state_billing() {
        try {
            final Dialog dialog = DialogUtils.getInstance().createDialog(mActivity);
            final DialogDropdownBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.dialog_dropdown, null, false);
            dialog.setContentView(dialogBinding.getRoot());

            // title
            dialogBinding.layoutToolbar.tvTitle.setText(getResources().getString(R.string.state));

            dialogBinding.etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    if (stateAdapter != null) {
                        stateAdapter.getFilter().filter(charSequence.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            if (list_state_billing != null && list_state_billing.size() > 0) {
                dialogBinding.etSearch.setVisibility(View.VISIBLE);
                dialogBinding.rvDropdown.setVisibility(View.VISIBLE);
                dialogBinding.tvNoData.setVisibility(View.GONE);

                fillAdapter_state_billing(dialog, dialogBinding);
            } else {
                dialogBinding.etSearch.setVisibility(View.GONE);
                dialogBinding.rvDropdown.setVisibility(View.GONE);
                dialogBinding.tvNoData.setVisibility(View.VISIBLE);
            }

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    /**
     * fill country list into adapter
     */
    /*private void fillAdapter_state(Dialog dialog, DialogDropdownBinding dialogBinding) {
        stateAdapter = new StateAdapter(mActivity, new StateAdapter.OnSateClickListener() {
            @Override
            public void onItemClickListener(StateModel object) {
                layoutBinding.tvState.setText(object.getName());
                layoutBinding.tvState.setTag(object.getId());
                request_getCity(object.getId());
                dialog.dismiss();
            }
        });
        stateAdapter.doRefresh(list_state);

        if (dialogBinding.rvDropdown.getAdapter() == null) {
            dialogBinding.rvDropdown.setHasFixedSize(false);
            dialogBinding.rvDropdown.setLayoutManager(new LinearLayoutManager(mActivity));
            dialogBinding.rvDropdown.setAdapter(stateAdapter);
        }
    }*/

    /**
     * fill country list into adapter
     */
    /*private void fillAdapter_state_billing(Dialog dialog, DialogDropdownBinding dialogBinding) {
        stateAdapter = new StateAdapter(mActivity, new StateAdapter.OnSateClickListener() {
            @Override
            public void onItemClickListener(StateModel object) {
                layoutBinding.tvStateBilling.setText(object.getName());
                layoutBinding.tvStateBilling.setTag(object.getId());
                request_getCity_billing(object.getId());
                dialog.dismiss();
            }
        });
        stateAdapter.doRefresh(list_state_billing);

        if (dialogBinding.rvDropdown.getAdapter() == null) {
            dialogBinding.rvDropdown.setHasFixedSize(false);
            dialogBinding.rvDropdown.setLayoutManager(new LinearLayoutManager(mActivity));
            dialogBinding.rvDropdown.setAdapter(stateAdapter);
        }
    }*/

    /**
     * dialog - for city
     */
    /*private void dialog_city() {
        try {
            final Dialog dialog = DialogUtils.getInstance().createDialog(mActivity);
            final DialogDropdownBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.dialog_dropdown, null, false);
            dialog.setContentView(dialogBinding.getRoot());

            // title
            dialogBinding.layoutToolbar.tvTitle.setText(getResources().getString(R.string.city));

            dialogBinding.etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    if (adapter_city != null) {
                        adapter_city.getFilter().filter(charSequence.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            if (list_city != null && list_city.size() > 0) {
                dialogBinding.etSearch.setVisibility(View.VISIBLE);
                dialogBinding.rvDropdown.setVisibility(View.VISIBLE);
                dialogBinding.tvNoData.setVisibility(View.GONE);

                fillAdapter_city(dialog, dialogBinding);
            } else {
                dialogBinding.etSearch.setVisibility(View.GONE);
                dialogBinding.rvDropdown.setVisibility(View.GONE);
                dialogBinding.tvNoData.setVisibility(View.VISIBLE);
            }

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * dialog - for city
     */
    /*private void dialog_city_billing() {
        try {
            final Dialog dialog = DialogUtils.getInstance().createDialog(mActivity);
            final DialogDropdownBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.dialog_dropdown, null, false);
            dialog.setContentView(dialogBinding.getRoot());

            // title
            dialogBinding.layoutToolbar.tvTitle.setText(getResources().getString(R.string.city));

            dialogBinding.etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    if (adapter_city != null) {
                        adapter_city.getFilter().filter(charSequence.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            if (list_city_billing != null && list_city_billing.size() > 0) {
                dialogBinding.etSearch.setVisibility(View.VISIBLE);
                dialogBinding.rvDropdown.setVisibility(View.VISIBLE);
                dialogBinding.tvNoData.setVisibility(View.GONE);

                fillAdapter_city_billing(dialog, dialogBinding);
            } else {
                dialogBinding.etSearch.setVisibility(View.GONE);
                dialogBinding.rvDropdown.setVisibility(View.GONE);
                dialogBinding.tvNoData.setVisibility(View.VISIBLE);
            }

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*private void fillAdapter_city_billing(Dialog dialog, DialogDropdownBinding dialogBinding) {
        adapter_city = new CityListAdapter(mActivity, new CityListAdapter.OnCityListClickListener() {
            @Override
            public void onItemClickListener(CityListModel object) {
                layoutBinding.tvCityBilling.setText(object.getName());
                layoutBinding.tvCityBilling.setTag(object.getId());
                dialog.dismiss();
            }
        });
        adapter_city.doRefresh(list_city_billing);

        if (dialogBinding.rvDropdown.getAdapter() == null) {
            dialogBinding.rvDropdown.setHasFixedSize(false);
            dialogBinding.rvDropdown.setLayoutManager(new LinearLayoutManager(mActivity));
            dialogBinding.rvDropdown.setAdapter(adapter_city);
        }
    }*/


    /*private void fillAdapter_city(Dialog dialog, DialogDropdownBinding dialogBinding) {
        adapter_city = new CityListAdapter(mActivity, new CityListAdapter.OnCityListClickListener() {
            @Override
            public void onItemClickListener(CityListModel object) {
                layoutBinding.tvCity.setText(object.getName());
                layoutBinding.tvCity.setTag(object.getId());
                dialog.dismiss();
            }
        });
        adapter_city.doRefresh(list_city);

        if (dialogBinding.rvDropdown.getAdapter() == null) {
            dialogBinding.rvDropdown.setHasFixedSize(false);
            dialogBinding.rvDropdown.setLayoutManager(new LinearLayoutManager(mActivity));
            dialogBinding.rvDropdown.setAdapter(adapter_city);
        }
    }
*/
    /**
     * check validation
     *
     * @return
     */
    private boolean isValidate() {
        boolean isValidate = true;

        if (TextUtils.isEmpty(layoutBinding.etCompanyLegalName.getText().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_enter_company_legal_name));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.etCompanyName.getText().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_enter_company_name));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.tvCompanyComposition.getSelectedItem().toString())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_select_company_composition));
            return false;
        }

        if (layoutBinding.tvCompanyComposition.getSelectedItem().equals("Select Company Composition")) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_select_company_composition));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.tvMsme.getSelectedItem().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_select_msme));
            return false;
        }

        if (layoutBinding.tvMsme.getSelectedItem().equals("Select MSME")) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_select_msme));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.etAddressLine1.getText().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_enter_address));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.tvCountry.getText().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_select_country));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.tvState.getText().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_select_state));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.tvCity.getText().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_select_city));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.etPincode.getText().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_enter_pincode));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.etAddressLine1.getText().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_enter_address));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.tvCountry.getText().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_select_country));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.tvState.getText().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_select_state));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.tvCity.getText().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_select_city));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.etPincode.getText().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_enter_pincode));
            return false;
        }

        /*if (TextUtils.isEmpty(layoutBinding.tvGstDocumentFileName.getSelectedItem().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_select_gst_status));
            return false;
        }*/

        if (TextUtils.isEmpty(layoutBinding.etGstNumberBilling.getText().toString().trim())) {
            showSnackbarError(mActivity, layoutBinding.getRoot(), getString(R.string.error_enter_gst_number));
            return false;
        }

        return isValidate;
    }

    /**
     * API call - get country
     */
    /*public void request_getCountry() {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface_server_user().get(
                APIClient.RQ_GET_COUNTRY);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {
                    CountryResponse response = (CountryResponse) Utility.jsonToPojo(json.body().toString(), CountryResponse.class);
                    if (json.isSuccessful() && response.getStatus() == ApiConstant.STATUS_SUCCESS) {
                        list_country = response.getCountry();
                        list_country_billing = response.getCountry();
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

    /**
     * API call - get state
     */
    /*public void request_getState() {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }

        Map<String, String> data = new HashMap<>();
        try {
            data.put(Constant.countryid, layoutBinding.tvCountry.getTag().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface_server_usertype().post(
                APIClient.RQ_GET_STATE, data);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {
                    GetStateResponse response = (GetStateResponse) Utility.jsonToPojo(json.body().toString(), GetStateResponse.class);
                    if (json.isSuccessful() && response.getStatus() == ApiConstant.STATUS_SUCCESS) {
                        list_state = (ArrayList<StateModel>) response.getData();
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

    /**
     * API call - get state for billing
     */
    /*public void request_getState_billing() {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }

        Map<String, String> data = new HashMap<>();
        try {
            data.put(Constant.countryid, layoutBinding.tvCountryBilling.getTag().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface_server_usertype().post(
                APIClient.RQ_GET_STATE, data);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {
                    GetStateResponse response = (GetStateResponse) Utility.jsonToPojo(json.body().toString(), GetStateResponse.class);
                    if (json.isSuccessful() && response.getStatus() == ApiConstant.STATUS_SUCCESS) {
                        list_state_billing = (ArrayList<StateModel>) response.getData();
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

    /**
     * API call - get City
     */
    /*public void request_getCity_billing(String id) {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }

        // add request data
        Map<String, String> data = new HashMap<>();
        try {
            data.put(Constant.stateid, layoutBinding.tvStateBilling.getTag().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface_server_usertype().post(
                APIClient.RQ_GET_CITY,
                data);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {
                    GetCityResponse response = (GetCityResponse) Utility.jsonToPojo(json.body().toString(), GetCityResponse.class);
                    if (json.isSuccessful() && response.getStatus() == ApiConstant.STATUS_SUCCESS) {
                        list_city_billing = (ArrayList<CityListModel>) response.getData();
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

    /**
     * API call - get City
     */
    /*public void request_getCity(String id) {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }

        // add request data
        Map<String, String> data = new HashMap<>();
        try {
            data.put(Constant.stateid, layoutBinding.tvState.getTag().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface_server_usertype().post(
                APIClient.RQ_GET_CITY,
                data);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {
                    GetCityResponse response = (GetCityResponse) Utility.jsonToPojo(json.body().toString(), GetCityResponse.class);
                    if (json.isSuccessful() && response.getStatus() == ApiConstant.STATUS_SUCCESS) {
                        list_city = (ArrayList<CityListModel>) response.getData();
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


    /**
     * API call - addCompany
     */
    /*public void request_addCompany() {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }

        // add file
        MultipartBody.Part logo_image = null;
        if (!TextUtils.isEmpty(logo_filePath)) {
            logo_image = MultipartBody.Part.createFormData(Constant.logo, new File(logo_filePath).getName(),
                    RequestBody.create(MediaType.parse("image/*"), new File(logo_filePath)));
        }

        MultipartBody.Part gstDocument_image = null;
        if (!TextUtils.isEmpty(gstdocument_filePath)) {
            gstDocument_image = MultipartBody.Part.createFormData(Constant.gstdocument, new File(gstdocument_filePath).getName(),
                    RequestBody.create(MediaType.parse("image/*"), new File(gstdocument_filePath)));
        }

        MultipartBody.Part tanDocument_image = null;
        if (!TextUtils.isEmpty(tanDocument_filePath)) {
            tanDocument_image = MultipartBody.Part.createFormData(Constant.tandocument, new File(tanDocument_filePath).getName(),
                    RequestBody.create(MediaType.parse("image/*"), new File(tanDocument_filePath)));
        }

        MultipartBody.Part panDocument_image = null;
        if (!TextUtils.isEmpty(panDocument_filePath)) {
            panDocument_image = MultipartBody.Part.createFormData(Constant.pandocument, new File(panDocument_filePath).getName(),
                    RequestBody.create(MediaType.parse("image/*"), new File(panDocument_filePath)));
        }

        // add request data
        Map<String, RequestBody> data = new HashMap<>();
        try {
            data.put(Constant.userid, APIClient.toRequestBody(PreferenceUtils.getInstance().getUser().getUserid()));
            data.put(Constant.legalname, APIClient.toRequestBody(layoutBinding.etCompanyLegalName.getText().toString()));
            data.put(Constant.name, APIClient.toRequestBody(layoutBinding.etCompanyName.getText().toString()));
            data.put(Constant.sac_code, APIClient.toRequestBody(layoutBinding.etSacCode.getText().toString()));
            data.put(Constant.composition, APIClient.toRequestBody(layoutBinding.tvCompanyComposition.getSelectedItem().toString()));
            data.put(Constant.msme, APIClient.toRequestBody(layoutBinding.tvMsme.getSelectedItem().toString()));
            data.put(Constant.msme_number, APIClient.toRequestBody(layoutBinding.etMsmeNumber.getText().toString()));
            data.put(Constant.raddress1, APIClient.toRequestBody(layoutBinding.etAddressLine1.getText().toString()));
            data.put(Constant.raddress2, APIClient.toRequestBody(layoutBinding.etAddressLine2.getText().toString()));
            data.put(Constant.rcountryid, APIClient.toRequestBody(layoutBinding.tvCountry.getTag().toString()));
            data.put(Constant.rstateid, APIClient.toRequestBody(layoutBinding.tvState.getTag().toString()));
            data.put(Constant.rcityid, APIClient.toRequestBody(layoutBinding.tvCity.getTag().toString()));
            data.put(Constant.rpincode, APIClient.toRequestBody(layoutBinding.etPincode.getText().toString()));
            data.put(Constant.baddress1, APIClient.toRequestBody(layoutBinding.etAddressLine1Billing.getText().toString()));
            data.put(Constant.baddress2, APIClient.toRequestBody(layoutBinding.etAddressLine2Billing.getText().toString()));
            data.put(Constant.bcountryid, APIClient.toRequestBody(layoutBinding.tvCountryBilling.getTag().toString()));
            data.put(Constant.bstateid, APIClient.toRequestBody(layoutBinding.tvStateBilling.getTag().toString()));
            data.put(Constant.bcityid, APIClient.toRequestBody(layoutBinding.tvCityBilling.getTag().toString()));
            data.put(Constant.bpincode, APIClient.toRequestBody(layoutBinding.etPincodeBilling.getText().toString()));
            data.put(Constant.gststatus, APIClient.toRequestBody(layoutBinding.tvGstStatusBilling.getSelectedItem().toString()));
            data.put(Constant.gstno, APIClient.toRequestBody(layoutBinding.etGstNumberBilling.getText().toString()));
            data.put(Constant.tanno, APIClient.toRequestBody(layoutBinding.etTanBilling.getText().toString()));
            data.put(Constant.pan_number, APIClient.toRequestBody(layoutBinding.etPanNumberBilling.getText().toString()));
            data.put(Constant.invoice_prefix, APIClient.toRequestBody(layoutBinding.etInvoicePrefixBilling.getText().toString()));
            data.put(Constant.odesk_number, APIClient.toRequestBody(layoutBinding.etOperationDeskNumbersBilling.getText().toString()));
            data.put(Constant.odesk_emails, APIClient.toRequestBody(layoutBinding.etOperationDeskEmailBilling.getText().toString()));
            data.put(Constant.contactname, APIClient.toRequestBody(layoutBinding.etContactName.getText().toString()));
            data.put(Constant.contactno, APIClient.toRequestBody(layoutBinding.etContactnoAdmin.getText().toString()));
            data.put(Constant.email, APIClient.toRequestBody(layoutBinding.etEmailAdmin.getText().toString()));
            data.put(Constant.fcontactname, APIClient.toRequestBody(layoutBinding.etContactName.getText().toString()));
            data.put(Constant.fcontactno, APIClient.toRequestBody(layoutBinding.etContactno.getText().toString()));
            data.put(Constant.femail, APIClient.toRequestBody(layoutBinding.etEmail.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface_server_usertype().addCompany(APIClient.RQ_ADD_COMPANY, data,
                !TextUtils.isEmpty(logo_filePath) ? logo_image : null,
                !TextUtils.isEmpty(gstdocument_filePath) ? gstDocument_image : null,
                !TextUtils.isEmpty(tanDocument_filePath) ? tanDocument_image : null,
                !TextUtils.isEmpty(panDocument_filePath) ? panDocument_image : null);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {
                    AddCompanyResponse response = (AddCompanyResponse) Utility.jsonToPojo(json.body().toString(), AddCompanyResponse.class);
                    if (json.isSuccessful() && response.getStatus() == ApiConstant.STATUS_SUCCESS) {
                        showSnackbarSuccess(mActivity, layoutBinding.getRoot(), response.getMessage());
                        if (isFromRegistration) {
                            IntentUtils.getInstance().navigateToNextActivity(mActivity,
                                    DashboardSupplierActivity.class,
                                    new Bundle(),
                                    true);
                        } else {
                            finish();
                        }
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        selectMSME = adapterView.getItemAtPosition(position).toString();
        if (selectMSME.equals("No")) {
//            layoutBinding.llMsmeNumber.setVisibility(View.GONE);
        } else {
//            layoutBinding.llMsmeNumber.setVisibility(View.VISIBLE);
        }
        if (selectMSME.equals("Unregistered")) {
            layoutBinding.llGstNumber.setVisibility(View.GONE);
        } else {
            layoutBinding.llGstNumber.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * open gallery
     */
    private void openGallery() {
        if (PermissionUtils.getInstance().checkCameraPermission(mActivity, null, PermissionUtils.RESULTCODE_PERMISSION_CAMERA)) {
            try {
                Intent intent = FileUtils.getInstance().intentForGallery();
                startActivityForResult(intent, IntentUtils.RESULTCODE_GALLERY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntentUtils.RESULTCODE_GALLERY:
                try {
                    Uri uri = data.getData();
               /*     CropImage.activity().setAllowRotation(false);
                    CropImage.activity().setAllowFlipping(false);
                    CropImage.activity(uri)
                            .start(this);*/
                    String path = FileUtils.getInstance().getPath(mActivity, uri);
                    if (flag == 1) {
                        logo_filePath = path;
                        layoutBinding.tvCompanyLogoFileName.setText(path);
                    } else if (flag == 2) {
                        gstdocument_filePath = path;
                        layoutBinding.tvGstDocumentFileName.setText(path);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        DialogUtils.getInstance().showCustomYesNoAlertDialog(mActivity, false,
                mActivity.getResources().getString(R.string.msg_confirm_dicard_save),
                mActivity.getResources().getString(R.string.discard),
                mActivity.getResources().getString(R.string.cancel),
                new DialogUtils.OnDialogOkCancelButtonClickListener() {
                    @Override
                    public void onOkButtonClick() {
                        AddCustomerOw.super.onBackPressed();
                        if (isFromRegistration) {
                            IntentUtils.getInstance().navigateToNextActivity(mActivity,
                                    OwnerLandingActivity.class,
                                    new Bundle(),
                                    true);
                        } else {
                            finish();
                        }
                    }

                    @Override
                    public void onCancelButtonClick() {
                    }
                });
    }
}