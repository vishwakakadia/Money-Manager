package com.example.incomeexpense.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.incomeexpense.Adapters.homeActivityAdapter;
import com.example.incomeexpense.Models.Data;
import com.example.incomeexpense.R;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.Timestamp;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import jxl.Cell;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int NOTIFICATION_REMINDER_NIGHT = 2;
    TabLayout tabLayout;
    ViewPager viewPager;

    FloatingActionButton mainbtn;
    FloatingActionButton incomebtn;
    FloatingActionButton expensebtn;

    Animation fadOpen;
    Animation fadclose;


    TextView total_income;
    TextView total_expense;
    ImageView download;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    String ans_title;
    File root;
    File myCSV;
    File root1;
    File XLS;
    WritableWorkbook workbook;
    WritableSheet sheet;
    int total;
    int i = 1;
    WritableCellFormat titleformat;


    FirebaseAuth mAuth;
    DatabaseReference incomeExpense;
    DatabaseReference personal;
    String onlineUserId = "";


    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        incomeExpense = FirebaseDatabase.getInstance().getReference("incomeExpense").child(onlineUserId);
        personal = FirebaseDatabase.getInstance().getReference("personal").child(onlineUserId);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Money Manager");
        setSupportActionBar(toolbar);


        navigationView = findViewById(R.id.hamburgur);
        navigationView.setNavigationItemSelectedListener(this);


        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        expenseData = new ExpenseData(getApplicationContext());

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        total_income = findViewById(R.id.total_income);
        total_expense = findViewById(R.id.total_expense);

        Calendar calendar;
        calendar = Calendar.getInstance();
        int currunt_year = calendar.get(Calendar.YEAR);
        String yearNlabel_expense = currunt_year + "expense";
        String yearlabel_income = currunt_year + "income";

        download = findViewById(R.id.dpwnlod_image);


        expensebtn = findViewById(R.id.expencebtn);
        expensebtn.setColorFilter(Color.WHITE);
        incomebtn = findViewById(R.id.addbtn);
        incomebtn.setColorFilter(Color.WHITE);
        mainbtn = findViewById(R.id.main_btn);
        mainbtn.setColorFilter(Color.WHITE);


        fadOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_open);
        fadclose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_close);

        tabLayout.addTab(tabLayout.newTab().setText("Daily"));
        tabLayout.addTab(tabLayout.newTab().setText("Monthly"));
        tabLayout.addTab(tabLayout.newTab().setText("Yearly"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final homeActivityAdapter adapter = new homeActivityAdapter(this, getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ftAnimation();
                activitynavigate();
                incomebtn.setVisibility(View.VISIBLE);
                expensebtn.setVisibility(View.VISIBLE);
            }


        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showbottomsheetDailog();


            }
        });

        incomeExpense.orderByChild("yearNlable").equalTo(yearlabel_income).addListenerForSingleValueEvent(new ValueEventListener() {

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
                    total_expense.setText(String.valueOf(amounttotal));
                } else {
                    Log.d("else", "not executing query");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        incomeExpense.orderByChild("yearNlable").equalTo(yearNlabel_expense).addListenerForSingleValueEvent(new ValueEventListener() {

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


    }

    private void showbottomsheetDailog() {

        FirebaseAuth mAuth;
        DatabaseReference incomeExpense;
        String onlineUserId = "";


        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        incomeExpense = FirebaseDatabase.getInstance().getReference("incomeExpense").child(onlineUserId);

        Spinner month_select;
        Spinner category_select;
        EditText format_select;
        EditText title;
        Button export;


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.download_view);

        title = bottomSheetDialog.findViewById(R.id.Title);
        export = bottomSheetDialog.findViewById(R.id.export_btn);

        month_select = bottomSheetDialog.findViewById(R.id.month_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.monthSelect, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_select.setAdapter(adapter);

        category_select = bottomSheetDialog.findViewById(R.id.category_select);
        ArrayAdapter<CharSequence> c_adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.dailogSelect, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_select.setAdapter(c_adapter);

        format_select = bottomSheetDialog.findViewById(R.id.Title_formate);


        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans_title = title.getText().toString();
                String month = month_select.getSelectedItem().toString();
                String category = category_select.getSelectedItem().toString();
                String format = format_select.getText().toString();
                root = new File(Environment.getExternalStorageDirectory() + "/Download/");
                myCSV = new File(root, ans_title + ".csv");
                try {
                    myCSV.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

//


                Map<String, Integer> lookup = new HashMap<String, Integer>();
                lookup.put("January", 1);
                lookup.put("February", 2);
                lookup.put("March", 3);
                lookup.put("April", 4);
                lookup.put("May", 5);
                lookup.put("June", 6);
                lookup.put("July", 7);
                lookup.put("August", 8);
                lookup.put("September", 9);
                lookup.put("October", 10);
                lookup.put("November", 11);
                lookup.put("December", 12);

                int int_month = lookup.get(month);

                String Month_label = int_month + category;
                Log.d("LLL__month--->", String.valueOf(int_month));

                if (format.equals("CSV")) {
                    incomeExpense.orderByChild("monthNlabel").equalTo(Month_label).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String date = dataSnapshot.child("date").getValue().toString();
                                String category = dataSnapshot.child("category").getValue().toString();
                                String amount = dataSnapshot.child("amount").getValue().toString();
                                String note = dataSnapshot.child("note").getValue().toString();
                                try {
                                    exportCSV(date, category, amount, note);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                Toast.makeText(getApplicationContext(), "file saved successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);

            }
        });


        bottomSheetDialog.show();
    }

    private void activitynavigate() {


        incomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), incomeAddActivity.class);
                startActivity(intent);
                incomebtn.setVisibility(View.GONE);
                expensebtn.setVisibility(View.GONE);
            }
        });

        expensebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), expenseAddActivity.class);
                startActivity(intent);
                expensebtn.setVisibility(View.GONE);
                incomebtn.setVisibility(View.GONE);
            }
        });
    }

    public void ftAnimation() {
        if (isOpen) {
            incomebtn.startAnimation(fadclose);
            expensebtn.startAnimation(fadclose);
            incomebtn.setClickable(false);
            expensebtn.setClickable(false);
            expensebtn.setVisibility(View.GONE);
            incomebtn.setVisibility(View.GONE);
            isOpen = false;


        } else {
            incomebtn.startAnimation(fadOpen);
            expensebtn.startAnimation(fadOpen);
            incomebtn.setClickable(true);
            expensebtn.setClickable(true);
            incomebtn.setVisibility(View.VISIBLE);
            expensebtn.setVisibility(View.VISIBLE);
            isOpen = true;


        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DisplaySelectListener(item.getItemId());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void DisplaySelectListener(int itemId) {
        Intent intent = null;
        switch (itemId) {
            case R.id.month_analysis:
                intent = new Intent(getApplicationContext(), MonthAnalysisActivity.class);
                startActivity(intent);
                break;

            case R.id.year_analysis:
                intent = new Intent(getApplicationContext(), yearAnalysisActivity.class);
                startActivity(intent);
                break;

            case R.id.logout_app:
                new AlertDialog.Builder(HomeActivity.this, R.style.MyAlertDialogMaterialStyle)
                        .setTitle("Money Manager")
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getApplicationContext(), LoginRegistrationActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;

        }
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

    }

    public void exportCSV(String date, String category, String amount, String note) throws IOException {

        try {


            if (!root.exists()) {
                root.mkdirs();
            }


            FileWriter writer = new FileWriter(myCSV, true);
            writer.append("Date : " + date + ",");
            writer.append("category : " + category + ",");
            writer.append("amount : " + amount + ",");
            writer.append("note : " + note + "\n");

            writer.flush();
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void expoetXls(String date, String category, String amount, String note) throws WriteException, IOException {
        if (!root1.exists()) {
            root1.mkdirs();
        }
        try {
            sheet.addCell(new Label(0, i + 1, date));
            sheet.addCell(new Label(1, i + 1, category));
            sheet.addCell(new Label(2, i + 1, amount));
            sheet.addCell(new Label(3, i + 1, note));
            workbook.write();
            workbook.close();
            Toast.makeText(this, "File Saved Successfully!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}
