package com.example.solarapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Customer_Display_All_Feedback_Activity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private  Account account;
    ListView Listview;
    ArrayList<Feedback> arrayList;
    View_All_FeedBack_Adapter adapter;
    DBHelper dbh = new DBHelper(this);


    @Override
    public void onBackPressed() {
        // Create an intent to navigate to the Customer_Home_Activity
        super.onBackPressed();
        Intent intent = new Intent(this, Article_Activity.class);
        intent.putExtra("UserInfo", account);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customer_display_all_feedback);

        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("UserInfo");

        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.articleFragment);
        Listview=findViewById(R.id.listView);
        showPostData();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.articleFragment) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), Article_Activity.class);
                    intent.putExtra("UserInfo", account);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.storeFragment) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), View_Products_Activity.class);
                    intent.putExtra("UserInfo", account);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;


                } else if (item.getItemId() == R.id.homeFragment) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), Customer_Home_Activity.class);
                    intent.putExtra("UserInfo", account);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;


                } else if (item.getItemId() == R.id.profileFragment) {
                    Intent intent3 = new Intent();
                    intent3.setClass(getApplicationContext(), Customer_Profile_Activity.class);
                    intent3.putExtra("UserInfo", account);
                    startActivity(intent3);
                    overridePendingTransition(0, 0);
                    return true;
                }

                return false;
            }
        });
    }

    private void showPostData() {
        try {
            arrayList = dbh.searchApprovedFeedback();

            if (arrayList.isEmpty()) {
                Toast.makeText(Customer_Display_All_Feedback_Activity.this, "No feedbacks to display", Toast.LENGTH_LONG).show();
            finish();
            }else {
                adapter = new View_All_FeedBack_Adapter(this, arrayList, dbh,account);
                Listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MyTag", "An error occurred: " + e.getMessage());

            // Create an AlertDialog to display the error message
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("An error occurred: " + e.getMessage());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}