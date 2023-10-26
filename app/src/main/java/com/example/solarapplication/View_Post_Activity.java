package com.example.solarapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Locale;
import android.content.Context;
public class View_Post_Activity extends AppCompatActivity {

    ImageView image,back;
    TextView  productName,  brandName,  price,  Description,  Category,  warrantyPeriod,status;

    Button Edit, Delete;

    private Products product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_post);

        back=findViewById(R.id.img23 );
        productName=findViewById(R.id.textView27 );
        brandName=findViewById(R.id. textView32);
        price=findViewById(R.id.textView33 );
        Description=findViewById(R.id.textView29 );
        Category=findViewById(R.id.category );
        warrantyPeriod=findViewById(R.id.WarrantyPeriod );
        status=findViewById(R.id.textView11);

        Edit=findViewById(R.id.edit);
        Delete=findViewById(R.id.delete);

        DBHelper dbh = new DBHelper(this);

        Intent intent = getIntent();
        product = (Products) intent.getSerializableExtra("PostInfo");

        status.setText("Status: "+product.getRequest());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }});

        if(product.getRequest().equals("Pending")){
            Edit.setBackgroundColor(ContextCompat.getColor(this, R.color.myColor3));
            Edit.setTextColor(ContextCompat.getColor(this, R.color.black));
            Edit.setText("Approve");

        }else{
            Edit.setBackgroundColor(ContextCompat.getColor(this, R.color.red_background)); // Replace R.color.green_background with your green background color resource
            Edit.setTextColor(ContextCompat.getColor(this, R.color.black_text)); // Set text color to black
            Edit.setText("Reject");


        }


        productName.setText(product.getProductName().toUpperCase(Locale.ROOT));
        brandName.setText(product.getBrandName());
        warrantyPeriod.setText(product.getWarrantyPeriod()+" Years Warranty");
        price.setText("Rs. "+product.getPrice()+".00/=");
        Description.setText(product.getDescription());
        Category.setText(product.getCategory());

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.getRequest().equals("Pending")){
                    dbh.updatePostStatus(product.getId(), "Approved");
                    Toast.makeText(getApplicationContext(), "Status Updated", Toast.LENGTH_SHORT).show();

                }else{
                    dbh.updatePostStatus(product.getId(), "Pending");
                    Toast.makeText(getApplicationContext(), "Status Updated", Toast.LENGTH_SHORT).show();


                }

                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }});
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean checkupdatedata=dbh.RemoveItem(product.getId());
                if (checkupdatedata==true){
                    Toast.makeText(getApplicationContext(), "Post Removed ", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(), "Failed to Remove Post ", Toast.LENGTH_SHORT).show();
                }

                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }});



        byte[] imageByteArray = product.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        ImageView imageView = findViewById(R.id.imageView9); // Replace "R.id.image" with the actual ID of your ImageView
        imageView.setImageBitmap(bitmap);





    }

    private byte[] imageViewToByte(@NonNull ImageView imageView) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;
    }
}