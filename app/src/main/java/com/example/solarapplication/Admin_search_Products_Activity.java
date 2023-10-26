package com.example.solarapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Admin_search_Products_Activity extends AppCompatActivity {
    TextView search, Home;
    ImageView BtnSearch, Back;
    ListView listView;
    EditText SearchText;
    Button pending,all,approved;
    ArrayList<Products> arrayList;
    Admin_Product_Adapter adapter;
    DBHelper dbh = new DBHelper(this);
    @Override
    public void onBackPressed() {
        // Create an intent to navigate to the Customer_Home_Activity
        super.onBackPressed();
        Intent intent = new Intent(this, Admin_View_Products_Activity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_search_products);


        Back=findViewById(R.id.img23);
        listView = findViewById(R.id.listView);
        BtnSearch=findViewById(R.id.btn_Search);
        SearchText=findViewById(R.id.txt_S_Name);

        all=findViewById(R.id.btn_all);
        approved=findViewById(R.id.btn_approved);
        pending=findViewById(R.id.btn_Pending);

        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra("SEARCH_QUERY");

        arrayList = dbh.searchAllproducts(searchQuery);
        if (arrayList.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Searched Products Not Found.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Admin_search_Products_Activity.this, Admin_View_Products_Activity.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            adapter = new Admin_Product_Adapter(this, arrayList,dbh);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_search_Products_Activity.this, Admin_search_Products_Activity.class);
                intent.putExtra("SEARCH_QUERY", "Approved");
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_search_Products_Activity.this, Admin_search_Products_Activity.class);
                intent.putExtra("SEARCH_QUERY", "Pending");
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_search_Products_Activity.this, Admin_View_Products_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_search_Products_Activity.this, Admin_View_Products_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0,0);
            }});


    }
}