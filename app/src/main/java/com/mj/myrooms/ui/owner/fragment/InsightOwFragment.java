package com.mj.myrooms.ui.owner.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mj.myrooms.R;


public class InsightOwFragment extends Fragment {

    public InsightOwFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment hh
        return inflater.inflate(R.layout.fragment_insight_ow, container, false);
    }
}