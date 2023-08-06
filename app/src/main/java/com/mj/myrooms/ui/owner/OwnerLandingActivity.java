package com.mj.myrooms.ui.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.flarebit.flarebarlib.FlareBar;
import com.flarebit.flarebarlib.Flaretab;
import com.mj.myrooms.R;

import java.util.ArrayList;

public class OwnerLandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_landing);
        final FlareBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setBarBackgroundColor(Color.parseColor("#FFFFFF"));
        ArrayList<Flaretab> tabs = new ArrayList<>();
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.apple),"Inbox","#FFECB3"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.google),"Search","#80DEEA"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.facebook),"Call Log","#B39DDB"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.profile),"Profile","#EF9A9A"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.send),"Settings","#B2DFDB"));

        bottomBar.setTabList(tabs);
        bottomBar.attachTabs(OwnerLandingActivity.this);
    }
}