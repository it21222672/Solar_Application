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
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Suppliers_add_data_Activity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 100;
    ImageView Back, image;
    EditText name, brand, price, warrantyperiod, description;
    Button add;
    Spinner SpinnerCategoryName;
    String[] Category = {"Solar-Panels","Solar-Battery", "Solar-Inverter"};
    private Suppliers account;


    @Override
    public void onBackPressed() {
        // Create an intent to navigate to the Customer_Home_Activity

        Intent intent = new Intent(this, Supplier_Dashbord_Activity.class);
        intent.putExtra("UserInfo", account);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_suppliers_add_data);

        DBHelper dbh = new DBHelper(this);
        Intent intent = getIntent();
        account = (Suppliers) intent.getSerializableExtra("UserInfo");

        Back = findViewById(R.id.img23);
        image = findViewById(R.id.imageView);
        add = findViewById(R.id.btn_Item);
        name = findViewById(R.id.product_name);
        brand = findViewById(R.id.product_Brand);
        price = findViewById(R.id.product_price);
        warrantyperiod = findViewById(R.id.WarrantyPeriod);
        description = findViewById(R.id.Description);
        SpinnerCategoryName = (Spinner) findViewById(R.id.sp_Category);


        // Retrieve the string array from resources
        String[] Category = getResources().getStringArray(R.array.categories);
         // Create an ArrayAdapter
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Category);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the adapter to the Spinner
        SpinnerCategoryName.setAdapter(ad);



        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Suppliers_add_data_Activity.this, Supplier_Dashbord_Activity.class);
                intent.putExtra("UserInfo", account);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent objectIntent = new Intent();
                objectIntent.setType("image/*");

                objectIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(objectIntent, PICK_IMAGE_REQUEST);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ProductName = name.getText().toString().trim();
                String BrandName = brand.getText().toString().trim();
                String Price = price.getText().toString().trim();
                String WarrantyPeriod = warrantyperiod.getText().toString().trim();
                String Description = description.getText().toString().trim();
                String Category = SpinnerCategoryName.getSelectedItem().toString();


                if (ProductName.isEmpty()) {
                    showError(name, "Fields Can't be Empty");
                }  else if (BrandName.isEmpty()) {
                    showError(brand, "Fields Can't be Empty");
                } else if (Price.isEmpty()) {
                    showError(price, "Fields Can't be Empty");
                } else if (!image.getDrawable().isFilterBitmap()) {
                    Toast.makeText(getApplicationContext(), "Please Insert a image ", Toast.LENGTH_LONG).show();
                } else if (WarrantyPeriod.isEmpty()) {
                    showError(warrantyperiod, "Fields Can't be Empty");
                } else if (Description.isEmpty()) {
                    showError(description, "Fields Can't be Empty");
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Suppliers_add_data_Activity.this);
                    alertDialog.setTitle("Confirm...");
                    alertDialog.setMessage("Are you sure you want post data");
                    alertDialog.setIcon(R.drawable.ic_right);
                    // Setting Positive "Yes" Btn
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    byte[] img = imageViewToByte(image);
                                    Boolean insert = dbh.insertProduct(img, ProductName, BrandName, Price, Description, Category, WarrantyPeriod, "Pending", account.getId());
                                    if (insert) {
                                        Toast.makeText(Suppliers_add_data_Activity.this, "Post Add Successfully", Toast.LENGTH_SHORT).show();

                                        name.setText("");
                                        brand.setText("");
                                        brand.setText("");
                                        price.setText("");
                                        warrantyperiod.setText("");
                                        description.setText("");
                                        image.setImageResource(R.drawable.ic_image_search);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed to insert product post", Toast.LENGTH_LONG).show();
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
                assert uri != null;
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