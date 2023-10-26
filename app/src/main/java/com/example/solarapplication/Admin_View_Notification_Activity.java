package com.example.solarapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_View_Notification_Activity extends AppCompatActivity {
    ListView listView;
    ImageView Back;
    ArrayList<Inquiries> arrayList;
    Admin_Notification_Adapter adapter;
    DBHelper dbh = new DBHelper(this);
    @Override
    public void onBackPressed() {
        // Create an intent to navigate to the Customer_Home_Activity
        super.onBackPressed();
        Intent intent = new Intent(this, Admin_Home_Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_view_notification);

        listView = findViewById(R.id.listView);
        Back=findViewById(R.id.img23);
        showPostData();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_View_Notification_Activity.this, Admin_Home_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }});


    }
    private void showPostData() {
        try {
            arrayList = dbh.getAllInquiries();

            if (arrayList.isEmpty()) {
                Toast.makeText(this, "You have no notification yet", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Admin_View_Notification_Activity.this, Admin_Home_Activity.class);
                startActivity(intent);
                finish(); // Close the current activity
                overridePendingTransition(0,0);
            }else {
            adapter = new Admin_Notification_Adapter(this, arrayList, dbh); // Pass the account
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "You have no notification yet", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Admin_View_Notification_Activity.this, Admin_Home_Activity.class);
            startActivity(intent);
            finish(); // Close the current activity
            overridePendingTransition(0,0);
        }
    }
}