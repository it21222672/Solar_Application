package com.example.solarapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Supplier_Profile_Activity extends AppCompatActivity {
    Button logout;
    ImageView Back,image;
    private  Suppliers account;
    ImageView ProfilePic;
   TextView TxtUser,TextEmail;

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

        setContentView(R.layout.activity_supplier_profile);


        logout=findViewById(R.id.signout);
        Back=findViewById(R.id.img23);
        TxtUser =findViewById(R.id.textView2);
        TextEmail=findViewById(R.id.textView3);
        ProfilePic=findViewById(R.id.imageView2);



        Intent intent = getIntent();

        if (intent != null) {
            account = (Suppliers) intent.getSerializableExtra("UserInfo");
            if (account != null) {
                TxtUser.setText(account.getName());
                TextEmail.setText(account.getEmail());

                // Check if the 'account' object contains image data
                byte[] imageData = account.getImage(); // Replace 'getImageData' with the actual method you use to get the image data

                if (imageData != null) {
                    // Create a Bitmap from the byte array
                    Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    ProfilePic.setImageBitmap(imageBitmap);
                } else {
                    // Handle the case where image data is not available
                    // You can set a default image or show a placeholder
                    ProfilePic.setImageResource(R.drawable.profile_icon); // Replace with your placeholder image
                }


            } else {
                Toast.makeText(Supplier_Profile_Activity.this, "Failed to Load User Data", Toast.LENGTH_SHORT).show();
            }
        }








        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Supplier_Profile_Activity.this, Supplier_Dashbord_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }});

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(Supplier_Profile_Activity.this);
                alertDialog2.setTitle("Confirm Logout...");
                alertDialog2.setMessage("Are you sure you want logout?");

                alertDialog2.setIcon(R.drawable.exit);
                // Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();

                                // Remove user data
                                editor.remove("username");
                                editor.remove("usertype");
                                editor.apply();

                                Intent intent = new Intent(Supplier_Profile_Activity.this, Login_Activity.class);
                                startActivity(intent);
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



    }
}