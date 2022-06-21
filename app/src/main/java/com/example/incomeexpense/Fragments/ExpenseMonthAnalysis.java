package com.example.incomeexpense.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

public class ExpenseMonthAnalysis extends Fragment {

    Spinner spinner;
    private FirebaseAuth mAuth;
    private DatabaseReference incomeExpense;
    private DatabaseReference personal;
    private String onlineUserId = "";
    PieChart pieChart;
    String health;
    String groceries;
    String shopping;
    String tax;
    String bill;
    String other_expense;
    String self_care;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_month_analysis, container, false);

        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        incomeExpense = FirebaseDatabase.getInstance().getReference("incomeExpense").child(onlineUserId);
        personal = FirebaseDatabase.getInstance().getReference("personal").child(onlineUserId);


        spinner = view.findViewById(R.id.month_Eselect);
        pieChart = view.findViewById(R.id.pieChart_Eview);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.monthSelect, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int currunt_month = spinner.getSelectedItemPosition() + 1;
                health = "Health" + currunt_month + "expense";
                groceries = "Groceries" + currunt_month + "expense";
                shopping = "Shopping" + currunt_month + "expense";
                tax = "Tax" + currunt_month + "expense";
                bill = "Bill" + currunt_month + "expense";
                self_care = "self care" + currunt_month + "expense";
                other_expense = "others" + currunt_month + "expense";

                gettotalBill(bill);
                gettotalGroceries(groceries);
                gettotalHealth(health);
                gettotalOtherexpense(other_expense);
                gettotalSelfcare(self_care);
                gettotalShopping(shopping);
                tgettotalTax(tax);

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

    private  void  loadGraph() {
        personal.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int health_sum;
                    if (snapshot.hasChild("monthHealth")) {
                        health_sum = Integer.parseInt(snapshot.child("monthHealth").getValue().toString());
                    } else {
                        health_sum = 0;
                    }

                    int groceries_sum;
                    if (snapshot.hasChild("monthGroceries")) {
                        groceries_sum = Integer.parseInt(snapshot.child("monthGroceries").getValue().toString());
                    } else {
                        groceries_sum = 0;
                    }

                    int shopping_sum;
                    if (snapshot.hasChild("monthShopping")) {
                        shopping_sum = Integer.parseInt(snapshot.child("monthShopping").getValue().toString());
                    } else {
                        shopping_sum = 0;
                    }

                    int tax_sum;
                    if (snapshot.hasChild("monthTax")) {
                        tax_sum = Integer.parseInt(snapshot.child("monthTax").getValue().toString());
                    } else {
                        tax_sum = 0;
                    }

                    int bill_sum;
                    if (snapshot.hasChild("monthBill")) {
                        bill_sum = Integer.parseInt(snapshot.child("monthBill").getValue().toString());
                    } else {
                        bill_sum = 0;
                    }
                    int selfCare_sum;
                    if (snapshot.hasChild("monthSelfcare")) {
                        selfCare_sum = Integer.parseInt(snapshot.child("monthSelfcare").getValue().toString());
                    } else {
                        selfCare_sum = 0;
                    }

                    int other_sum;
                    if (snapshot.hasChild("monthOtherexpense")) {
                        other_sum = Integer.parseInt(snapshot.child("monthOtherexpense").getValue().toString());
                    } else {
                        other_sum = 0;
                    }



                    ArrayList<PieEntry> pieEntries = new ArrayList<>();
                    String label = "type";

                    Map<String, Integer> typeAmountMap = new HashMap<>();
                    typeAmountMap.put("Health", health_sum);
                    typeAmountMap.put("Groceries", groceries_sum);
                    typeAmountMap.put("Shopping", shopping_sum);
                    typeAmountMap.put("Tax", tax_sum);
                    typeAmountMap.put("Bill", bill_sum);
                    typeAmountMap.put("Self care", selfCare_sum);
                    typeAmountMap.put("others", other_sum);

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

        public void gettotalHealth(String health) {

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(health).addListenerForSingleValueEvent(new ValueEventListener() {

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
//                        Log.d("LLL__amount-->",String.valueOf(amounttotal));
                    }
                    personal.child("monthHealth").setValue(amounttotal);
                } else {
                    personal.child("monthHealth").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void gettotalGroceries(String groceries) {

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(groceries).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    }
                    personal.child("monthGroceries").setValue(amounttotal);
                } else {
                    personal.child("monthGroceries").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void gettotalShopping(String shopping) {

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(shopping).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    }
                    personal.child("monthShopping").setValue(amounttotal);
                } else {
                    personal.child("monthShopping").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void tgettotalTax(String tax) {


        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(tax).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int amounttotal = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        amounttotal += pTotal;
                    }
                    personal.child("monthTax").setValue(amounttotal);
                } else {
                    personal.child("monthTax").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void gettotalBill(String bill) {

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(bill).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int amounttotal = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        amounttotal += pTotal;
                    }
                    personal.child("monthBill").setValue(amounttotal);
                } else {
                    personal.child("monthBill").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void gettotalSelfcare(String self_care) {

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(self_care).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int amounttotal = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        amounttotal += pTotal;
                    }
                    personal.child("monthSelfcare").setValue(amounttotal);
                } else {
                    personal.child("monthSelfcare").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void gettotalOtherexpense(String other_expense) {

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(other_expense).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int amounttotal = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        amounttotal += pTotal;
                    }
                    personal.child("monthOtherexpense").setValue(amounttotal);
                } else {
                    personal.child("monthOtherexpense").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}