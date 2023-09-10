package com.mj.myrooms.ui.owner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.mj.myrooms.BaseAppCompatActivity;
import com.mj.myrooms.R;
import com.mj.myrooms.constant.BundleConstant;
import com.mj.myrooms.constant.Constant;
import com.mj.myrooms.databinding.ActivityAddRoomOwBinding;
import com.mj.myrooms.databinding.GridTabRoomBinding;
import com.mj.myrooms.object.TabModel;
import com.mj.myrooms.ui.owner.fragment.AddRoomCustomOptionFragment;
import com.mj.myrooms.ui.owner.fragment.AddRoomPhotoFragment;
import com.mj.myrooms.ui.owner.fragment.AddRoomPricingFragment;
import com.mj.myrooms.ui.owner.fragment.OwAddRoomDetailFragment;
import com.mj.myrooms.utils.IntentUtils;
import com.mj.myrooms.utils.PreferenceUtils;
import com.mj.myrooms.utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRoomOw extends BaseAppCompatActivity implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = AddRoomOw.this;
    private ActivityAddRoomOwBinding layoutBinding;

    private ViewPagerAdapter adapter_tab;
//    public ArrayList<SupplierBranchModel> list_supplierBranch;
//    public BookingModel object_booking;

    public boolean isUpdate = false;
    private boolean isKeyboardTouch = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_room_ow);

        // get data from intent
        Bundle extras = getIntent().getExtras();
/*        if (extras != null) {
            isUpdate = extras.getBoolean(BundleConstant.EXTRA_IS_UPDATE, false);
            object_booking = extras.getParcelable(BundleConstant.EXTRA_BOOKING);
        }*/

