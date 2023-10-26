package com.example.solarapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Article_Activity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private  Account account;

    Button Articles,News,Feedback,MyFeedback;

    @Override
    public void onBackPressed() {
        // Create an intent to navigate to the Customer_Home_Activity
        super.onBackPressed();
        Intent intent = new Intent(this, Customer_Home_Activity.class);
        intent.putExtra("UserInfo", account);
        startActivity(intent);
        finish(); // Finish the current activity
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_article);

        Articles =findViewById(R.id.button2);
        News =findViewById(R.id.button1);
        Feedback =findViewById(R.id.button3);
        MyFeedback =findViewById(R.id.button4);

        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("UserInfo");

        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.articleFragment);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.articleFragment) {

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


        News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Solar_News_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

        MyFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Customer_Feedback_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

        Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Customer_Display_All_Feedback_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        Articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Customer_View_Articles_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });



    }
}