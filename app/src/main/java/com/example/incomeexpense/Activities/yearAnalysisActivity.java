package com.example.incomeexpense.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.incomeexpense.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class yearAnalysisActivity extends AppCompatActivity {
    Spinner spinner;
    Toolbar toolbar;

    private FirebaseAuth mAuth;
    private DatabaseReference incomeExpense;
    private DatabaseReference personal;
    private String onlineUserId = "";

    String coupons;
    String deposits;
    String saving;
    String salary;
    String grants;
    String others;
    String label;
    String health;
    String shopping;
    String bill;
    String self_care;
    String groceries;
    String other_expense;
    String tax;
    String income;
    String expense;

    PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_analysis);

        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        incomeExpense = FirebaseDatabase.getInstance().getReference("incomeExpense").child(onlineUserId);
        personal = FirebaseDatabase.getInstance().getReference("personal").child(onlineUserId);

        toolbar = findViewById(R.id.toolbar_year);
        toolbar.setTitle("Money Manager");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        spinner = findViewById(R.id.category_year_select);
        pieChart = findViewById(R.id.pieChart_Yview);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.yearAnalysis, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Calendar calendar;
                calendar = Calendar.getInstance();
                int currunt_year = calendar.get(Calendar.YEAR);
                label = (String.valueOf(spinner.getSelectedItem()));
                coupons = "Coupons" + currunt_year + "income";
                deposits = "Deposits" + currunt_year + "income";
                grants = "Grants" + currunt_year + "income";
                others = "others" + currunt_year + "income";
                salary = "Salary" + currunt_year + "income";
                saving = "Saving" + currunt_year + "income";
                health = "Health" + currunt_year + "expense";
                groceries = "Groceries" + currunt_year + "expense";
                shopping = "Shopping" + currunt_year + "expense";
                tax = "Tax" + currunt_year + "expense";
                bill = "Bill" + currunt_year + "expense";
                self_care = "self care" + currunt_year + "expense";
                other_expense = "others" + currunt_year + "expense";
                income = currunt_year+"income";
                expense = currunt_year+"expense";

                if(label.equals("income")){
                    getotalYsaving(saving);
                    gettotalDeposits(deposits);
                    gettotalGrants(grants);
                    gettotalSalary(salary);
                    gettotalYcoupons(coupons);
                    gettotalYother(others);
                    initPieChart();
                    loadGraphincome();
                }
                else if(label.equals("expense")){
                    gettoalYhealth(health);
                    gettotalGroceries(groceries);
                    gettotalShopping(shopping);
                    gettotalTax(tax);
                    gettotalBill(bill);
                    gettotalSelfcare(self_care);
                    gettotalOtherexpense(other_expense);
                    initPieChart();
                    loadGraphExpense();

                }
                else {
                    gettotalIncome(income);
                    gettotalExpense(expense);
                    initPieChart();
                    loadGraph();


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
    private  void  loadGraphincome(){
        personal.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int coupon_sum;
                    if(snapshot.hasChild("yearCoupons")){
                        coupon_sum = Integer.parseInt(snapshot.child("yearCoupons").getValue().toString());
                    }
                    else {
                        coupon_sum = 0;
                    }

                    int deposite_sum;
                    if(snapshot.hasChild("yearDeposits")){
                        deposite_sum = Integer.parseInt(snapshot.child("yearDeposits").getValue().toString());
                    }
                    else {
                        deposite_sum = 0;
                    }

                    int grant_sum;
                    if(snapshot.hasChild("yearGrants")){
                        grant_sum = Integer.parseInt(snapshot.child("yearGrants").getValue().toString());
                    }
                    else {
                        grant_sum = 0;
                    }

                    int salary_sum;
                    if(snapshot.hasChild("yearSalary")){
                        salary_sum = Integer.parseInt(snapshot.child("yearSalary").getValue().toString());
                    }
                    else {
                        salary_sum = 0;
                    }

                    int saving_sum;
                    if(snapshot.hasChild("yearSaving")){
                        saving_sum = Integer.parseInt(snapshot.child("yearSaving").getValue().toString());
                    }
                    else {
                        saving_sum = 0;
                    }
                    int others_sum;
                    if(snapshot.hasChild("yearOther")){
                        others_sum = Integer.parseInt(snapshot.child("yearOther").getValue().toString());
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
    private  void  loadGraph(){
        personal.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int income_sum;
                    if(snapshot.hasChild("totalIncome")){
                        income_sum = Integer.parseInt(snapshot.child("totalIncome").getValue().toString());
                    }
                    else {
                        income_sum = 0;
                    }

                    int expense_sum;
                    if(snapshot.hasChild("totalExpense")){
                        expense_sum = Integer.parseInt(snapshot.child("totalExpense").getValue().toString());
                    }
                    else {
                        expense_sum = 0;
                    }

                    ArrayList<PieEntry> pieEntries = new ArrayList<>();
                    String label = "type";

                    Map<String, Integer> typeAmountMap = new HashMap<>();
                    typeAmountMap.put("Income",income_sum);
                    typeAmountMap.put("Expense",expense_sum);

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
    private  void  loadGraphExpense() {
        personal.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int health_sum;
                    if (snapshot.hasChild("yearHealth")) {
                        health_sum = Integer.parseInt(snapshot.child("yearHealth").getValue().toString());
                    } else {
                        health_sum = 0;
                    }

                    int groceries_sum;
                    if (snapshot.hasChild("yearGroceries")) {
                        groceries_sum = Integer.parseInt(snapshot.child("yearGroceries").getValue().toString());
                    } else {
                        groceries_sum = 0;
                    }

                    int shopping_sum;
                    if (snapshot.hasChild("yearShopping")) {
                        shopping_sum = Integer.parseInt(snapshot.child("yearShopping").getValue().toString());
                    } else {
                        shopping_sum = 0;
                    }

                    int tax_sum;
                    if (snapshot.hasChild("yearTax")) {
                        tax_sum = Integer.parseInt(snapshot.child("yearTax").getValue().toString());
                    } else {
                        tax_sum = 0;
                    }

                    int bill_sum;
                    if (snapshot.hasChild("yearBill")) {
                        bill_sum = Integer.parseInt(snapshot.child("yearBill").getValue().toString());
                    } else {
                        bill_sum = 0;
                    }
                    int selfCare_sum;
                    if (snapshot.hasChild("yearSelfcare")) {
                        selfCare_sum = Integer.parseInt(snapshot.child("yearSelfcare").getValue().toString());
                    } else {
                        selfCare_sum = 0;
                    }

                    int other_sum;
                    if (snapshot.hasChild("yearotherexpense")) {
                        other_sum = Integer.parseInt(snapshot.child("yearotherexpense").getValue().toString());
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


    public void gettotalYcoupons(String coupons) {

        incomeExpense.orderByChild("categoryNyearNlabel").equalTo(coupons).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    personal.child("yearCoupons").setValue(amounttotal);
                } else {
                    personal.child("yearCoupons").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void gettotalDeposits(String deposits) {

        incomeExpense.orderByChild("categoryNyearNlabel").equalTo(deposits).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    personal.child("yearDeposits").setValue(amounttotal);
                } else {
                    personal.child("yearDeposits").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void gettotalGrants(String grants) {

        incomeExpense.orderByChild("categoryNyearNlabel").equalTo(grants).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    personal.child("yearGrants").setValue(amounttotal);
                } else {
                    personal.child("yearGrants").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void gettotalSalary(String salary) {

        incomeExpense.orderByChild("categoryNyearNlabel").equalTo(salary).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    personal.child("yearSalary").setValue(amounttotal);
                } else {
                    personal.child("yearSalary").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getotalYsaving(String saving) {

        incomeExpense.orderByChild("categoryNyearNlabel").equalTo(saving).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    personal.child("yearSaving").setValue(amounttotal);
                } else {
                    personal.child("yearSaving").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void gettotalYother(String others) {


        incomeExpense.orderByChild("categoryNyearNlabel").equalTo(others).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    personal.child("yearOther").setValue(amounttotal);
                } else {
                    personal.child("yearOther").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void gettoalYhealth(String health) {
        incomeExpense.orderByChild("categoryNyearNlabel").equalTo(health).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    personal.child("yearHealth").setValue(amounttotal);
                } else {
                    personal.child("yearHealth").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public  void  gettotalGroceries(String groceries) {

        incomeExpense.orderByChild("categoryNyearNlabel").equalTo(groceries).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    personal.child("yearGroceries").setValue(amounttotal);
                } else {
                    personal.child("yearGroceries").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public  void  gettotalShopping(String shopping) {

        incomeExpense.orderByChild("categoryNyearNlabel").equalTo(shopping).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    personal.child("yearShopping").setValue(amounttotal);
                } else {
                    personal.child("yearShopping").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

public  void  gettotalTax(String tax) {
    incomeExpense.orderByChild("categoryNyearNlabel").equalTo(tax).addListenerForSingleValueEvent(new ValueEventListener() {

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
                personal.child("yearTax").setValue(amounttotal);
            } else {
                personal.child("yearTax").setValue(0);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}

public  void gettotalBill(String bill) {

    incomeExpense.orderByChild("categoryNyearNlabel").equalTo(bill).addListenerForSingleValueEvent(new ValueEventListener() {

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
                personal.child("yearBill").setValue(amounttotal);
            } else {
                personal.child("yearBill").setValue(0);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

}

public  void  gettotalSelfcare(String self_care) {

    incomeExpense.orderByChild("categoryNyearNlabel").equalTo(self_care).addListenerForSingleValueEvent(new ValueEventListener() {

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
                personal.child("yearSelfcare").setValue(amounttotal);
            } else {
                personal.child("yearSelfcare").setValue(0);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}

public  void  gettotalOtherexpense(String other_expense) {

    incomeExpense.orderByChild("categoryNyearNlabel").equalTo(other_expense).addListenerForSingleValueEvent(new ValueEventListener() {

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
                personal.child("yearotherexpense").setValue(amounttotal);
            } else {
                personal.child("yearotherexpense").setValue(0);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}
public  void  gettotalIncome(String income) {
    incomeExpense.orderByChild("yearNlable").equalTo(income).addListenerForSingleValueEvent(new ValueEventListener() {

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
                personal.child("totalIncome").setValue(amounttotal);
            } else {
                personal.child("totalIncome").setValue(0);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}

public  void  gettotalExpense(String expense) {

    incomeExpense.orderByChild("yearNlable").equalTo(expense).addListenerForSingleValueEvent(new ValueEventListener() {

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
                personal.child("totalExpense").setValue(amounttotal);
            } else {
                personal.child("totalExpense").setValue(0);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}


}



