package com.example.incomeexpense.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.incomeexpense.Activities.expenseAddActivity;
import com.example.incomeexpense.Activities.incomeAddActivity;
import com.example.incomeexpense.Fragments.Daily;
import com.example.incomeexpense.Models.Data;
import com.example.incomeexpense.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;



public class dailyAdapter extends FirebaseRecyclerAdapter<Data,dailyAdapter.ViewHolder> {
Context context;
    public dailyAdapter(Context context,@NonNull FirebaseRecyclerOptions<Data> options)
    {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull dailyAdapter.ViewHolder holder, int position, @NonNull Data model) {
        holder.date.setText(String.valueOf(model.getDate()));
        if (model.getLabel().equals("income")) {

            holder.amount.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            holder.amount.setTextColor(context.getResources().getColor(R.color.red));
        }
        holder.amount.setText(String.valueOf(model.getAmount()));
        holder.category.setText(model.getCategory());
        holder.note.setText(model.getNote());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(model.getLabel().equals("income")){
                    intent = new Intent(context, incomeAddActivity.class);
                    intent.putExtra("id",model.getId());
                    intent.putExtra("date",model.getDate());
                    intent.putExtra("category",model.getCategory());
                    intent.putExtra("amount",model.getAmount());
                    intent.putExtra("note",model.getNote());
                    intent.putExtra("isclick",true);

                }
                else {
                    intent  = new Intent(context, expenseAddActivity.class);
                    intent.putExtra("id",model.getId());
                    intent.putExtra("date",model.getDate());
                    intent.putExtra("category",model.getCategory());
                    intent.putExtra("amount",model.getAmount());
                    intent.putExtra("note",model.getNote());
                    intent.putExtra("isclick",true);


                }
                context.startActivity(intent);

            }
        });


    }

    @NonNull
    @Override
    public dailyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_raw, parent, false);
        return new dailyAdapter.ViewHolder(view);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView category, amount, date, note;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            note = itemView.findViewById(R.id.note);

        }
    }
}
