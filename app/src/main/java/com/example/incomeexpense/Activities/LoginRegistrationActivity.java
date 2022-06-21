package com.example.incomeexpense.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.incomeexpense.Adapters.LoginregistrationAdapter;
import com.example.incomeexpense.R;
import com.google.android.material.tabs.TabLayout;

public class LoginRegistrationActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout_registration);
        viewPager = findViewById(R.id.viewPager_analysis);

        tabLayout.addTab(tabLayout.newTab().setText("Registration"));
        tabLayout.addTab(tabLayout.newTab().setText("Login"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final LoginregistrationAdapter loginregistrationAdapter = new LoginregistrationAdapter(this, getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(loginregistrationAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
