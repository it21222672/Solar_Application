package com.example.solarapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class Admin_Product_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Products> arrayList;
    private DBHelper dbHelper;

    public Admin_Product_Adapter(Context context, ArrayList<Products> arrayList, DBHelper dbHelper) {
        this.context = context;
        this.arrayList = arrayList;
        this.dbHelper = dbHelper;
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
        convertView = inflater.inflate(R.layout.admin_product_layout, null);
        TextView name, price, BrandName, Quantity,Status;
        //   ImageView plus,mines;
        ImageView imageView = convertView.findViewById(R.id.imageView9);
        ImageView actionButton = convertView.findViewById(R.id.img99);
        Button StatusButton=convertView.findViewById(R.id.button);


        Status=convertView.findViewById(R.id.textView12);
        name = convertView.findViewById(R.id.textView27);
        BrandName = convertView.findViewById(R.id.textView32);
        price = convertView.findViewById(R.id.textView33);


        Products post = arrayList.get(position);
        int id=post.getId();
        String status=post.getRequest();


        String ProductName = post.getProductName();
        byte[] img = post.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        imageView.setImageBitmap(bitmap);

        String ProductPrice = post.getPrice();
        String ProductBrand = post.getBrandName();

        if (post.getRequest().equals("Pending")){
            StatusButton.setBackgroundColor(ContextCompat.getColor(context, R.color.myColor3)); // Replace R.color.red_background with your red background color resource
            StatusButton.setTextColor(ContextCompat.getColor(context, R.color.black)); // Set text color to white
            StatusButton.setText("Approve");
        }else if(post.getRequest().equals("Approved")){
            StatusButton.setBackgroundColor(ContextCompat.getColor(context, R.color.red_background)); // Replace R.color.green_background with your green background color resource
            StatusButton.setTextColor(ContextCompat.getColor(context, R.color.black_text)); // Set text color to black
            StatusButton.setText("Reject");
        }


        Status.setText("Status : "+post.getRequest());
        name.setText(ProductName);
        BrandName.setText(ProductBrand);
        price.setText("Rs. " + ProductPrice + ".00/=");


        StatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newStatus;
                if (status.equals("Pending")) {
                    dbHelper.updatePostStatus(id, "Approved");
                    newStatus = "Approved";

                } else {
                    dbHelper.updatePostStatus(id, "Pending");
                    newStatus = "Pending";

                }

                // Display a Toast message indicating the updated status
                String toastMessage = "Status Updated to " + newStatus;
                Toast.makeText(context.getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();

                // Notify the adapter that the data has changed, causing the UI to refresh
                post.setRequest(newStatus);
                notifyDataSetChanged();
              //  Intent intent = new Intent(context, Admin_View_Products_Activity.class);
             //   context.startActivity(intent);
            }
        });




        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // Set the title and message for the dialog
                builder.setTitle("Select an option");
                builder.setMessage("Choose what you want to do:");

                // Set up the buttons for the dialog
                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Boolean checkupdatedata=dbHelper.RemoveItem(id);
                        if (checkupdatedata==true){
                            Toast.makeText(context.getApplicationContext(), "Post Removed ", Toast.LENGTH_SHORT).show();
                            // Assuming 'arrayList' is your data source
                            int deletedPosition = arrayList.indexOf(post);
                            if (deletedPosition != -1) {
                                arrayList.remove(deletedPosition); // Remove the item from the list
                                notifyDataSetChanged(); // Notify the adapter that the data set has changed
                            }
                        }else{
                            Toast.makeText(context.getApplicationContext(), "Failed to Remove Post ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, View_Post_Activity.class);
                        intent.putExtra("PostInfo", post);
                        context.startActivity(intent);


                    }
                });



                // Show the dialog
                builder.show();
            }
        });



        return convertView;
    }
}