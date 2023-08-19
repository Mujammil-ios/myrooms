package com.mj.myrooms.ui.owner.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.mj.myrooms.R;


public class HomeOwFragment extends Fragment {
    private ProgressBar progressBar;
    private TextView textPercentage;
    private TextView textRemainingTime;
    private LottieAnimationView animationView;

    private final int totalDuration = 1000; // Total duration in milliseconds
    private final int updateInterval = 10; // Interval to update progress and text


    public HomeOwFragment() {
        // Required empty public constructor
    }

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
            return 50; // Fill up to half when half the time has passed
        } else {
            return 100; // Fill up to full when the full time has passed
        }
    }

}