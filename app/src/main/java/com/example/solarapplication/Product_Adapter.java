package com.example.solarapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Product_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Products> arrayList;
    private DBHelper dbHelper;
    private Account account;
    public Product_Adapter(Context context, ArrayList<Products> arrayList, DBHelper dbHelper,Account account) {
        this.context = context;
        this.arrayList = arrayList;
        this.dbHelper = dbHelper;
        this.account = account;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.supplier_product_layout, null);
        TextView name, price, BrandName, Quantity;
        Button Buy,customize;

        ImageView imageView = convertView.findViewById(R.id.imageView9);
        ImageView Description=convertView.findViewById(R.id.img99);
        ImageView plus = convertView.findViewById(R.id.imageView22);
        ImageView minus = convertView.findViewById(R.id.imageView23);
        Quantity = convertView.findViewById(R.id.textView35);
        Buy=convertView.findViewById(R.id.buy);
        customize= convertView.findViewById(R.id.btncustomize);
        name = convertView.findViewById(R.id.textView27);
        BrandName = convertView.findViewById(R.id.textView32);
        price = convertView.findViewById(R.id.textView33);


        Products post = arrayList.get(position);
        String ProductName = post.getProductName();
        byte[] img = post.getImage();
        String  ProductPrice= post.getPrice();
        String  ProductBrand= post.getBrandName();
        String qty = Quantity.getText().toString().trim();


        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        name.setText(ProductName);
        BrandName.setText(ProductBrand);
        price.setText("Rs. "+ProductPrice+".00/=");
        imageView.setImageBitmap(bitmap);

        // Set the initial quantity value
        int initialQuantity = 1;
        Quantity.setText(String.valueOf(initialQuantity));


            int quantity = Integer.parseInt(qty);
            // Parse the product price (assuming it's a numeric value)
            double price1 = Double.parseDouble(ProductPrice);

            // Calculate the subtotal
            double subtotal = quantity * price1;


        //    TextView subtotalTextView = findViewById(R.id.subtotalTextView);
          //  subtotalTextView.setText("Subtotal: Rs. " + subtotal);



        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment the quantity when the plus button is clicked
                int currentQuantity = Integer.parseInt(Quantity.getText().toString());
                Quantity.setText(String.valueOf(currentQuantity + 1));
              //  Toast.makeText(context.getApplicationContext(),"Subtotal: Rs. " + subtotal, Toast.LENGTH_SHORT).show();
            }
        });
        // Add a click listener to the minus button
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Decrement the quantity when the minus button is clicked
                int currentQuantity = Integer.parseInt(Quantity.getText().toString());
                if (currentQuantity > 1) {
                    Quantity.setText(String.valueOf(currentQuantity - 1));
                }
            }
        });




        Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setTitle("Product Details");
                alertDialog.setMessage("Product Name: "+post.getProductName()+
                                "\nBrand Name: "+ post.getBrandName() +
                                "\nCategory: "+post.getCategory()+
                                "\nWarranty Period: "+post.getWarrantyPeriod()+" Years"+
                                "\nPrice: "+post.getPrice()+".00/="+
                        "\nDescription: "+post.getDescription());
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });

        customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Inflate the custom layout (confirm_order_layout)
                View customView = LayoutInflater.from(context).inflate(R.layout.customize_product, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Please confirm...");
                alertDialog.setIcon(R.drawable.baseline_add_task_24);
                // Set the custom view for the dialog
                alertDialog.setView(customView);
                // Create the dialog
                AlertDialog dialog = alertDialog.create();
                // Setting Positive "Buy" Btn
                alertDialog.setPositiveButton("Request", null);
                // Setting Negative "Cancel" Btn
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                // Create and show the dialog
                dialog = alertDialog.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button buyButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        buyButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditText customerNameEditText = customView.findViewById(R.id.nameEditText);
                                EditText emailEditText = customView.findViewById(R.id.emailEditText);
                                EditText contactNumberEditText = customView.findViewById(R.id.contactNumberEditText);
                                EditText addressEditText = customView.findViewById(R.id.addressEditText);
                                EditText postalCodeEditText = customView.findViewById(R.id.postalCodeEditText);

                                EditText LengthEditText = customView.findViewById(R.id.LengthEditText);
                                EditText WidthEditText = customView.findViewById(R.id.widthEditText);
                                EditText InputEditText = customView.findViewById(R.id.powerInputEditText);
                                EditText OutPutEditText = customView.findViewById(R.id.outEditText);
                                EditText SpecialReqEditText = customView.findViewById(R.id.DescriptionEditText);

                                String length = LengthEditText.getText().toString().trim();
                                String width = WidthEditText.getText().toString().trim();
                                String input = InputEditText.getText().toString().trim();
                                String output = OutPutEditText.getText().toString().trim();
                                String specealreq = SpecialReqEditText.getText().toString().trim();

                                String OtherSpecialRequest="";

                                if (!specealreq.isEmpty()) {
                                    OtherSpecialRequest = specealreq; // Assign a different value if the condition is met
                                }else if (specealreq.isEmpty()) {
                                    OtherSpecialRequest = "No";
                                }

                              //  Toast.makeText(context.getApplicationContext(), (CharSequence) OtherSpecialRequest, Toast.LENGTH_SHORT).show();

                                String name = customerNameEditText.getText().toString().trim();
                                String email = emailEditText.getText().toString().trim();
                                String contact = contactNumberEditText.getText().toString().trim();
                                String address = addressEditText.getText().toString().trim();
                                String postalCode = postalCodeEditText.getText().toString().trim();

                                // Validate user input
                                if (length.isEmpty()) {
                                    showError(LengthEditText, "Field is empty");
                                } else if (width.isEmpty()) {
                                    showError(WidthEditText, "Field is empty");
                                } else if (input.isEmpty()) {
                                    showError(InputEditText, "Field is empty");
                                }else if (output.isEmpty()) {
                                    showError(OutPutEditText, "Field is empty");
                                }else if (name.isEmpty() || !name.matches("^[A-Za-z]+$")) {
                                    showError(customerNameEditText, "Name is invalid");
                                } else if (email.isEmpty() || !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                                    showError(emailEditText, "Email is invalid");
                                } else if (contact.isEmpty() || !contact.matches("^[0-9]*$")) {
                                    showError(contactNumberEditText, "Phone Number is invalid");
                                } else if (address.isEmpty()) {
                                    showError(addressEditText, "Address can't be empty");
                                } else if (postalCode.isEmpty() || !postalCode.matches("^[0-9]*$")) {
                                    showError(postalCodeEditText, "Postal Code is invalid");
                                } else {
                                    String Description = "Length : "+length +"cm,"+
                                                         "Width : " +width +"cm,"+
                                                         "Power Input : " +input +"Watts,"+
                                                         "Power output : " +output +"Watts,"+
                                                         "Other Requirement : " +OtherSpecialRequest

                                            ;

                                    // Data is valid, insert the order
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                                   String todayDate = dateFormat.format(calendar.getTime());
                                    Boolean insert = dbHelper.insertOrder(img, ProductName, Quantity.getText().toString(), account.getId(), name, email, contact, address, postalCode, todayDate, Description,"Pending",post.getSupplierId());
                                    if (insert) {
                                        Toast.makeText(context.getApplicationContext(), "Order successful", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(context.getApplicationContext(), "Order failed", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }

                                }


                            }
                        });
                    }
                });
                // Show the dialog
                dialog.show();
            }
        });

        Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inflate the custom layout (confirm_order_layout)
                View customView = LayoutInflater.from(context).inflate(R.layout.confirm_order_layout, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Please confirm...");
                alertDialog.setIcon(R.drawable.baseline_add_task_24);
                // Set the custom view for the dialog
                alertDialog.setView(customView);
                // Create the dialog
                AlertDialog dialog = alertDialog.create();
                // Setting Positive "Buy" Btn
                alertDialog.setPositiveButton("Buy", null);
                // Setting Negative "Cancel" Btn
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                // Create and show the dialog
                dialog = alertDialog.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button buyButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        buyButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditText customerNameEditText = customView.findViewById(R.id.nameEditText);
                                EditText emailEditText = customView.findViewById(R.id.emailEditText);
                                EditText contactNumberEditText = customView.findViewById(R.id.contactNumberEditText);
                                EditText addressEditText = customView.findViewById(R.id.addressEditText);
                                EditText postalCodeEditText = customView.findViewById(R.id.postalCodeEditText);
                                TextView totalPrice=customView.findViewById(R.id.priceText);
                              //  totalPrice.setText("Rs.00.00");

                                String name = customerNameEditText.getText().toString().trim();
                                String email = emailEditText.getText().toString().trim();
                                String contact = contactNumberEditText.getText().toString().trim();
                                String address = addressEditText.getText().toString().trim();
                                String postalCode = postalCodeEditText.getText().toString().trim();


                                // Validate user input

                                if (name.isEmpty() || !name.matches("^[A-Za-z]+$")) {
                                    showError(customerNameEditText, "Name is invalid");
                                } else if (email.isEmpty() || !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                                    showError(emailEditText, "Email is invalid");
                                } else if (contact.isEmpty() || !contact.matches("^[0-9]*$")) {
                                    showError(contactNumberEditText, "Phone Number is invalid");
                                } else if (address.isEmpty()) {
                                    showError(addressEditText, "Address can't be empty");
                                } else if (postalCode.isEmpty() || !postalCode.matches("^[0-9]*$")) {
                                    showError(postalCodeEditText, "Postal Code is invalid");
                                } else {
                                    // Data is valid, insert the order
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    String todayDate = dateFormat.format(calendar.getTime());
                                    Boolean insert = dbHelper.insertOrder(img, ProductName, Quantity.getText().toString(), account.getId(), name, email, contact, address, postalCode, todayDate, "No","Pending",post.getSupplierId());
                                    if (insert) {
                                        Toast.makeText(context.getApplicationContext(), "Order successful", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(context.getApplicationContext(), "Order failed", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            }
                        });
                    }
                });
                // Show the dialog
                dialog.show();
            }
        });
        return convertView;
    }
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}
