package com.mj.myrooms.ui.owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.customview.widget.Openable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.flarebit.flarebarlib.FlareBar;
import com.flarebit.flarebarlib.Flaretab;
import com.flarebit.flarebarlib.TabEventObject;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mj.myrooms.R;
import com.mj.myrooms.databinding.ActivityOwnerLandingBinding;
import com.mj.myrooms.ui.owner.fragment.AddNewOwFragment;
import com.mj.myrooms.ui.owner.fragment.HomeOwFragment;
import com.mj.myrooms.ui.owner.fragment.InsightOwFragment;
import com.mj.myrooms.ui.owner.fragment.PaymentOwFragment;
import com.mj.myrooms.ui.owner.fragment.ProfileOwFragment;

import java.util.ArrayList;
import java.util.Calendar;

import me.ibrahimsn.lib.SmoothBottomBar;

public class OwnerLandingActivity extends AppCompatActivity {
    private ActivityOwnerLandingBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_landing);
        final FlareBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setBarBackgroundColor(Color.parseColor("#FFFFFF"));
        ArrayList<Flaretab> tabs = new ArrayList<>();
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.home),"Home","#35C9FF"));
//        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.inshigts),"Insights","#35C9FF"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.addroom),"Add New","#35C9FF"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.wallet),"Payments","#35C9FF"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.profile),"Profile","#35C9FF"));

        bottomBar.setTabList(tabs);
        bottomBar.attachTabs(OwnerLandingActivity.this);

        loadFragment(new HomeOwFragment());

        bottomBar.setTabChangedListener(new TabEventObject.TabChangedListener() {
            @Override
            public void onTabChanged(LinearLayout selectedTab, int selectedIndex, int oldIndex) {
                //tabIndex starts from 0 (zero). Example : 4 tabs = last Index - 3
                Fragment fragment = null;
                switch (selectedIndex) {
                    case 0:
                        fragment = new HomeOwFragment();
                        loadFragment(fragment);
                        break;
                    case 1:
                        fragment = new AddNewOwFragment();
                        loadFragment(fragment);
                        break;
                    case 2:
                        fragment = new PaymentOwFragment();
                        loadFragment(fragment);
                        break;
                    case 3:
                        fragment = new ProfileOwFragment();
                        loadFragment(fragment);
                        break;

                }
                Toast.makeText(OwnerLandingActivity.this,"Tab "+ selectedIndex+" Selected.",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flFragment, fragment);
            fragmentTransaction.commit();
        }
    }
    /*
    *//**
     * dialog - filter
     *//*
    private void dialog_filter() {
        try {
            final Dialog dialog = DialogUtils.getInstance().createDialog(mActivity);
            final DialogFilterSupplierBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.dialog_filter_supplier, null, false);
            dialog.setContentView(dialogBinding.getRoot());

            // title
            dialogBinding.layoutToolbar.tvTitle.setText(getResources().getString(R.string.filter));

            if (!TextUtils.isEmpty(fromDate)) {
                dialogBinding.tvFromDate.setText(DateTimeUtils.getInstance().formatDateTime(
                        fromDate,
                        DateTimeUtils.DateFormats.yyyyMMdd.getLabel(),
                        DateTimeUtils.DateFormats.ddMMMyyyy.getLabel()));
                dialogBinding.tvFromDate.setTag(fromDate);
            }
            if (!TextUtils.isEmpty(toDate)) {
                dialogBinding.tvToDate.setText(DateTimeUtils.getInstance().formatDateTime(
                        toDate,
                        DateTimeUtils.DateFormats.yyyyMMdd.getLabel(),
                        DateTimeUtils.DateFormats.ddMMMyyyy.getLabel()));
                dialogBinding.tvToDate.setTag(toDate);
            }

            if (object_customer != null) {
                dialogBinding.tvCustomerName.setText(object_customer.getBranchName());
            }

            dialogBinding.llFromDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtils.getInstance().showDatePickerDialog(mActivity, false, dialogBinding.tvFromDate, new DialogUtils.OnDateTimePickerDialogClickListener() {
                        @Override
                        public void onOkButtonClick(View view, Calendar calendar) {
                            dialogBinding.tvFromDate.setText(DateTimeUtils.getInstance().formatDateTime(calendar.getTime(), DateTimeUtils.DateFormats.ddMMMyyyy.getLabel()));
                            dialogBinding.tvFromDate.setTag(DateTimeUtils.getInstance().formatDateTime(calendar.getTime(), DateFormats.yyyyMMdd.getLabel()));
                        }
                    });
                }
            });

            dialogBinding.llToDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtils.getInstance().showDatePickerDialog(mActivity, false, dialogBinding.tvToDate, new DialogUtils.OnDateTimePickerDialogClickListener() {
                        @Override
                        public void onOkButtonClick(View view, Calendar calendar) {
                            dialogBinding.tvToDate.setText(DateTimeUtils.getInstance().formatDateTime(calendar.getTime(), DateTimeUtils.DateFormats.ddMMMyyyy.getLabel()));
                            dialogBinding.tvToDate.setTag(DateTimeUtils.getInstance().formatDateTime(calendar.getTime(), DateFormats.yyyyMMdd.getLabel()));
                        }
                    });
                }
            });

            dialogBinding.llCompanyName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //dialog_customer(dialogBinding);
                }
            });

            dialogBinding.btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.hideSoftKeyboard(mActivity);
                    fromDate = "";
                    toDate = "";
                    object_customer = null;
                    dialogBinding.tvFromDate.setText("");
                    dialogBinding.tvFromDate.setTag("");
                    dialogBinding.tvToDate.setText("");
                    dialogBinding.tvToDate.setTag("");
                    dialogBinding.tvCustomerName.setText("");
                    dialogBinding.etSearchText.setText("");
                }
            });

            dialogBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.hideSoftKeyboard(mActivity);
                    dialog.dismiss();

                    if (!TextUtils.isEmpty(dialogBinding.tvFromDate.getText().toString())) {
                        fromDate = dialogBinding.tvFromDate.getTag().toString();
                    }
                    if (!TextUtils.isEmpty(dialogBinding.tvToDate.getText().toString())) {
                        toDate = dialogBinding.tvToDate.getTag().toString();
                    }
                    if (!TextUtils.isEmpty(dialogBinding.etSearchText.getText().toString())) {
                        text = dialogBinding.etSearchText.getText().toString();
                    }
                    startLoading(true);
                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
}