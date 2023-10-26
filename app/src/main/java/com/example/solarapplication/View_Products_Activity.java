package com.example.solarapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class View_Products_Activity extends AppCompatActivity {
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
        Intent intent = new Intent(this, Customer_Home_Activity.class);
        intent.putExtra("UserInfo", account);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_products);

        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("UserInfo");

        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.storeFragment);
        Listview = findViewById(R.id.listView);
        SearchText=findViewById(R.id.editTextTextPersonName4);
        Category1=findViewById(R.id.img_pannel_1);
        Category2=findViewById(R.id.img_launcher_icon3);
        Category3=findViewById(R.id.img_launcher_icon2);
        BtnSearch=findViewById(R.id.btn_Search);
        showPostData();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.storeFragment) {
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
                    bottomNavigationView.setVisibility(View.INVISIBLE);
                } else { // If keyboard is not visible
                    Listview.setVisibility(View.VISIBLE);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });

        Category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_Products_Activity.this, Customer_search_Products_Activity.class);
                intent.putExtra("UserInfo", account);
                intent.putExtra("SEARCH_QUERY", "Solar-Panels");
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_Products_Activity.this, Customer_search_Products_Activity.class);
                intent.putExtra("UserInfo", account);
                intent.putExtra("SEARCH_QUERY", "Solar-Battery");
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_Products_Activity.this, Customer_search_Products_Activity.class);
                intent.putExtra("SEARCH_QUERY", "Solar-Inverter");
                intent.putExtra("UserInfo", account);
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
                    Intent intent = new Intent(View_Products_Activity.this, Customer_search_Products_Activity.class);
                    intent.putExtra("SEARCH_QUERY", searchQuery);
                    intent.putExtra("UserInfo", account);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            }
        });
    }
    private void showPostData() {
        try {
            arrayList = dbh.getApprovedProducts();
            adapter = new Product_Adapter(this, arrayList, dbh,account);
            Listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            if (arrayList.isEmpty()) {
                Toast.makeText(this, "There are no products to display.", Toast.LENGTH_SHORT).show();
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