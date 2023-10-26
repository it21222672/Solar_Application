package com.example.solarapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class Supplier_Order_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Orders> arrayList;
    private DBHelper dbHelper;
    private Suppliers account; // Add this instance variable

    public Supplier_Order_Adapter(Context context, ArrayList<Orders> arrayList, DBHelper dbHelper, Suppliers account) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.supplier_view_oder_layout, null);

        TextView productName, qTy, customerName, email, contactNumber, address, postalCode,Status,Description;
        ImageView imageView = convertView.findViewById(R.id.productImage);

        Description=convertView.findViewById(R.id.Descriptiontxt);
        productName = convertView.findViewById(R.id.productName);
        qTy = convertView.findViewById(R.id.quantity);
        customerName = convertView.findViewById(R.id.customerName);
        email = convertView.findViewById(R.id.email);
        contactNumber = convertView.findViewById(R.id.contactNumber);
        address = convertView.findViewById(R.id.address);
        postalCode = convertView.findViewById(R.id.postalCode);
        Status=convertView.findViewById(R.id.status);

        Orders post = arrayList.get(position);

        byte[] img = post.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        imageView.setImageBitmap(bitmap);

      //  Description.setText(post.Description);

        String description = post.getDescription();


        if (description.contains(",")) {
            // Split the brandName by the comma and join the parts with a line break
            String[] parts = description.split(",");
            description = TextUtils.join("\n", parts);
        }Description.setText("Special Requirements: \n"+description);


        productName.setText("Product Name"+post.getProductName());
        qTy.setText(" Quantity: " + post.Quantity);
        customerName.setText("  Customer Name: " + post.UserName);
        email.setText("  Email: " + post.Email);
        contactNumber.setText("  Contact Number: " + post.ContactNumber);
        address.setText("Address: " + post.Address);
        postalCode.setText("  Postal Code: " + post.PostalCode);

        if (post.getStatus().equals("Pending")) {
            // Set the background color to red
            Status.setBackgroundColor(ContextCompat.getColor(context, R.color.myColor11)); // Replace R.color.red_background with your red background color resource
            Status.setTextColor(ContextCompat.getColor(context, R.color.black)); // Set text color to white
            Status.setText("Status: " + post.getStatus());
        }else if (post.getStatus().equals("Rejected")) {
            // Set the background color to red
            Status.setBackgroundColor(ContextCompat.getColor(context, R.color.red_background)); // Replace R.color.red_background with your red background color resource
            Status.setTextColor(ContextCompat.getColor(context, R.color.black)); // Set text color to white
            Status.setText("Status: " + post.getStatus());
        } else if (post.getStatus().equals("Shipping")){
            // Set the background color to green
            Status.setBackgroundColor(ContextCompat.getColor(context, R.color.green_background)); // Replace R.color.green_background with your green background color resource
            Status.setTextColor(ContextCompat.getColor(context, R.color.black_text)); // Set text color to black
            Status.setText("Status: " + post.getStatus());
        }


            convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (post.getStatus().equals("Pending")) {
                    showConfirmationDialog(arrayList.get(position));
                }


            }
        });

        return convertView;

    }


    private void showConfirmationDialog(Orders order) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Confirm Status");
        alertDialog.setMessage("Do you want to confirm, reject, or cancel the status for this order?");

        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                String Status = order.getStatus();
                if ("Shipping".equals(Status)) {
                    Toast.makeText(context.getApplicationContext(), "Orders is already Shipped", Toast.LENGTH_SHORT).show();
                } else {
                    String newStatus = "Shipping";

                    if (dbHelper.updateOrderStatus(order.getId(), newStatus)) {
                        Toast.makeText(context.getApplicationContext(), "Orders status Shipped ", Toast.LENGTH_SHORT).show();
                        order.setStatus(newStatus); // Update the status in your data object
                        notifyDataSetChanged(); // Notify the adapter that the data set has changed
                    } else {
                        Toast.makeText(context.getApplicationContext(), "Failed to update orders status", Toast.LENGTH_LONG).show();
                    }
                }

                dialog.dismiss();

            }
        });
        alertDialog.setNeutralButton("Reject", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Handle the reject action
                String newStatus = "Rejected";

                if (dbHelper.updateOrderStatus(order.getId(), newStatus)) {
                    Toast.makeText(context.getApplicationContext(), "Orders Rejected ", Toast.LENGTH_SHORT).show();
                    order.setStatus(newStatus); // Update the status in your data object
                    notifyDataSetChanged(); // Notify the adapter that the data set has changed
                } else {
                    Toast.makeText(context.getApplicationContext(), "Failed to update orders status", Toast.LENGTH_LONG).show();
                }

            }
        });


        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
