package com.example.incomeexpense.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.incomeexpense.Adapters.dailyAdapter;
import com.example.incomeexpense.Adapters.homeActivityAdapter;
import com.example.incomeexpense.Models.Data;
import com.example.incomeexpense.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class Daily extends Fragment {
    RecyclerView recyclerView;
    dailyAdapter dailyadapter;

    private FirebaseAuth mAuth;
    private DatabaseReference incomeExpense;

    TextView dateset;
    TextView total_income;
    TextView total_expense;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily, container, false);

        dateset = view.findViewById(R.id.date_daily);
        total_income = view.findViewById(R.id.total_income_amount_daily);
        total_expense = view.findViewById(R.id.total_expense_amount_daily);

        mAuth = FirebaseAuth.getInstance();
        String userid = mAuth.getUid();
        incomeExpense = FirebaseDatabase.getInstance().getReference("incomeExpense").child(String.valueOf(userid));


        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        String today_date = today+"-"+month+"-"+year;
        dateset.setText(today_date);
        String dateNlable = today_date+"income";
        String dateNlableX = today_date+"expense";

        incomeExpense.orderByChild("dateNlable").equalTo(dateNlable).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    total_income.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        incomeExpense.orderByChild("dateNlable").equalTo(dateNlableX).addListenerForSingleValueEvent(new ValueEventListener() {

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
                      Log.d("LLL__daily-->",String.valueOf(amounttotal));
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



        recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        FirebaseRecyclerOptions<Data> options
                = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(incomeExpense, Data.class)
                .build();
        dailyadapter = new dailyAdapter(getContext(),options);

        recyclerView.setAdapter(dailyadapter);



        return view;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        dailyadapter.startListening();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        dailyadapter.stopListening();
    }

 }







