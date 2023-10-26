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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_View_Supplier_Activity extends AppCompatActivity {

    TextView search, Home;
    ImageView BtnSearch, Back;
    ListView listView;
    EditText SearchText;
    Button pending,all,approved;
    ArrayList<Suppliers> arrayList;
    admin_View_Supplier_Adapter adapter;
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
        setContentView(R.layout.activity_admin_view_supplier);


        Back=findViewById(R.id.img23);
        listView = findViewById(R.id.listView);
        BtnSearch=findViewById(R.id.btn_Search);
        SearchText=findViewById(R.id.txt_S_Name);

        all=findViewById(R.id.btn_all);
        approved=findViewById(R.id.btn_approved);
        pending=findViewById(R.id.btn_Pending);

        showPostData();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_View_Supplier_Activity.this, Admin_Home_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }});

        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_View_Supplier_Activity.this, Admin_search_Supplier_Activity.class);
                intent.putExtra("SEARCH_QUERY", "Approved");
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_View_Supplier_Activity.this, Admin_search_Supplier_Activity.class);
                intent.putExtra("SEARCH_QUERY", "Pending");
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_View_Supplier_Activity.this, Admin_View_Supplier_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
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


                    Intent intent = new Intent(Admin_View_Supplier_Activity.this, Admin_search_Supplier_Activity.class);
                    intent.putExtra("SEARCH_QUERY", searchQuery);
                    //  intent.putExtra("UserInfo", account);
                    startActivity(intent);
                    overridePendingTransition(0,0);
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


    private void showPostData() {
        try {
            arrayList = dbh.getSuppliersPost();

            if (arrayList.isEmpty()) {
                Toast.makeText(this, "No Suppliers to Display yet", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Admin_View_Supplier_Activity.this, Admin_Home_Activity.class);
                startActivity(intent);
                finish(); // Close the current activity
                overridePendingTransition(0, 0);
            }else {
                adapter = new admin_View_Supplier_Adapter(this, arrayList,dbh);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "No Suppliers to Display yet", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Admin_View_Supplier_Activity.this, Admin_Home_Activity.class);
            startActivity(intent);
            finish(); // Close the current activity
            overridePendingTransition(0, 0);
        }
    }
}