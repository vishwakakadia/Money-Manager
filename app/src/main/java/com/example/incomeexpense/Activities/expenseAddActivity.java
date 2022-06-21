package com.example.incomeexpense.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class expenseAddActivity extends AppCompatActivity {
    TextView date;
    TextView dateans;
    EditText amount;
    EditText note;
    public Button savebtn;
    private DatePicker datePicker;
    DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private int year, month, day;
    Spinner spinner;


    private Data data;

    String label = "expense";

    private FirebaseAuth mAuth;
    private DatabaseReference incomeExpense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_add_activiy);

        date = findViewById(R.id.date);
        dateans = findViewById(R.id.dateans);
        amount = findViewById(R.id.amountans);
        note = findViewById(R.id.noteans);
        savebtn = findViewById(R.id.savebtn);

        mAuth = FirebaseAuth.getInstance();
        String userid = mAuth.getCurrentUser().getUid();
        incomeExpense = FirebaseDatabase.getInstance().getReference("incomeExpense").child(userid);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dateans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(expenseAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        dateans.setText(dayOfMonth + "-" + (month + 1) + "-" + year);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        spinner = findViewById(R.id.selectcat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.Category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        Intent intent = getIntent();
        String get_id = intent.getStringExtra("id");
        String get_date = intent.getStringExtra("date");
        int get_amount = intent.getIntExtra("amount", 0);
        String get_category = intent.getStringExtra("category");
        String get_note = intent.getStringExtra("note");
        boolean isclick = intent.getBooleanExtra("isclick", false);

        dateans.setText(get_date);
        amount.setText(String.valueOf(get_amount));
        note.setText(get_note);

        if (isclick) {
            savebtn.setText("Update");
        } else {
            savebtn.setText("Save");
        }

//        int x = dbqueries.monthincomeTotal();
//        Log.d("LLL___x-->", String.valueOf(x));

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isclick) {
                    String date_ans = dateans.getText().toString();
                    String category_ans = spinner.getSelectedItem().toString();
                    String amount_ans = amount.getText().toString();
                    String note_ans = note.getText().toString();
                    calendar = Calendar.getInstance();
                    int d_year = calendar.get(Calendar.YEAR);
                    int d_month = calendar.get(Calendar.MONTH) + 1;

                    String categoryNmonthNlabel = category_ans + d_month +label;
                    String categorynyearNlabel = category_ans + d_year + label;
                    String monthNlabel = d_month+label;
                    String yearNlabel = d_year+label;
                    String dateNlable = date_ans+label;

                    Data data = new Data(get_id, date_ans, category_ans, categoryNmonthNlabel, categorynyearNlabel, note_ans, label,monthNlabel,yearNlabel,dateNlable, Integer.parseInt(amount_ans), d_month, d_year);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("incomeExpense").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    reference.child(get_id).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                } else {
                    String date_ans = dateans.getText().toString();
                    String category_ans = spinner.getSelectedItem().toString();
                    String amount_ans = amount.getText().toString();
                    String note_ans = note.getText().toString();

                    if (TextUtils.isEmpty(amount_ans)) {
                        amount.setError("Amount is required!");
                        return;
                    }


                    if (TextUtils.isEmpty(note_ans)) {
                        note.setError("Note is required");
                        return;
                    } else {
                        String id = incomeExpense.push().getKey();
                        calendar = Calendar.getInstance();
                        int d_year = calendar.get(Calendar.YEAR);
                        int d_month = calendar.get(Calendar.MONTH) + 1;

                        String categoryNmonthNlabel = category_ans + d_month +label;
                        String categorynyearNlabel = category_ans + d_year+label;
                        String monthNlabel = d_month+label;
                        String yearNlabel = d_year+label;
                        String dateNlable = date_ans+label;


                        Data data = new Data(id, date_ans, category_ans, categoryNmonthNlabel, categorynyearNlabel, note_ans, label,monthNlabel,yearNlabel,dateNlable, Integer.parseInt(amount_ans), d_month, d_year);
                        incomeExpense.child(id).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "added successfuly", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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