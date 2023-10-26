package com.example.solarapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Journalist_Home_Activity extends AppCompatActivity {

    Button add,pr,profile;
    private  Journalist account;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_journalist_home);

        add =findViewById(R.id.button1);
        pr =findViewById(R.id.button2);
        profile =findViewById(R.id.button3);

        Intent intent = getIntent();
        account = (Journalist) intent.getSerializableExtra("UserInfo");



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Journalist_Home_Activity.this, Add_Articles_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Journalist_Home_Activity.this, Journalist_Manage_Articles_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Journalist_Home_Activity.this, Journalist_Profile_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}