package com.example.incomeexpense.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.incomeexpense.Fragments.Daily;
import com.example.incomeexpense.Fragments.Monthly;
import com.example.incomeexpense.Fragments.Yearly;

public class homeActivityAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public homeActivityAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Daily daily = new Daily();
                return daily;
            case 1:
                Monthly monthly = new Monthly();
                return monthly;
            case 2:
                Yearly yearly = new Yearly();
                return yearly;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}

