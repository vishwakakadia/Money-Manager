package com.example.incomeexpense.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.incomeexpense.Models.Data;
import com.example.incomeexpense.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class incomeAddActivity extends AppCompatActivity {
    TextView date, dateans;
    EditText income_amount;
    EditText income_note;
    Spinner spinner;
    Button save_btn;

    private FirebaseAuth mAuth;
    private DatabaseReference incomeExpense;
    private  Data data;
    String label = "income";


    private DatePicker datePicker;
    DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_add);

        date = findViewById(R.id.date);
        dateans = findViewById(R.id.dateans);
        spinner = findViewById(R.id.selectincome);
        income_amount = findViewById(R.id.income_amountans);
        income_note = findViewById(R.id.income_noteans);
        save_btn = findViewById(R.id.save_btn);

        mAuth = FirebaseAuth.getInstance();
        String userid = mAuth.getUid();
        incomeExpense = FirebaseDatabase.getInstance().getReference("incomeExpense").child(String.valueOf(userid));



        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dateans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(incomeAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        dateans.setText(dayOfMonth + "-" + (month + 1) + "-" + year);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        spinner = findViewById(R.id.selectincome);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.IncomeCategory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        Intent intent = getIntent();
        String get_id = intent.getStringExtra("id");
        String get_date = intent.getStringExtra("date");
        int  get_amount = intent.getIntExtra("amount",0);
        String get_category = intent.getStringExtra("category");
        String get_note = intent.getStringExtra("note");
        boolean isclick = intent.getBooleanExtra("isclick",false);
        dateans.setText(get_date);
        income_amount.setText(String.valueOf(get_amount));
        income_note.setText(get_note);

        if (isclick) {
            save_btn.setText("Update");
        } else {
            save_btn.setText("Save");
        }

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isclick) {
                    String date_ans = dateans.getText().toString();
                    String category_ans = spinner.getSelectedItem().toString();
                    String amount_ans = income_amount.getText().toString();
                    String note_ans = income_note.getText().toString();
                    calendar = Calendar.getInstance();
                    int d_year = calendar.get(Calendar.YEAR);
                    int d_month = calendar.get(Calendar.MONTH)+1;

                    String categoryNmonthNlabel = category_ans+d_month + label;
                    String categorynyearNlabel = category_ans+d_year + label;
                    String monthNlabel = d_month+label;
                    String yearNlabel = d_year+label;
                    String dateNlable = date_ans+label;

                    Data data = new Data(get_id,date_ans,category_ans,categoryNmonthNlabel,categorynyearNlabel,note_ans,label,monthNlabel,yearNlabel,dateNlable,Integer.parseInt(amount_ans),d_month,d_year);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("incomeExpense").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    reference.child(get_id).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                } else {
                    String date_ans = dateans.getText().toString();
                    String category_ans = spinner.getSelectedItem().toString();
                    String amount_ans = income_amount.getText().toString();
                    String note_ans = income_note.getText().toString();

                    if (TextUtils.isEmpty(date_ans)) {
                        dateans.setError("Date is required");
                        return;
                    }
                    if (TextUtils.isEmpty(amount_ans)) {
                        income_amount.setError("Amount is required");
                    }
                    if (TextUtils.isEmpty(note_ans)) {
                        income_note.setError("Note is required");
                    } else {
                        String id = incomeExpense.push().getKey();
                        calendar = Calendar.getInstance();
                        int d_year = calendar.get(Calendar.YEAR);
                        int d_month = calendar.get(Calendar.MONTH)+1;

                        String categoryNmonthNlabel = category_ans+d_month + label;
                        String categoryNyearNlabel = category_ans+d_year + label;
                        String monthNlabel = d_month+label;
                        String yearNlabel = d_year+label;
                        String dateNlanle = date_ans+label;

                        Data data = new Data(id,date_ans,category_ans,categoryNmonthNlabel,categoryNyearNlabel,note_ans,label,monthNlabel,yearNlabel,dateNlanle,Integer.parseInt(amount_ans),d_month,d_year);
                        incomeExpense.child(id).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "added successfuly", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }

                }
            }
        });
    }


}