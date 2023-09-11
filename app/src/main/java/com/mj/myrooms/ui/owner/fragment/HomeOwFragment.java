package com.mj.myrooms.ui.owner.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.mj.myrooms.BaseFragment;
import com.mj.myrooms.R;
import com.mj.myrooms.adapter.HomeOwCustomerAdapter;
import com.mj.myrooms.ui.owner.OwnerLandingActivity;
import com.paginate.Paginate;

import java.util.ArrayList;


public class HomeOwFragment extends BaseFragment implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity;
    private OwnerLandingActivity parentActivity;
    private HomeOwFragment layoutBinding;

//    private UserAdapter adapter_customer;
//    private ArrayList<CustomerModel> list_customer;
//
//    private CustomerModel object_customer;
    private String fromDate, toDate, text;

//    private HomeOwCustomerAdapter.OnHomeOwCustomerClickListener;
//    private PastBookingSupplierAdapter adapter_booking;
//    private ArrayList<BookingModel> list_booking = new ArrayList<>();
//
//    private SupplierPackageAdapter adapter_supplierPackage;
//    private ArrayList<SupplierPackageModel> list_supplierPackage;
//    private SupplierPackageModel object_supplierPackage;
    private boolean isFromPast = false;
    private Paginate paginate;
    boolean loading = false, isAllPageLoaded = false;
    int page = 0;
    private boolean mUserSeen = false;
    private ProgressBar progressBar;
    private TextView textPercentage;
    private TextView textRemainingTime;

    private final int totalDuration = 1000;
    private final int updateInterval = 10;
    /**
     * initialize toolbar
     */


    public HomeOwFragment() {
        // Required empty public constructor
    }


    /**
     * initialize toolbar
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_ow, container, false);

        progressBar = rootView.findViewById(R.id.progressBar);

        CountDownTimer timer = new CountDownTimer(totalDuration, updateInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (((totalDuration - millisUntilFinished) / (float) totalDuration) * 100);
//                int progress = calculateProgress(millisUntilFinished);
                progressBar.setProgress(progress);

                int remainingTime = (int) (millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                // Handle completion
            }
        };


// Decrease the update interval for faster progress updates
        timer.start();


        return rootView;
    }
    private int calculateProgress(long millisUntilFinished) {
        int halfDuration = totalDuration / 2;

        if (millisUntilFinished <= halfDuration) {
            return 50;
        } else {
            return 100;
        }
    }

    @Override
    public void onClick(View view) {

    }
}