package com.example.solarapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Supplier_Search_Products_Activity extends AppCompatActivity {

    TextView search, Home;
    ImageView BtnSearch, Back;
    ListView listView;
    EditText SearchText;

    ArrayList<Products> arrayList;
    Supplier_Post_Adapter adapter;
    DBHelper dbh = new DBHelper(this);
    private  Suppliers account;

    @Override
    public void onBackPressed() {
        // Create an intent to navigate to the Customer_Home_Activity
        super.onBackPressed();
        Intent intent = new Intent(this, View_Supplier_Products_Activity.class);
        intent.putExtra("UserInfo", account);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_supplier_search_products);


        Back=findViewById(R.id.img23);
        listView = findViewById(R.id.listView);
        BtnSearch=findViewById(R.id.btn_Search);
        SearchText=findViewById(R.id.txt_S_Name);

        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra("SEARCH_QUERY");
        account = (Suppliers) intent.getSerializableExtra("UserInfo");

        if (account != null) {
            arrayList = dbh.SearchSupplierProduct(account.getId(), searchQuery);

            if (arrayList.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Searched Product Not Found.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Supplier_Search_Products_Activity.this, View_Supplier_Products_Activity.class);
                                intent.putExtra("UserInfo", account);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                adapter = new Supplier_Post_Adapter(this, arrayList, dbh,account);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        } else {
            // Handle the case when the account information is not properly retrieved
            Toast.makeText(this, "Failed to load user data", Toast.LENGTH_SHORT).show();
        }

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Supplier_Search_Products_Activity.this, View_Supplier_Products_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }});



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


                    Intent intent = new Intent(Supplier_Search_Products_Activity.this, Supplier_Search_Products_Activity.class);
                    intent.putExtra("UserInfo", account);
                    intent.putExtra("SEARCH_QUERY", searchQuery);
                    startActivity(intent);
                }
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
                    listView.setVisibility(View.INVISIBLE);
                } else { // If keyboard is not visible
                    listView.setVisibility(View.VISIBLE);
                }
            }
        });



    }




    }
