package com.example.solarapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_Manage_News_Activity extends AppCompatActivity {

    ImageView Back;
    ListView Listview;
    Button pending,all,approved;
    ArrayList<Article> arrayList;
    Admin_Article_Adapter adapter;
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
        setContentView(R.layout.activity_admin_manage_news);

        Back=findViewById(R.id.img23);
        Listview = findViewById(R.id.listView);
        all=findViewById(R.id.btn_all);
        approved=findViewById(R.id.btn_approved);
        pending=findViewById(R.id.btn_Pending);

        showPostData();

        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Manage_News_Activity.this, Admin_search_Articles_Activity.class);//Articles
                intent.putExtra("SEARCH_QUERY", "Approved");
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Manage_News_Activity.this, Admin_search_Articles_Activity.class);//Articles
                intent.putExtra("SEARCH_QUERY", "Pending");
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Manage_News_Activity.this, Admin_Manage_News_Activity.class);//Articles
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Manage_News_Activity.this, Admin_Home_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }});

    }

    private void showPostData() {
        try {
            arrayList = dbh.getAllNewsPost();


            if (arrayList.isEmpty()) {
                Toast.makeText(Admin_Manage_News_Activity.this, "No Articles add yet", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Admin_Manage_News_Activity.this, Admin_Home_Activity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }else {

                adapter = new Admin_Article_Adapter(this, arrayList, dbh);
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