package com.example.incomeexpense.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.incomeexpense.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Monthly extends Fragment {
    private FirebaseAuth mAuth;
    private DatabaseReference incomeExpense;
    private String onlineUserId = "";

    TextView month;
    TextView total_income;
    TextView total_expense;
    TextView category_coupons;
    TextView category_deposits;
    TextView category_grants;
    TextView category_salary;
    TextView category_others;
    TextView category_saving;
    TextView category_health;
    TextView category_groceries;
    TextView category_shopping;
    TextView category_tax;
    TextView category_bill;
    TextView category_selfCare;
    TextView category_otherExpense;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_monthly, container, false);



        month = view.findViewById(R.id.month);
        total_income = view.findViewById(R.id.total_income_amount);
        total_expense = view.findViewById(R.id.total_expense_amount);
        category_coupons = view.findViewById(R.id.c1_income);
        category_deposits = view.findViewById(R.id.c2_income);
        category_grants = view.findViewById(R.id.c3_income);
        category_salary = view.findViewById(R.id.c4_income);
        category_saving = view.findViewById(R.id.c5_income);
        category_others = view.findViewById(R.id.c6_income);
        category_health = view.findViewById(R.id.e1_expense);
        category_groceries = view.findViewById(R.id.e2_expense);
        category_shopping = view.findViewById(R.id.e3_expense);
        category_tax = view.findViewById(R.id.e4_expense);
        category_bill = view.findViewById(R.id.e5_expense);
        category_selfCare = view.findViewById(R.id.e6_expense);
        category_otherExpense = view.findViewById(R.id.e7_expense);

        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        incomeExpense = FirebaseDatabase.getInstance().getReference("incomeExpense").child(onlineUserId);
        Calendar calendar;
        calendar = Calendar.getInstance();
        int currunt_month = calendar.get(Calendar.MONTH) + 1;
        int currunt_year = calendar.get(Calendar.YEAR);


        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        String month_name = monthName[currunt_month - 1];

        month.setText(month_name);

        String monthNlabel_expense = currunt_month + "expense";
        String monthlabel_income = currunt_month + "income";
        String coupons = "Coupons" + currunt_month + "income";
        String deposits = "Deposits" + currunt_month + "income";
        String grants = "Grants" + currunt_month + "income";
        String others = "others" + currunt_month + "income";
        String salary = "Salary" + currunt_month + "income";
        String saving = "Saving" + currunt_month + "income";
        String health = "Health" + currunt_month + "expense";
        String groceries = "Groceries" + currunt_month + "expense";
        String shopping = "Shopping" + currunt_month + "expense";
        String tax = "Tax" + currunt_month + "expense";
        String bill = "Bill" + currunt_month + "expense";
        String self_care = "self care" + currunt_month + "expense";
        String other_expense = "others" + currunt_month + "expense";
        String other_expense_find_view_by_id = "others" + currunt_month + "expense";
        String other_expense_send_monthly = "others" + currunt_month + "expense";

        incomeExpense.orderByChild("monthNlabel").equalTo(monthNlabel_expense).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    total_expense.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        incomeExpense.orderByChild("monthNlabel").equalTo(monthlabel_income).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    total_income.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(coupons).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    category_coupons.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(deposits).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    category_deposits.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(grants).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    category_grants.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(salary).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    category_salary.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(saving).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    category_saving.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(others).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    category_others.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(health).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    category_health.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                    category_groceries.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(shopping).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    category_shopping.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
                    category_tax.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                    category_bill.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(self_care).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    category_selfCare.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        incomeExpense.orderByChild("categoryNmonthNlabel").equalTo(other_expense).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    category_otherExpense.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }

}