package com.example.solarapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class Admin_search_Articles_Activity extends AppCompatActivity {

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
        Intent intent = new Intent(this, Admin_Manage_News_Activity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_search_articles);

        Back=findViewById(R.id.img23);
        Listview = findViewById(R.id.listView);
        all=findViewById(R.id.btn_all);
        approved=findViewById(R.id.btn_approved);
        pending=findViewById(R.id.btn_Pending);

        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra("SEARCH_QUERY");

        arrayList = dbh.searchAllArticles(searchQuery);
        if (arrayList.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Articles Not Found.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Admin_search_Articles_Activity.this, Admin_Manage_News_Activity.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            adapter = new Admin_Article_Adapter(this, arrayList, dbh);
            Listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_search_Articles_Activity.this, Admin_search_Articles_Activity.class);//Articles
                intent.putExtra("SEARCH_QUERY", "Approved");
                startActivity(intent);
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_search_Articles_Activity.this, Admin_search_Articles_Activity.class);//Articles
                intent.putExtra("SEARCH_QUERY", "Pending");
                startActivity(intent);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_search_Articles_Activity.this, Admin_search_Articles_Activity.class);//Articles
                startActivity(intent);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_search_Articles_Activity.this, Admin_Home_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0,0);
            }});

    }

    }
