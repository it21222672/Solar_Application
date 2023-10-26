package com.example.solarapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Journalist_Manage_Articles_Activity extends AppCompatActivity {
    ImageView BtnSearch, Back;
    ListView listview;
    EditText SearchText;
    ArrayList<Article> arrayList;
    Journalist_Manage_Articles_Adapter adapter;
    private  Journalist account;
    DBHelper dbh = new DBHelper(this);

    @Override
    public void onBackPressed() {
        // Create an intent to navigate to the Customer_Home_Activity
        super.onBackPressed();
        Intent intent = new Intent(this, Journalist_Home_Activity.class);
        intent.putExtra("UserInfo", account);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_journalist_manage_articles);


        Intent intent = getIntent();
        account = (Journalist) intent.getSerializableExtra("UserInfo");

        Back=findViewById(R.id.img23);
        listview = findViewById(R.id.listView);
        BtnSearch=findViewById(R.id.btn_Search);

        showPostData();


        Back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Journalist_Manage_Articles_Activity.this, Journalist_Home_Activity.class);
            intent.putExtra("UserInfo", account);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }});





   /* final View mainLayout = findViewById(R.id.main_layout);
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
    });*/



}

    private void showPostData() {
        try {

            arrayList = dbh.getJournalistNews(account.getId());
            if (arrayList.isEmpty()) {
                // If the arrayList is empty, show a Toast message
                Toast.makeText(this, "Articles not added yet", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Journalist_Manage_Articles_Activity.this, Journalist_Home_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(0, 0);
            } else {
                adapter = new Journalist_Manage_Articles_Adapter(this, arrayList, dbh, account); // Pass the account
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Article not added yet", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Journalist_Manage_Articles_Activity.this, Journalist_Home_Activity.class);
            intent.putExtra("UserInfo", account);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }
}