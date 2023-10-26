package com.example.solarapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Customer_View_Orders_Activity extends AppCompatActivity {
    private Account account;
    ListView listView;
    ArrayList<Orders> arrayList;
    Customer_Order_Adapter adapter;
    DBHelper dbh = new DBHelper(this);
    BottomNavigationView bottomNavigationView;

    @Override
    public void onBackPressed() {
        // Create an intent to navigate to the Customer_Home_Activity
        super.onBackPressed();
        Intent intent = new Intent(this, Customer_Profile_Activity.class);
        intent.putExtra("UserInfo", account);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customer_view_orders);

        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.profileFragment);
        listView = findViewById(R.id.listView);
        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("UserInfo");

        showPostData();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.profileFragment) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), Customer_Profile_Activity.class);
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


                } else if (item.getItemId() == R.id.articleFragment) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), Article_Activity.class);
                    intent.putExtra("UserInfo", account);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;


                } else if (item.getItemId() == R.id.storeFragment) {
                    Intent intent3 = new Intent();
                    intent3.setClass(getApplicationContext(), View_Products_Activity.class);
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
            arrayList = dbh.getCustomerOrders(account.getId());
            adapter = new Customer_Order_Adapter(this, arrayList, dbh, account); // Pass the account
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "You have no orders yet", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Customer_View_Orders_Activity.this, Customer_Profile_Activity.class);
            intent.putExtra("UserInfo", account);
            startActivity(intent);
            finish(); // Close the current activity
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}