package com.mj.myrooms.ui.owner.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mj.myrooms.MainActivity;
import com.mj.myrooms.R;
import com.mj.myrooms.databinding.FragmentAddNewOwBinding;
import com.mj.myrooms.ui.owner.activity.AddCustomerOw;
import com.mj.myrooms.ui.owner.activity.AddRoomOw;
import com.mj.myrooms.utils.IntentUtils;


public class AddNewOwFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity;
    private FragmentAddNewOwBinding layoutBinding;

    public static AddNewOwFragment newInstance(int instance, Bundle args) {
        AddNewOwFragment fragment = new AddNewOwFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }
    public AddNewOwFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_ow, container, false);
        return layoutBinding.getRoot();
        // Inflate the layout for this fragment

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = new Bundle();
    layoutBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            IntentUtils.getInstance().navigateToNextActivity(mActivity,
                    AddCustomerOw.class,
                    bundle,
                    true);
        }
    });
        layoutBinding.btnSubmit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.getInstance().navigateToNextActivity(mActivity,
                        AddRoomOw.class,
                        bundle,
                        true);
            }
        });
    }
}