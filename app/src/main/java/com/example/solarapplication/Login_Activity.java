package com.example.solarapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;


public class Login_Activity extends AppCompatActivity {

     Button login_btn;
     TextView text_register;
    ImageView chatbot;
    EditText Lusername, Lpassword;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);


        login_btn = findViewById(R.id.login_btn);
        text_register = findViewById(R.id.text_register);
        Lusername = findViewById(R.id.reNo);
        Lpassword = findViewById(R.id.editTextTextPersonName);
        chatbot=findViewById(R.id.imageView15);
        DBHelper dbh = new DBHelper(this);

     /*   chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatBot_Activity.class);
                startActivity(intent);
            }
        });*/


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = Lusername.getText().toString().trim();
                String pass = Lpassword.getText().toString().trim();

                ArrayList<Account> userDetails = dbh.ValidLogin(Lusername.getText().toString(), Lpassword.getText().toString().trim());
              //  Suppliers SupplierDetails = dbh.ValidLogin2(Lusername.getText().toString(), Lpassword.getText().toString());
                ArrayList<Suppliers> SupplierDetails = dbh.ValidLogin2(Lusername.getText().toString(), Lpassword.getText().toString().trim());
                ArrayList<Journalist> JournalistDetails = dbh.ValidLoginJournalist(Lusername.getText().toString(), Lpassword.getText().toString().trim());


                  if (user.isEmpty()) {
                       showError(Lusername, "Fields Can't be Empty");
                   } else if (pass.isEmpty()) {
                       showError(Lpassword, "Fields Can't be Empty");
                   }  else if (user.equals("admin") && pass.equals("1234567")) {
                        Toast.makeText(Login_Activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Admin_Home_Activity.class);
                        startActivity(intent);

                   }else if (userDetails.size() != 0) {
                        Account account = userDetails.get(0);
                        Toast.makeText(Login_Activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Customer_Home_Activity.class);
                        intent.putExtra("UserInfo", account);
                        startActivity(intent);
                    } else if (JournalistDetails.size() != 0) {
                    Journalist account = JournalistDetails.get(0);
                    Toast.makeText(Login_Activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login_Activity.this, Journalist_Home_Activity.class);
                    intent.putExtra("UserInfo", account);
                    startActivity(intent);
                   }

                  else if (SupplierDetails.size() != 0) {
                        Suppliers account = SupplierDetails.get(0);
                        Toast.makeText(Login_Activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login_Activity.this, Supplier_Dashbord_Activity.class);
                        intent.putExtra("UserInfo", account);
                        startActivity(intent);
                    }

                  else {
                      // Invalid login credentials
                      // Display an error message or prevent login
                  AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle(R.string.error);
                        builder.setMessage("User is not approved or Invalid login credentials");
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();

                            }
                        });
                        builder.show();
                    }






            }
        });


        text_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(Login_Activity.this);


                // Inflate a layout to use as the dialog's view
                View view = LayoutInflater.from(Login_Activity.this).inflate(R.layout.dialog_layout, null);
                TextView CustomerButton = view.findViewById(R.id.customer);
                TextView SupplierButton = view.findViewById(R.id.supplier);
                TextView JournalistButton= view.findViewById(R.id.Journalist);

                // Add the view to the dialog builder
                builder.setView(view);


                // Set up the negative button (Cancel button)
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing - the dialog will be dismissed automatically
                    }
                });

              // Show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                // Set an OnClickListener for CustomerButton
                CustomerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Start the new activity when CustomerButton is clicked
                        Intent intent = new Intent(Login_Activity.this, Signup_Activity.class);
                        startActivity(intent);
                        alertDialog.dismiss(); // Close the AlertDialog
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });

                SupplierButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Start the new activity when CustomerButton is clicked
                        Intent intent = new Intent(Login_Activity.this, Supplier_Signup_Activity.class);
                        startActivity(intent);
                        alertDialog.dismiss(); // Close the AlertDialog
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
                JournalistButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Start the new activity when CustomerButton is clicked
                        Intent intent = new Intent(Login_Activity.this, Journalist_Signup_Activity.class);
                        startActivity(intent);
                        alertDialog.dismiss(); // Close the AlertDialog
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });

            }
        });

    }
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}