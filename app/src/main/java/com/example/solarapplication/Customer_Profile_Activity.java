package com.example.solarapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class Customer_Profile_Activity extends AppCompatActivity {
    private  Account account;
    BottomNavigationView bottomNavigationView;
    Button logout,contact1,ChatBot1,ChatBot3;
    TextView TxtUser,TextEmail;
    Button OderPageText;
    ImageView ProfilePic,OrderPageImage,contact2,ChatBot2,LogOut;


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
        setContentView(R.layout.activity_customer_profile);

       logout=findViewById(R.id.signout);
        TxtUser =findViewById(R.id.textView2);
        TextEmail=findViewById(R.id.textView3);
        ProfilePic=findViewById(R.id.imageView2);
        OderPageText=findViewById(R.id.notification);
        OrderPageImage=findViewById(R.id.imageView2);

        contact1= findViewById(R.id.repair);
        contact2= findViewById(R.id.imageView14);

        ChatBot2= findViewById(R.id.imageView9);
        ChatBot1=findViewById(R.id.helpMe);
        ChatBot3=findViewById(R.id.setting);

        LogOut= findViewById(R.id.imageView10);

        Intent intent = getIntent();
        if (intent != null) {
            account = (Account) intent.getSerializableExtra("UserInfo");
            if (account != null) {
                TxtUser.setText(account.getUsername().toUpperCase());
                TextEmail.setText(account.getEmail());
            } else {
                Toast.makeText(Customer_Profile_Activity.this, "Fail to Load User Data", Toast.LENGTH_SHORT).show();
            }
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.profileFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.profileFragment) {

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


                } else if (item.getItemId() == R.id.storeFragment) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), View_Products_Activity.class);
                    intent.putExtra("UserInfo", account);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }

                return false;
            }
        });

        ChatBot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
              //  intent.setClass(getApplicationContext(), Online_Chat_Bot_Activity.class);
                intent.setClass(getApplicationContext(), ChatBot_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        ChatBot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
               // intent.setClass(getApplicationContext(), Online_Chat_Bot_Activity.class);
                intent.setClass(getApplicationContext(), ChatBot_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        ChatBot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        contact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Contact_Page_Activity2.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        contact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Contact_Page_Activity2.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(Customer_Profile_Activity.this);
                alertDialog2.setTitle("Confirm Logout...");
                alertDialog2.setMessage("Are you sure you want logout?");
                alertDialog2.setIcon(R.drawable.exit);
                // Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Customer_Profile_Activity.this, Login_Activity.class);
                                startActivity(intent);
                                account = null;
                                finish();
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            }
                        });
                // Setting Negative "NO" Btn
                alertDialog2.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });
                // Showing Alert Dialog
                alertDialog2.show();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(Customer_Profile_Activity.this);
                alertDialog2.setTitle("Confirm Logout...");
                alertDialog2.setMessage("Are you sure you want logout?");
                alertDialog2.setIcon(R.drawable.exit);
                // Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Customer_Profile_Activity.this, Login_Activity.class);
                                startActivity(intent);
                                account = null;
                                finish();
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            }
                        });
                // Setting Negative "NO" Btn
                alertDialog2.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });
                // Showing Alert Dialog
                alertDialog2.show();

            }
        });

        OderPageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Customer_View_Orders_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        OrderPageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Customer_View_Orders_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}