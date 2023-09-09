package com.mj.myrooms.ui.owner.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.mj.myrooms.BaseFragment;
import com.mj.myrooms.R;
import com.mj.myrooms.databinding.FragmentOwAddRoomDetailBinding;
import com.mj.myrooms.ui.owner.activity.AddCustomerOw;
import com.mj.myrooms.ui.owner.activity.AddRoomOw;
import com.mj.myrooms.utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwAddRoomDetailFragment extends BaseFragment implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity;
    private AddRoomOw parentActivity;
    private FragmentOwAddRoomDetailBinding layoutBinding;

//    private UserAdapter /adapter_customer;
//    private ArrayList<CustomerModel> list_customer;

//    private CityAdapter adapter_city;
//    private ArrayList<CityModel> list_city;
//
//    private CustomerModel object_customer;

    public static OwAddRoomDetailFragment newInstance(int instance, Bundle args) {
        OwAddRoomDetailFragment fragment = new OwAddRoomDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        parentActivity = (AddRoomOw) mActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ow_add_room_detail, container, false);
        return layoutBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initListener();
//        displayData();
    }

    @Override
    public void onClick(View v) {
        Utility.hideSoftKeyboard(mActivity);
        switch (v.getId()) {
            case R.id.home:
                mActivity.onBackPressed();
                break;

            case R.id.tv_customer_name:
//                dialog_customer();
                break;

            case R.id.tv_rental_location:
//                dialog_city();
                break;
        }
    }

    /**
     * initialize listener
     */
    private void initListener() {
        layoutBinding.tvCustomerName.setOnClickListener(this);
        layoutBinding.tvRentalLocation.setOnClickListener(this);
    }

    /**
     * display data
     */
/*
    private void displayData() {
        try {
            if (parentActivity.isUpdate) {
                layoutBinding.tvCustomerName.setEnabled(false);
                layoutBinding.tvRentalLocation.setEnabled(false);

                layoutBinding.tvCustomerName.setText(parentActivity.object_booking.getCompanyName());
                layoutBinding.tvCustomerName.setTag(parentActivity.object_booking.getCcompanyid());

                if (object_customer == null) {
                    object_customer = new CustomerModel();
                    object_customer.setCompanyid(parentActivity.object_booking.getCompanyid());
                    object_customer.setBranchid(parentActivity.object_booking.getCbranchid());
                    object_customer.setUserid(parentActivity.object_booking.getUserid());
                }

                layoutBinding.tvRentalLocation.setText(parentActivity.object_booking.getRentalCity());
                layoutBinding.tvRentalLocation.setTag(parentActivity.object_booking.getRentalLocation());

                layoutBinding.etGuestName.setText(parentActivity.object_booking.getGuestname());
                layoutBinding.etContactno.setText(parentActivity.object_booking.getContactno());
                layoutBinding.etAlternateno.setText(parentActivity.object_booking.getAlternateno());
            } else {
                request_getCustomer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    /**
     * dialog - for customer
     */
/*
    private void dialog_customer() {
        try {
            final Dialog dialog = DialogUtils.getInstance().createDialog(mActivity);
            final DialogDropdownBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.dialog_dropdown, null, false);
            dialog.setContentView(dialogBinding.getRoot());

            // title
            dialogBinding.layoutToolbar.tvTitle.setText(getResources().getString(R.string.customer));

            dialogBinding.etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    if (adapter_customer != null) {
                        adapter_customer.getFilter().filter(charSequence.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            if (list_customer != null && list_customer.size() > 0) {
                dialogBinding.etSearch.setVisibility(View.VISIBLE);
                dialogBinding.rvDropdown.setVisibility(View.VISIBLE);
                dialogBinding.tvNoData.setVisibility(View.GONE);

                fillAdapter_customer(dialog, dialogBinding);
            } else {
                dialogBinding.etSearch.setVisibility(View.GONE);
                dialogBinding.rvDropdown.setVisibility(View.GONE);
                dialogBinding.tvNoData.setVisibility(View.VISIBLE);
            }

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    /**
     * dialog - for city
     */
/*
    private void dialog_city() {
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
    }
*/

    /**
     * fill customer list into adapter
     *
     * @param dialog
     * @param dialogBinding
     */
