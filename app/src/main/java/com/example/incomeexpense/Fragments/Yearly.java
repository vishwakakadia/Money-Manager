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
import java.util.Map;

public class Yearly extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference incomeExpense;
    private String onlineUserId = "";
    TextView year;
    TextView income;
    TextView expense;
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

        View view = inflater.inflate(R.layout.fragment_yearly, container, false);

        year = view.findViewById(R.id.year);
        income = view.findViewById(R.id.income);
        expense = view.findViewById(R.id.expense);
        category_coupons = view.findViewById(R.id.y1_income);
        category_deposits = view.findViewById(R.id.y2_income);
        category_grants = view.findViewById(R.id.y3_income);
        category_salary = view.findViewById(R.id.y4_income);
        category_saving = view.findViewById(R.id.y5_income);
        category_others = view.findViewById(R.id.y6_income);
        category_health = view.findViewById(R.id.y1_expense);
        category_groceries = view.findViewById(R.id.y2_expense);
        category_shopping = view.findViewById(R.id.y3_expense);
        category_tax = view.findViewById(R.id.y4_expense);
        category_bill = view.findViewById(R.id.y5_expense);
        category_selfCare = view.findViewById(R.id.y6_expense);
        category_otherExpense = view.findViewById(R.id.y7_expense);

        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        incomeExpense = FirebaseDatabase.getInstance().getReference("incomeExpense").child(onlineUserId);
        Calendar calendar;
        calendar = Calendar.getInstance();
        int currunt_year = calendar.get(Calendar.YEAR);
        String income_txt = "Category - Income - "+currunt_year;
        String expense_txt = "Category - expense - "+currunt_year;

        income.setText(income_txt);
        expense.setText(expense_txt);
        

        String yearNlabel_expense = currunt_year+"expense";
        String yearlabel_income = currunt_year+"income";
        String coupons = "Coupons"+currunt_year+"income";
        String deposits = "Deposits"+currunt_year+"income";
        String grants = "Grants"+currunt_year+"income";
        String others = "others"+currunt_year+"income";
        String salary = "Salary"+currunt_year+"income";
        String saving = "Saving"+currunt_year+"income";
        String health = "Health"+currunt_year+"expense";
        String groceries = "Groceries"+currunt_year+"expense";
        String shopping = "Shopping"+currunt_year+"expense";
        String tax = "Tax"+currunt_year+"expense";
        String bill = "Bill"+currunt_year+"expense";
        String self_care = "self care"+currunt_year+"expense";
        String other_expense = "others"+currunt_year+"expense";



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
                        Log.d("LLL__amount-->",String.valueOf(amounttotal));
                    }
                    category_coupons.setText(String.valueOf(amounttotal));
                }

                else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                        Log.d("LLL__amount-->",String.valueOf(amounttotal));
                    }
                    category_deposits.setText(String.valueOf(amounttotal));
                }

                else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                        Log.d("LLL__amount-->",String.valueOf(amounttotal));
                    }
                    category_grants.setText(String.valueOf(amounttotal));
                }

                else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                        Log.d("LLL__amount-->",String.valueOf(amounttotal));
                    }
                    category_salary.setText(String.valueOf(amounttotal));
                }

                else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                        Log.d("LLL__amount-->",String.valueOf(amounttotal));
                    }
                    category_saving.setText(String.valueOf(amounttotal));
                }

                else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                        Log.d("LLL__amount-->",String.valueOf(amounttotal));
                    }
                    category_others.setText(String.valueOf(amounttotal));
                }

                else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                    category_health.setText(String.valueOf(amounttotal));
                }

                else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                    category_groceries.setText(String.valueOf(amounttotal));
                }

                else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                    category_shopping.setText(String.valueOf(amounttotal));
                }

                else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
                    category_tax.setText(String.valueOf(amounttotal));
                }

                else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                    category_bill.setText(String.valueOf(amounttotal));
                }

                else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                    category_selfCare.setText(String.valueOf(amounttotal));
                }

                else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                    category_otherExpense.setText(String.valueOf(amounttotal));
                }

                else {
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