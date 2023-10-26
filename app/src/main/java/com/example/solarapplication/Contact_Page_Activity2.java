package com.example.solarapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Contact_Page_Activity2 extends AppCompatActivity {

    Button submit;
    private  Account account;

    EditText Username, Email, Text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_contact_page2);
        submit=findViewById(R.id.button);
        Username=findViewById(R.id.editTextTextPersonName);
        Email=findViewById(R.id.editTextTextPersonName2);
        Text=findViewById(R.id.editTextTextPersonName3);
        DBHelper dbh = new DBHelper(this);
        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("UserInfo");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = Username.getText().toString().trim();
                String text = Text.getText().toString().trim();
                String email = Email.getText().toString().trim();

                if (user.isEmpty()){
                    showError(Username, "Fields Can't be Empty");
                }else if (!user.matches("^[A-Za-z]+$")) {
                    showError(Username, "Username is Invalid");
                } else if(email.isEmpty()){
                    showError(Email, "Fields Can't be Empty");
                }else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    showError(Email, "Email is Invalid");
                }else if(text.isEmpty()){
                    showError(Text, "Fields Can't be Empty");
                }else {
                    Boolean insert = dbh.insertInquiries(user, email, text);
                    if (insert == true) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle(R.string.Done);
                        builder.setMessage("Your message has sent");
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Username.setText(null);
                                Email.setText(null);
                                Text.setText(null);
                            }
                        });
                        builder.show();
                    } else {
                        Toast.makeText(Contact_Page_Activity2.this, "Failed to send", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


    }
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}