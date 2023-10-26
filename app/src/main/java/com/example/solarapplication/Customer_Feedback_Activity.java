package com.example.solarapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Customer_Feedback_Activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private  Account account;
    EditText Feedback;
    Button Submit;
    ListView Listview;
    ArrayList<Feedback> arrayList;
    Customer_Feedback_Adapter adapter;
    DBHelper dbh = new DBHelper(this);
    RatingBar rbStars;
    private Handler handler = new Handler();

    @Override
    public void onBackPressed() {
        // Create an intent to navigate to the Customer_Home_Activity
        super.onBackPressed();
        Intent intent = new Intent(this, Article_Activity.class);
        intent.putExtra("UserInfo", account);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customer_feedback);


        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("UserInfo");


        rbStars = findViewById(R.id.rbStars);
        // Get the rating as a float
        float rating = rbStars.getRating();
        int intValue = (int) rating;


        // Convert the float rating to an integer if needed

      //  String ratingString = String.valueOf(rating);

        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.articleFragment);
        Feedback =findViewById(R.id.feedback_text);
        Submit=findViewById(R.id.btnsubmit);
        Listview=findViewById(R.id.listView);
        int id=account.getId();



        showPostData();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.articleFragment) {
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


                } else if (item.getItemId() == R.id.homeFragment) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), Customer_Home_Activity.class);
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
                    bottomNavigationView.setVisibility(View.INVISIBLE);
                    Listview.setVisibility(View.INVISIBLE);
                } else { // If keyboard is not visible
                    Listview.setVisibility(View.VISIBLE);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });

        rbStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 0) {

                    float Rating = 0;
                    Submit.setTag(Rating);
                } else if (rating == 1) {

                    float Rating = 1;
                    Submit.setTag(Rating);
                } else if (rating == 2 )  {

                    float Rating =2;
                    Submit.setTag(Rating);
                }else if (rating == 3){
                    float Rating =3;
                    Submit.setTag(Rating);
                }
                else if (rating == 4) {
                    float Rating =4;
                    Submit.setTag(Rating);
                } else if (rating == 5) {
                    float Rating =5;
                    Submit.setTag(Rating);
                } else {
                    float Rating = 0;
                    Submit.setTag(Rating);
                }
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float ratingValue = (Float) Submit.getTag();
                String feedback = Feedback.getText().toString().trim();
                if (feedback.isEmpty()){
                    showError(Feedback, "Fields Can't be Empty");
                } else if (ratingValue != null) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Customer_Feedback_Activity.this);
                    alertDialog.setTitle("Confirm...");
                    alertDialog.setMessage("Are you sure you want to Feedback");
                    alertDialog.setIcon(R.drawable.ic_right);
                    // Setting Positive "Yes" Btn
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Boolean insert = dbh.insertFeedback(feedback, id,ratingValue ,"Pending");
                                    if (insert == true) {
                                        showPostData();
                                        Toast.makeText(Customer_Feedback_Activity.this, "We are appreciate for your feedbacks", Toast.LENGTH_SHORT).show();
                                        Feedback.setText("");
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed to feedback", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    // Setting Negative "NO" Btn
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    dialog.cancel();
                                }
                            });
                    // Showing Alert Dialog
                    alertDialog.show();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Customer_Feedback_Activity.this);
                    alertDialog.setTitle("Confirm...");
                    alertDialog.setMessage("Are you sure you want to Feedback");
                    alertDialog.setIcon(R.drawable.ic_right);
                    // Setting Positive "Yes" Btn
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Boolean insert = dbh.insertFeedback(feedback, id,0 ,"Pending");
                                    if (insert == true) {
                                        showPostData();
                                        Toast.makeText(Customer_Feedback_Activity.this, "We are appreciate for your feedbacks", Toast.LENGTH_SHORT).show();
                                        Feedback.setText("");
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed to feedback", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    // Setting Negative "NO" Btn
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    dialog.cancel();
                                }
                            });
                    // Showing Alert Dialog
                    alertDialog.show();

                }


            }
        });

    }


    private void showPostData() {
        try {
            arrayList = dbh.getUserFeedback(account.getId());
            if (arrayList.isEmpty()) {
                Toast.makeText(Customer_Feedback_Activity.this, "No feedback add yet", Toast.LENGTH_LONG).show();
            }else {
                adapter = new Customer_Feedback_Adapter(this, arrayList, dbh,account);
                Listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
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


    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}