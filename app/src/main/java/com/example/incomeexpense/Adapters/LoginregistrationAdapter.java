package com.example.incomeexpense.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.incomeexpense.Fragments.Login;
import com.example.incomeexpense.Fragments.Registration;

public class LoginregistrationAdapter  extends FragmentPagerAdapter {

    Context context;
    int totalTabs;
    public LoginregistrationAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                Registration registration = new Registration();
                return registration;
            case 1:
                Login login = new Login();
                return login;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}


