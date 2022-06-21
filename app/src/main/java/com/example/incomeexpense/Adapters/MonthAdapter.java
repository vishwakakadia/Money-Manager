package com.example.incomeexpense.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.incomeexpense.Fragments.ExpenseMonthAnalysis;
import com.example.incomeexpense.Fragments.IncomeMonthAnalysis;

public class MonthAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MonthAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                IncomeMonthAnalysis incomeMonthAnalysis = new IncomeMonthAnalysis();
                return incomeMonthAnalysis;
            case 1:
                ExpenseMonthAnalysis expenseMonthAnalysis = new ExpenseMonthAnalysis();
                return expenseMonthAnalysis;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