/*
    private void fillAdapter_customer(Dialog dialog, DialogDropdownBinding dialogBinding) {
        adapter_customer = new UserAdapter(mActivity, new UserAdapter.OnUserClickListener() {
            @Override
            public void onItemClickListener(CustomerModel object) {
                object_customer = object;
                layoutBinding.tvCustomerName.setText(object.getBranchName());
                layoutBinding.tvCustomerName.setTag(object.getUserid());

                dialog.dismiss();
                layoutBinding.tvRentalLocation.setText("");
                layoutBinding.tvRentalLocation.setTag("");
                request_getRentalLocation(object.getUserid());
            }
        });
        adapter_customer.doRefresh(list_customer);

        if (dialogBinding.rvDropdown.getAdapter() == null) {
            dialogBinding.rvDropdown.setHasFixedSize(false);
            dialogBinding.rvDropdown.setLayoutManager(new LinearLayoutManager(mActivity));
            dialogBinding.rvDropdown.setAdapter(adapter_customer);
        }
    }
*/

    /**
     * fill city list into adapter
     */
/*
    private void fillAdapter_city(Dialog dialog, DialogDropdownBinding dialogBinding) {
        adapter_city = new CityAdapter(mActivity, new CityAdapter.OnCityClickListener() {
            @Override
            public void onItemClickListener(CityModel object) {
                layoutBinding.tvRentalLocation.setText(object.getName());
                layoutBinding.tvRentalLocation.setTag(object.getId());

                dialog.dismiss();
                request_getSupplierBranch(object.getId());
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
    public boolean isValidate() {
        boolean isValidate = true;

        if (TextUtils.isEmpty(layoutBinding.tvCustomerName.getText().toString().trim())) {
            showSnackbarError(mActivity, getString(R.string.error_select_customer_name));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.tvRentalLocation.getText().toString().trim())) {
            showSnackbarError(mActivity, getString(R.string.error_enter_rental_location));
            return false;
        }

        if (TextUtils.isEmpty(layoutBinding.etGuestName.getText().toString().trim())) {
            showSnackbarError(mActivity, getString(R.string.error_enter_guest_name));
            return false;
        }

//        parentActivity.object_booking.setCcompanyid(object_customer.getCompanyid());
//        parentActivity.object_booking.setCbranchid(object_customer.getBranchid());
//        parentActivity.object_booking.setUserid(object_customer.getUserid());
//        parentActivity.object_booking.setRentalLocation(layoutBinding.tvRentalLocation.getTag().toString());
//        parentActivity.object_booking.setGuestname(layoutBinding.etGuestName.getText().toString());
//        parentActivity.object_booking.setContactno(layoutBinding.etContactno.getText().toString());
//        parentActivity.object_booking.setAlternateno(layoutBinding.etAlternateno.getText().toString());

        return isValidate;
    }

    /**
     * API call - get customer
     */
/*
    public void request_getCustomer() {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }

        // add request data
        Map<String, String> data = new HashMap<>();
        try {
            data.put(Constant.userid, PreferenceUtils.getInstance().getUser().getUserid());
        } catch (Exception e) {
            e.printStackTrace();
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface_server_usertype().post(
                APIClient.RQ_GET_MANUAL_CUSTOMER,
                data);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {
                    CustomerResponse response = (CustomerResponse) Utility.jsonToPojo(json.body().toString(), CustomerResponse.class);
                    if (json.isSuccessful() && response.getStatus() == ApiConstant.STATUS_SUCCESS) {
                        list_customer = response.getCustomer();
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
*/

    /**
     * API call - get rental location
     *
     * @param id
     */
/*
    public void request_getRentalLocation(String id) {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }

        // add request data
        Map<String, String> data = new HashMap<>();
        try {
            data.put(Constant.userid, PreferenceUtils.getInstance().getUser().getUserid());
            data.put(Constant.customerid, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface_server_usertype().post(
                APIClient.RQ_GET_RENTAl_LOCATION,
                data);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {
                    CityResponse response = (CityResponse) Utility.jsonToPojo(json.body().toString(), CityResponse.class);
                    if (json.isSuccessful() && response.getStatus() == ApiConstant.STATUS_SUCCESS) {
                        list_city = response.getCity();
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
*/

    /**
     * API call - get supplier branch
     *
     * @param id
     */
/*
    public void request_getSupplierBranch(String id) {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }

        // add request data
        Map<String, String> data = new HashMap<>();
        try {
            data.put(Constant.userid, PreferenceUtils.getInstance().getUser().getUserid());
            data.put(Constant.customerid, object_customer.getUserid());
            data.put(Constant.cityid, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface_server_usertype().post(
                APIClient.RQ_GET_SUPPLIER_BRANCH,
                data);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {
                    SupplierBranchResponse response = (SupplierBranchResponse) Utility.jsonToPojo(json.body().toString(), SupplierBranchResponse.class);
                    if (json.isSuccessful() && response.getStatus() == ApiConstant.STATUS_SUCCESS) {
                        parentActivity.list_supplierBranch = response.getSupplier();
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
*/
}
