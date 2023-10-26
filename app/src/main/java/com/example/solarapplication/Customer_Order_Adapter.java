package com.example.solarapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Date;

public class Customer_Order_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Orders> arrayList;
    private DBHelper dbHelper;
    private Account account; // Add this instance variable

    public Customer_Order_Adapter(Context context, ArrayList<Orders> arrayList, DBHelper dbHelper, Account account) {
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
        convertView = inflater.inflate(R.layout.customer_order_layout, null);

        TextView productName, qTy,Status,Date;
        ImageView imageView = convertView.findViewById(R.id.productImage);
        ImageView imageremove = convertView.findViewById(R.id.removeButton);


        productName = convertView.findViewById(R.id.productName);
        qTy = convertView.findViewById(R.id.quantity);
        Status=convertView.findViewById(R.id.status);
        Date=convertView.findViewById(R.id.date);


        Orders post = arrayList.get(position);

        byte[] img = post.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        imageView.setImageBitmap(bitmap);

        Date.setText(" Date: " +post.getDate());
        productName.setText(post.getProductName());
        qTy.setText(" Quantity: " + post.Quantity);

        if (post.getStatus().equals("Pending")) {
            // Set the background color to red
            Status.setBackgroundColor(ContextCompat.getColor(context, R.color.myColor11)); // Replace R.color.red_background with your red background color resource
            Status.setTextColor(ContextCompat.getColor(context, R.color.black)); // Set text color to white
            Status.setText(" "+ post.getStatus());
        } else if (post.getStatus().equals("Rejected")) {
            Status.setBackgroundColor(ContextCompat.getColor(context, R.color.red_background)); // Replace R.color.green_background with your green background color resource
            Status.setTextColor(ContextCompat.getColor(context, R.color.black_text)); // Set text color to black
            Status.setText(" "+ post.getStatus());
        }else if (post.getStatus().equals("Shipping")) {
            // Set the background color to green
            Status.setBackgroundColor(ContextCompat.getColor(context, R.color.green_background)); // Replace R.color.green_background with your green background color resource
            Status.setTextColor(ContextCompat.getColor(context, R.color.black_text)); // Set text color to black
            Status.setText(" "+ post.getStatus());
        }

        if (post.getStatus().equals("Pending")||post.getStatus().equals("Rejected")){
            imageremove.setVisibility(View.VISIBLE);
            imageremove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean checkupdatedata = dbHelper.RemoveOder(post.getId());
                    if (checkupdatedata) {
                        Toast.makeText(context.getApplicationContext(), "Order Canceled", Toast.LENGTH_SHORT).show();

                        // Assuming 'arrayList' is your data source
                        int deletedPosition = arrayList.indexOf(post);
                        if (deletedPosition != -1) {
                            arrayList.remove(deletedPosition); // Remove the item from the list
                            notifyDataSetChanged(); // Notify the adapter that the data set has changed
                        }
                    } else {
                        Toast.makeText(context.getApplicationContext(), "Failed to Cancel the Order", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            imageremove.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
