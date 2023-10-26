package com.example.solarapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Customer_search_Products_Activity extends AppCompatActivity {

    ListView Listview;
    ArrayList<Products> arrayList;
    Product_Adapter adapter;
    ImageView BtnSearch, Back, Category1, Category2,Category3;
    EditText SearchText;
    DBHelper dbh = new DBHelper(this);
    BottomNavigationView bottomNavigationView;
    private  Account account;

    @Override
    public void onBackPressed() {
        // Create an intent to navigate to the Customer_Home_Activity
        super.onBackPressed();
        Intent intent = new Intent(this, View_Products_Activity.class);
        intent.putExtra("UserInfo", account);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customer_search_products);

        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.storeFragment);
        Listview = findViewById(R.id.listView);
        SearchText=findViewById(R.id.editTextTextPersonName4);
        Category1=findViewById(R.id.img_pannel_1);
        Category2=findViewById(R.id.img_launcher_icon3);
        Category3=findViewById(R.id.img_launcher_icon2);
        BtnSearch=findViewById(R.id.btn_Search);

        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("UserInfo");
        String searchQuery = intent.getStringExtra("SEARCH_QUERY");

        arrayList = dbh.searchProductsByRequest(searchQuery);
        if (arrayList.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Searched Products Not Found.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Customer_search_Products_Activity.this, View_Products_Activity.class);
                            intent.putExtra("UserInfo", account);
                            intent.removeExtra("SEARCH_QUERY");
                            startActivity(intent);
                            overridePendingTransition(0, 0);

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            adapter = new Product_Adapter(this, arrayList,dbh,account);
            Listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.storeFragment) {

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


                } else if (item.getItemId() == R.id.articleFragment) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), Article_Activity.class);
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

        final View mainLayout = findViewById(R.id.main_layout);
        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                mainLayout.getWindowVisibleDisplayFrame(r);

                int screenHeight = mainLayout.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;

                if (keypadHeight > screenHeight * 0.15) { // If keyboard is visible
                    Listview.setVisibility(View.INVISIBLE);
                } else { // If keyboard is not visible
                    Listview.setVisibility(View.VISIBLE);
                }
            }
        });

        Category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer_search_Products_Activity.this, Customer_search_Products_Activity.class);
                intent.putExtra("SEARCH_QUERY", "Solar-Panels");
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer_search_Products_Activity.this, Customer_search_Products_Activity.class);
                intent.putExtra("SEARCH_QUERY", "Solar-Battery");
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer_search_Products_Activity.this, Customer_search_Products_Activity.class);
                intent.putExtra("SEARCH_QUERY", "Solar-Inverter");
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


        BtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = SearchText.getText().toString().trim();
                if (searchQuery.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.error);
                    builder.setMessage(R.string.fieldes_cant_be_empty);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                } else {


                    Intent intent = new Intent(Customer_search_Products_Activity.this, Customer_search_Products_Activity.class);
                    intent.putExtra("SEARCH_QUERY", searchQuery);
                    intent.putExtra("UserInfo", account);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            }
        });

    }
}