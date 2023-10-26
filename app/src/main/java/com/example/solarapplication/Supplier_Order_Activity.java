package com.example.solarapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Supplier_Order_Activity extends AppCompatActivity {

    TextView search, Home;
    ImageView BtnSearch, Back;
    ListView listView;
    EditText SearchText;

    ArrayList<Orders> arrayList;
    Supplier_Order_Adapter adapter;
    DBHelper dbh = new DBHelper(this);
    private  Suppliers account;

    @Override
    public void onBackPressed() {
        // Create an intent to navigate to the Customer_Home_Activity
        super.onBackPressed();
        Intent intent = new Intent(this, Supplier_Dashbord_Activity.class);
        intent.putExtra("UserInfo", account);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_supplier_order);

        Back=findViewById(R.id.img23);
        listView = findViewById(R.id.listView);


        Intent intent = getIntent();
        account = (Suppliers) intent.getSerializableExtra("UserInfo");

        showPostData();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Supplier_Order_Activity.this, Supplier_Dashbord_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }});



    }
    private void showPostData() {
        try {

            arrayList = dbh.getSupplerOrders(account.getId());
            adapter = new Supplier_Order_Adapter(this, arrayList, dbh, account); // Pass the account
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "You have no orders yet", Toast.LENGTH_SHORT).show();

            // Create an intent to go to Supplier_Dashbord_Activity
            Intent intent = new Intent(Supplier_Order_Activity.this, Supplier_Dashbord_Activity.class);
            intent.putExtra("UserInfo", account);
            startActivity(intent);
            finish(); // Close the current activity
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}