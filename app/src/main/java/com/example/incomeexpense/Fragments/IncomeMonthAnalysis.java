package com.example.incomeexpense.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.incomeexpense.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IncomeMonthAnalysis extends Fragment {

    Spinner spinner;
    private FirebaseAuth mAuth;
    private DatabaseReference incomeExpense;
    private DatabaseReference personal;
    private String onlineUserId = "";
    PieChart pieChart;
    String  coupons;
    String deposits;
    String saving;
    String salary;
    String grants;
    String others;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income_month_analysis, container, false);

        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        incomeExpense = FirebaseDatabase.getInstance().getReference("incomeExpense").child(onlineUserId);
        personal = FirebaseDatabase.getInstance().getReference("personal").child(onlineUserId);

        spinner = view.findViewById(R.id.month_select);
        pieChart = view.findViewById(R.id.pieChart_view);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.monthSelect, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int currunt_month  = spinner.getSelectedItemPosition() + 1;
                coupons = "Coupons" + currunt_month + "income";
                deposits = "Deposits" + currunt_month + "income";
                grants = "Grants" + currunt_month + "income";
                others = "others" + currunt_month + "income";
                salary = "Salary" + currunt_month + "income";
                saving = "Saving" + currunt_month + "income";

                gettotalCoupons(coupons);
                gettotalDeposit(deposits);
                gettotalGrants(grants);
                gettotalSalary(salary);
                gettotalSaving(saving);
                gettotalOthetrs(others);

                initPieChart();
                loadGraph();


            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return view;
    }


    private void initPieChart() {
        pieChart.setUsePercentValues(true);
//        pieChart.getDescription().setEnabled(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(true);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setRotationAngle(0);
        pieChart.setDrawSliceText(false);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        pieChart.setHoleColor(Color.parseColor("#ffffff"));
    }


private  void  loadGraph(){
        personal.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int coupon_sum;
                    if(snapshot.hasChild("monthcoupon")){
                        coupon_sum = Integer.parseInt(snapshot.child("monthcoupon").getValue().toString());
                    }
                    else {
                        coupon_sum = 0;
                    }

                    int deposite_sum;
                    if(snapshot.hasChild("monthDeposit")){
                        deposite_sum = Integer.parseInt(snapshot.child("monthDeposit").getValue().toString());
                    }
                    else {
                        deposite_sum = 0;
                    }

                    int grant_sum;
                    if(snapshot.hasChild("mothGrants")){
                        grant_sum = Integer.parseInt(snapshot.child("mothGrants").getValue().toString());
                    }
                    else {
                        grant_sum = 0;
                    }

                    int salary_sum;
                    if(snapshot.hasChild("monthSalary")){
                        salary_sum = Integer.parseInt(snapshot.child("monthSalary").getValue().toString());
                    }
                    else {
                        salary_sum = 0;
                    }

                    int saving_sum;
                    if(snapshot.hasChild("monthSaving")){
                        saving_sum = Integer.parseInt(snapshot.child("monthSaving").getValue().toString());
                    }
                    else {
                        saving_sum = 0;
                    }
                    int others_sum;
                    if(snapshot.hasChild("monthOther")){
                        others_sum = Integer.parseInt(snapshot.child("monthOther").getValue().toString());
                    }
                    else {
                        others_sum = 0;
                    }

                    ArrayList<PieEntry> pieEntries = new ArrayList<>();
                    String label = "type";

                    Map<String, Integer> typeAmountMap = new HashMap<>();
                    typeAmountMap.put("Coupons",coupon_sum);
                    typeAmountMap.put("Deposite",deposite_sum);
                    typeAmountMap.put("Grants", grant_sum);
                    typeAmountMap.put("Salary", salary_sum);
                    typeAmountMap.put("Saving", saving_sum);
                    typeAmountMap.put("others", others_sum);

                    ArrayList<Integer> colors = new ArrayList<>();
                    colors.add(Color.parseColor("#304567"));
                    colors.add(Color.parseColor("#8f9305"));
                    colors.add(Color.parseColor("#476567"));
                    colors.add(Color.parseColor("#890567"));
                    colors.add(Color.parseColor("#a35567"));
                    colors.add(Color.parseColor("#ff5f67"));
                    colors.add(Color.parseColor("#3ca567"));




                    for (String type : typeAmountMap.keySet()) {
                        pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
                    }


                    PieDataSet pieDataSet = new PieDataSet(pieEntries, label);
                    pieDataSet.setValueTextSize(12f);
                    pieDataSet.setColors(colors);
                    pieDataSet.setSliceSpace(3f);
                    PieData pieData = new PieData(pieDataSet);
                    pieDataSet.setValueTextColor(Color.WHITE);
                    pieData.setValueFormatter(new PercentFormatter());
                    pieData.setDrawValues(true);

                    pieChart.setData(pieData);
                    pieChart.invalidate();
                    pieData.setValueFormatter(new PercentFormatter());
                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

}

public  void gettotalCoupons(String coupons) {


    incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(coupons).addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.d("LLL_income_month-->", String.valueOf(snapshot.getValue()));
            if (snapshot.exists()) {
                int amounttotal = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    amounttotal += pTotal;
                    Log.d("LLL__amount-->", String.valueOf(amounttotal));
                }
                personal.child("monthcoupon").setValue(amounttotal);


            } else {
                personal.child("monthcoupon").setValue(0);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}

public void gettotalDeposit(String deposits) {
    incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(deposits).addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.d("LLL-->", String.valueOf(snapshot.getValue()));
            if (snapshot.exists()) {
                int amounttotal = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    amounttotal += pTotal;
                    Log.d("LLL__amount-->", String.valueOf(amounttotal));
                }
                personal.child("monthDeposit").setValue(amounttotal);
            } else {
                personal.child("monthDeposit").setValue(0);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}

public  void gettotalGrants(String grants) {

    incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(grants).addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.d("LLL-->", String.valueOf(snapshot.getValue()));
            if (snapshot.exists()) {
                int amounttotal = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    amounttotal += pTotal;
                    Log.d("LLL__amount-->", String.valueOf(amounttotal));
                }
                personal.child("mothGrants").setValue(amounttotal);
            } else {
                personal.child("mothGrants").setValue(0);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}

public  void  gettotalSalary(String salary) {

    incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(salary).addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.d("LLL-->", String.valueOf(snapshot.getValue()));
            if (snapshot.exists()) {
                int amounttotal = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    amounttotal += pTotal;
                    Log.d("LLL__amount-->", String.valueOf(amounttotal));
                }
                personal.child("monthSalary").setValue(amounttotal);
            } else {
                personal.child("monthSalary").setValue(0);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}

public void gettotalSaving(String saving) {
    incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(saving).addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.d("LLL-->", String.valueOf(snapshot.getValue()));
            if (snapshot.exists()) {
                int amounttotal = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    amounttotal += pTotal;
                    Log.d("LLL__amount-->", String.valueOf(amounttotal));
                }
                personal.child("monthSaving").setValue(amounttotal);
            } else {
                personal.child("monthSaving").setValue(0);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}

public  void  gettotalOthetrs(String others){

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(others).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("LLL-->", String.valueOf(snapshot.getValue()));
                if (snapshot.exists()) {
                    int amounttotal = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        amounttotal += pTotal;
                        Log.d("LLL__amount-->", String.valueOf(amounttotal));
                    }
                    personal.child("monthOther").setValue(amounttotal);
                } else {
                    personal.child("monthOther").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}