/*
        if (!isUpdate) {
            object_booking = new BookingModel();
        }
*/

        initToolbar();
        initListener();
        initTab();
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

            case R.id.btn_previous:
                Utility.hideSoftKeyboard(mActivity);
                layoutBinding.vpBooking.setCurrentItem(layoutBinding.vpBooking.getCurrentItem() - 1);
                break;

            case R.id.btn_next:
                Utility.hideSoftKeyboard(mActivity);

                Fragment fragment = adapter_tab.list_fragment.get(layoutBinding.tlBooking.getSelectedTabPosition());
                if (fragment instanceof OwAddRoomDetailFragment) {
                    if (((OwAddRoomDetailFragment) fragment).isValidate()) {
                        nextPage();
                    }
                } else if (fragment instanceof AddRoomPricingFragment) {
                    if (((AddRoomPricingFragment) fragment).isValidate()) {
                        nextPage();
                    }
                } else if (fragment instanceof AddRoomPhotoFragment) {
                    if (((AddRoomPhotoFragment) fragment).isValidate()) {
                        nextPage();
                    }
                }else if (fragment instanceof AddRoomCustomOptionFragment) {
                    if (((AddRoomCustomOptionFragment) fragment).isValidate()) {
                        nextPage();
                    }
                }/* else if (fragment instanceof AddOnsSupplierFragment) {
                    if (((AddOnsSupplierFragment) fragment).isValidate()) {
                        if (isUpdate) {
//                            request_updateBooking();
                        } else {
//                            request_createBooking();
                        }
                    }
                }*/
                break;
        }
    }

    /**
     * initialize toolbar
     */
    private void initToolbar() {
        if (isUpdate) {
            layoutBinding.layoutToolbar.tvTitle.setText(getResources().getString(R.string.update_booking));
        } else {
            layoutBinding.layoutToolbar.tvTitle.setText(getResources().getString(R.string.create_booking));
        }

        layoutBinding.layoutToolbar.home.setOnClickListener(this);
    }

    /**
     * initialize listener
     */
    private void initListener() {
        layoutBinding.btnPrevious.setOnClickListener(this);
        layoutBinding.btnNext.setOnClickListener(this);
    }

    /**
     * initialize tab
     */
    private void initTab() {
        adapter_tab = new ViewPagerAdapter(getSupportFragmentManager());
        adapter_tab.addFragment(new OwAddRoomDetailFragment(), new TabModel(getResources().getString(R.string.room_property_details), 1));
        adapter_tab.addFragment(new AddRoomPricingFragment(), new TabModel(getResources().getString(R.string.pricing_landlord_details), 2));
        adapter_tab.addFragment(new AddRoomPhotoFragment(), new TabModel(getResources().getString(R.string.property_details_amenities), 3));
        adapter_tab.addFragment(new AddRoomCustomOptionFragment(), new TabModel(getResources().getString(R.string.optional_detail_custom_option), 4));
//        adapter_tab.addFragment(new AddOnsSupplierFragment(), new TabModel(getResources().getString(R.string.add_one), 5));

        layoutBinding.vpBooking.setAdapter(adapter_tab);
        layoutBinding.tlBooking.setupWithViewPager(layoutBinding.vpBooking);
        layoutBinding.vpBooking.setOffscreenPageLimit(5);

        for (int i = 0; i < layoutBinding.tlBooking.getTabCount(); i++) {
            TabLayout.Tab tab = layoutBinding.tlBooking.getTabAt(i);
            tab.setCustomView(adapter_tab.getCustomTabView(mActivity, i));
        }

        LinearLayout tabStrip = ((LinearLayout) layoutBinding.tlBooking.getChildAt(0));
        tabStrip.setEnabled(true);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
        }

        layoutBinding.tlBooking.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        layoutBinding.btnNext.setText(getResources().getString(R.string.next));

                        layoutBinding.btnPrevious.setVisibility(View.INVISIBLE);
                        layoutBinding.btnNext.setVisibility(View.VISIBLE);
                        break;

                    case 4:
                        layoutBinding.btnNext.setText(getResources().getString(R.string.submit));

                        layoutBinding.btnPrevious.setVisibility(View.VISIBLE);
                        layoutBinding.btnNext.setVisibility(View.VISIBLE);
                        break;

                    default:
                        layoutBinding.btnNext.setText(getResources().getString(R.string.next));

                        layoutBinding.btnPrevious.setVisibility(View.VISIBLE);
                        layoutBinding.btnNext.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * next page
     */
    private void nextPage() {
        layoutBinding.vpBooking.setCurrentItem(layoutBinding.vpBooking.getCurrentItem() + 1);
    }

    /**
     * fill tabs into adapter
     */
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> list_fragment = new ArrayList<>();
        private final List<TabModel> list_data = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }

        @Override
        public int getCount() {
            return list_fragment.size();
        }

        public void addFragment(Fragment fragment, TabModel title) {
            list_fragment.add(fragment);
            list_data.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list_data.get(position).getName();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        public View getCustomTabView(Activity mActivity, int position) {
            GridTabRoomBinding layoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.grid_tab_room, null, false);

            layoutBinding.tvCount.setText(String.valueOf(list_data.get(position).getCount()) + ".");
            layoutBinding.tvName.setText(list_data.get(position).getName());

            return layoutBinding.getRoot();
        }
    }

    /**
     * API call - create booking
     */
    /*public void request_createBooking() {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }

        // add request data
        Map<String, String> data = new HashMap<>();
        try {
            data.put(Constant.userid, PreferenceUtils.getInstance().getUser().getUserid());

            data.put(Constant.ccompanyid, object_booking.getCcompanyid());
            data.put(Constant.cbranchid, object_booking.getCbranchid());
            data.put(Constant.customerid, object_booking.getUserid());
            data.put(Constant.rental_location, object_booking.getRentalLocation());
            data.put(Constant.guestname, object_booking.getGuestname());
            data.put(Constant.contactno, object_booking.getContactno());
            data.put(Constant.alternateno, object_booking.getAlternateno());

            data.put(Constant.pickupdate, object_booking.getPickupdate());
            data.put(Constant.pickuptime, object_booking.getPickuptime());
            data.put(Constant.reportingaddress, object_booking.getReportingaddress());
            data.put(Constant.anddetail, object_booking.getAnddetail());

            data.put(Constant.companyid, object_booking.getCompanyid());
            data.put(Constant.served_by, object_booking.getServedBy());
            data.put(Constant.supplierid, object_booking.getSupplierid());
            data.put(Constant.noofseat, object_booking.getNoofseat());
            data.put(Constant.duty_type, object_booking.getDutyType());
            data.put(Constant.cartypeid, object_booking.getCartypeid());
            data.put(Constant.carcategoryid, object_booking.getCarcategoryid());
            data.put(Constant.payment_method, object_booking.getPaymentMethod());

            data.put(Constant.dropdate, object_booking.getDropdate() != null ? object_booking.getDropdate() : "");
            data.put(Constant.droptime, object_booking.getDroptime() != null ? object_booking.getDroptime() : "");
            data.put(Constant.dropaddress, object_booking.getDropaddress());
            data.put(Constant.departure, object_booking.getDeparture());

            data.put(Constant.specialinstruction, object_booking.getSpecialinstruction());
            data.put(Constant.addemail, object_booking.getAddemail());
            data.put(Constant.addmobileno, object_booking.getAddmobileno());
        } catch (Exception e) {
            e.printStackTrace();
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface_server_usertype().post(
                APIClient.RQ_CREATE_BOOKING,
                data);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {
                    ResponseModel response = (ResponseModel) Utility.jsonToPojo(json.body().toString(), ResponseModel.class);
                    if (json.isSuccessful() && response.getStatus() == ApiConstant.STATUS_SUCCESS) {
                        showSnackbarSuccess(mActivity, response.getMessage(), new OnCloseListener() {
                            @Override
                            public void onClose() {
                                onBackPressed();
                            }
                        });
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

    /**
     * API call - update booking
     */
    /*public void request_updateBooking() {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }

        // add request data
        Map<String, String> data = new HashMap<>();
        try {
            data.put(Constant.userid, PreferenceUtils.getInstance().getUser().getUserid());
            data.put(Constant.bookingid, object_booking.getBookingid());

//            data.put(Constant.ccompanyid, object_booking.getCcompanyid());
//            data.put(Constant.cbranchid, object_booking.getCbranchid());
//            data.put(Constant.customerid, object_booking.getUserid());
//            data.put(Constant.rental_location, object_booking.getRentalLocation());
            data.put(Constant.guestname, object_booking.getGuestname());
            data.put(Constant.contactno, object_booking.getContactno());
            data.put(Constant.alternateno, object_booking.getAlternateno());

//            data.put(Constant.pickupdate, object_booking.getPickupdate());
//            data.put(Constant.pickuptime, object_booking.getPickuptime());
            data.put(Constant.reportingaddress, object_booking.getReportingaddress());
            data.put(Constant.arrival, object_booking.getAnddetail());

//            data.put(Constant.companyid, object_booking.getCompanyid());
//            data.put(Constant.served_by, object_booking.getServedBy());
//            data.put(Constant.supplierid, object_booking.getSupplierid());
            data.put(Constant.noofseat, object_booking.getNoofseat());
//            data.put(Constant.duty_type, object_booking.getDutyType());
//            data.put(Constant.cartypeid, object_booking.getCartypeid());
//            data.put(Constant.carcategoryid, object_booking.getCarcategoryid());
//            data.put(Constant.payment_method, object_booking.getPaymentMethod());

//            data.put(Constant.dropdate, object_booking.getDropdate());
//            data.put(Constant.droptime, object_booking.getDroptime());
            data.put(Constant.dropaddress, object_booking.getDropaddress());
            data.put(Constant.departure, object_booking.getDeparture());

            data.put(Constant.specialinstruction, object_booking.getSpecialinstruction());
            data.put(Constant.addemail, object_booking.getAddemail());
            data.put(Constant.addmobileno, object_booking.getAddmobileno());
        } catch (Exception e) {
            e.printStackTrace();
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface_server_usertype().post(
                APIClient.RQ_UPDATE_BOOKING,
                data);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> json) {
                hideProgress();
                try {
                    ResponseModel response = (ResponseModel) Utility.jsonToPojo(json.body().toString(), ResponseModel.class);
                    if (json.isSuccessful() && response.getStatus() == ApiConstant.STATUS_SUCCESS) {
                        showSnackbarSuccess(mActivity, response.getMessage(), new OnCloseListener() {
                            @Override
                            public void onClose() {
                                onBackPressed();
                            }
                        });
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