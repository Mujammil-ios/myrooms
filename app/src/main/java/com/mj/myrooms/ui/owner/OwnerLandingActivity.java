package com.mj.myrooms.ui.owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.flarebit.flarebarlib.FlareBar;
import com.flarebit.flarebarlib.Flaretab;
import com.flarebit.flarebarlib.TabEventObject;
import com.mj.myrooms.R;
import com.mj.myrooms.databinding.ActivityLandingPageBinding;
import com.mj.myrooms.databinding.ActivityOwnerLandingBinding;
import com.mj.myrooms.ui.owner.fragment.HomeOwFragment;
import com.mj.myrooms.ui.owner.fragment.InsightOwFragment;
import com.mj.myrooms.ui.owner.fragment.PaymentOwFragment;
import com.mj.myrooms.ui.owner.fragment.ProfileOwFragment;
import com.simform.custombottomnavigation.Model;

import java.util.ArrayList;

public class OwnerLandingActivity extends AppCompatActivity {
    private ActivityOwnerLandingBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_landing);
        final FlareBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setBarBackgroundColor(Color.parseColor("#FFFFFF"));
        ArrayList<Flaretab> tabs = new ArrayList<>();
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.home_icon),"Home","#35C9FF"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.insight_icon),"Insights","#35C9FF"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.add_room_icon),"Add Room","#35C9FF"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.payment_icon),"Payments","#35C9FF"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.profil_icon),"Profile","#35C9FF"));

        bottomBar.setTabList(tabs);
        bottomBar.attachTabs(OwnerLandingActivity.this);
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
                        fragment = new InsightOwFragment();
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

}