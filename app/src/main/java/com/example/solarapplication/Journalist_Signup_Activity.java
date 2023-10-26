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
import android.widget.TextView;
import android.widget.Toast;

public class Journalist_Signup_Activity extends AppCompatActivity {
    TextView Login;
    EditText Semail, Spassword, Spassword2, Sname, Spnumber,NicNumber;
    Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_journalist_signup);

        DBHelper dbh = new DBHelper(this);
        Login = findViewById(R.id.already_member);
        Sname = findViewById(R.id.username_signup);
        Semail = findViewById(R.id.email_signup);
        Spassword  = findViewById(R.id.password_signup);
        Spassword2 = findViewById(R.id.re_password_signup);
        Spnumber = findViewById(R.id.contact_signup);
        NicNumber = findViewById(R.id.nicNumber);
        Register = findViewById(R.id.signup_btn1);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Journalist_Signup_Activity.this, Login_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = Sname.getText().toString().trim();
                String pass = Spassword.getText().toString().trim();
                String repass = Spassword2.getText().toString().trim();
                String username = Sname.getText().toString().trim();
                String email = Semail.getText().toString().trim();
                String Pnumber = Spnumber.getText().toString().trim();
                String Nic = NicNumber.getText().toString().trim();

                if (user.isEmpty()){
                    showError(Sname, "Fields Can't be Empty");
                }else if (!username.matches("^[A-Za-z]+$")) {
                    showError(Sname, "Username is Invalid");
                } else if(email.isEmpty()){
                    showError(Semail, "Fields Can't be Empty");
                }else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    showError(Semail, "Email is Invalid");
                }else if(Pnumber.isEmpty()){
                    showError(Spnumber, "Fields Can't be Empty");
                }else if (!Pnumber.matches("^[0-9]*$")) {
                    showError(Spnumber, "Phone Number is Invalid");
                }else if(Nic.isEmpty()) {
                    showError(NicNumber, "Fields Can't be Empty");
                }else if(!Nic.matches("^(\\d{9}[xXvV]|\\d{12})$")) {
                    showError(NicNumber, "Nic is is Invalid");
                }else if(pass.isEmpty()) {
                    showError(Spassword, "Fields Can't be Empty");
                }else if(repass.isEmpty()) {
                    showError(Spassword2, "Fields Can't be Empty");
                } else if (Spassword.getText().toString().length() < 6) {
                    showError(Spassword, "Password need to be more than 6 Characters");
                } else if (!Spassword.getText().toString().equals(Spassword2.getText().toString())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle(R.string.error);
                    builder.setMessage(R.string.password_and_confirm_password_should_match_to_register);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();

                }   else if(user.equals("admin")||user.equals("Admin")){
                    showError(Sname, "Invalid name try another");
                }else {

                    if (pass.equals(repass)) {

                        Boolean checkUser = dbh.checkSuppler(email);
                        Boolean checkemailaddress = dbh.checkEmail(email);
                        Boolean checkJournalist = dbh.checkEmailJournalist(email);

                        if(checkemailaddress== true){

                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setTitle(R.string.error);
                            builder.setMessage(R.string.user_email);
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            builder.show();

                        }
                        else if (checkUser == false) {
                            Boolean insert = dbh.insertJournalist(user,email,  Pnumber,Nic, pass);
                            if (insert == true) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                builder.setTitle(R.string.Done);
                                builder.setMessage(R.string.registered_successfully);
                                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        Intent intent = new Intent(Journalist_Signup_Activity.this, Login_Activity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                    }
                                });
                                builder.show();
                            } else {
                                Toast.makeText(Journalist_Signup_Activity.this, "Registered Failed", Toast.LENGTH_SHORT).show();
                            }
                        }else if (checkJournalist == false) {
                            Boolean insert = dbh.insertJournalist(user,email,  Pnumber,Nic, pass);
                            if (insert == true) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                builder.setTitle(R.string.Done);
                                builder.setMessage(R.string.registered_successfully);
                                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        Intent intent = new Intent(Journalist_Signup_Activity.this, Login_Activity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                    }
                                });
                                builder.show();
                            } else {
                                Toast.makeText(Journalist_Signup_Activity.this, "Registered Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setTitle(R.string.error);
                            builder.setMessage(R.string.user_exitsts);
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            builder.show();
                        }


                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle(R.string.error);
                        builder.setMessage(R.string.passwords_not_matching);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.show();
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