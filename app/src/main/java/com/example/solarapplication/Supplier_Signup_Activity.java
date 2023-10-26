package com.example.solarapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Supplier_Signup_Activity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 100;
    ImageView image;
    EditText Company_Name,Registration_No, Address, Contact,Position, email, web,contact_no,Description, Password, RePassword;
    Button register;
    TextView back,Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_supplier_signup);


        DBHelper dbh = new DBHelper(this);

        Login=findViewById(R.id.already_member);
        image=findViewById(R.id.imageView15);
        Company_Name=findViewById(R.id.companyName_signupe_signup);
        Registration_No=findViewById(R.id.Business_Registration_No);
        Address=findViewById(R.id.Business_Address);
        Contact=findViewById(R.id.textPersonName);
        Position=findViewById(R.id.Personposition);
        email=findViewById(R.id.E_mail);
        web=findViewById(R.id.webaddress);
        contact_no=findViewById(R.id.Contact_No);
        Description=findViewById(R.id.Company_Description);
        Password=findViewById(R.id.password);
        RePassword=findViewById(R.id.re_password);

        register=findViewById(R.id.signup_btn1);
        back=findViewById(R.id.already_member);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Supplier_Signup_Activity.this, Login_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }});



        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent objectIntent = new Intent();
                objectIntent.setType("image/*");

                objectIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(objectIntent, PICK_IMAGE_REQUEST);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = Contact.getText().toString().trim();
                String pass = Password.getText().toString().trim();
                String repass = RePassword.getText().toString().trim();
                String CompanyName = Company_Name.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String RegistrationNo = Registration_No.getText().toString().trim();
                String CompanyAddress = Address.getText().toString().trim();
                String CompanyPosition = Position.getText().toString().trim();
                String Pnumber = contact_no.getText().toString().trim();
                String Data = Description.getText().toString().trim();
                String webAddress = web.getText().toString().trim();
                byte[] img = imageViewToByte(image);


                if (CompanyName.isEmpty()){
                    showError(Company_Name, "Fields Can't be Empty");
                }else if (!CompanyName.matches("^[A-Za-z]+$")) {
                    showError(Company_Name, "Company Name is Invalid");
                }else if (RegistrationNo.isEmpty()){
                    showError(Registration_No, "Fields Can't be Empty");
                }else if (CompanyAddress.isEmpty()){
                    showError(Address, "Fields Can't be Empty");
                }else if (user.isEmpty()){
                    showError(Contact, "Fields Can't be Empty");
                }else if (!user.matches("^[A-Za-z]+$")) {
                    showError(Contact, "Username is Invalid");
                } else if (CompanyPosition.isEmpty()){
                    showError(Position, "Fields Can't be Empty");
                }else if(Email.isEmpty()){
                    showError(email, "Fields Can't be Empty");
                }else if (!Email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    showError(email, "Email is Invalid");
                }else if(Pnumber.isEmpty()){
                    showError(contact_no, "Fields Can't be Empty");
                }else if (!Pnumber.matches("^[0-9]*$")) {
                    showError(contact_no, "Phone Number is Invalid");
                }
                else if (webAddress.isEmpty()){
                    showError(web, "Fields Can't be Empty");
                }else if (!Patterns.WEB_URL.matcher(webAddress).matches()) {
                    showError(web, "Web Address is Invalid");
                }else if (Data.isEmpty()){
                    showError(Description, "Fields Can't be Empty");
                }else if(pass.isEmpty()) {
                    showError(Password, "Fields Can't be Empty");
                }
                else if(repass.isEmpty()) {
                    showError(RePassword, "Fields Can't be Empty");
                }
                else if (Password.getText().toString().length() < 6) {
                    showError(Password, "Password need to be more than 6 Characters");
                } else if (!Password.getText().toString().equals(RePassword.getText().toString())) {
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

                }  else if  (!image.getDrawable().isFilterBitmap()){
                    Toast.makeText(getApplicationContext(), "Please Insert a image ", Toast.LENGTH_LONG).show();
                } else if(user.equals("admin")||user.equals("Admin")){
                    showError(Contact, "Invalid name try another");
                }else {

                    if (pass.equals(repass)) {


                        Boolean CheckSuppler = dbh.checkSuppler(Email);

                        Boolean checkUser = dbh.checkSuppler(Email);
                        Boolean checkJournalist = dbh.checkEmailJournalist(Email);

                        if(CheckSuppler== true){
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

                        }else if(checkUser== true){
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

                        }else if(checkJournalist== true){
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

                        else{
                            Boolean insert = dbh.SupplierReg(img,CompanyName,RegistrationNo,  CompanyAddress, user, CompanyPosition, Email , webAddress, Pnumber , Data, repass, "Pending"  );
                            if (insert == true) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                builder.setTitle(R.string.Done);
                                builder.setMessage(R.string.registered_successfully);
                                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();

                                        Intent intent = new Intent(Supplier_Signup_Activity.this, Login_Activity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                    }
                                });
                                builder.show();
                            } else {
                                Toast.makeText(Supplier_Signup_Activity.this, "Registered Failed", Toast.LENGTH_SHORT).show();
                            }

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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                // Check the image size
                int imageSizeBytes = inputStream.available();
                int maxSizeBytes = 1024 * 1024; // 1MB
                if (imageSizeBytes > maxSizeBytes) {
                    // Image size exceeds 1MB, display a toast message
                    Toast.makeText(getApplicationContext(), "Select an image less than 1MB", Toast.LENGTH_SHORT).show();
                } else {
                    // Image size is within the limit, decode and set the image
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    image.setImageBitmap(bitmap);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] imageViewToByte(@NonNull ImageView imageView) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}