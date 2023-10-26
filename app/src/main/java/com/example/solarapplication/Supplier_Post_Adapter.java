package com.example.solarapplication;

import android.app.Activity;
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

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class Supplier_Post_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Products> arrayList;
     DBHelper dbHelper;
     Suppliers account; // Add this instance variable

    public Supplier_Post_Adapter(Context context, ArrayList<Products> arrayList, DBHelper dbHelper, Suppliers account) {
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
        convertView  = inflater.inflate(R.layout.suplier_post_layout, null);

        TextView name, price, BrandName, Description,Category,warranty;
        Button EDIT, Delete;
        ImageView imageView = convertView.findViewById(R.id.imageView9);



        name = convertView.findViewById(R.id.textView27);
        BrandName = convertView.findViewById(R.id.textView32);
        price = convertView.findViewById(R.id.textView33);
        Description= convertView.findViewById(R.id.textView29);
        Category = convertView.findViewById(R.id.category);
       warranty = convertView.findViewById(R.id.WarrantyPeriod);
         EDIT=convertView.findViewById(R.id.edit);
         Delete=convertView.findViewById(R.id.delete);

        Products post = arrayList.get(position);
        String ProductName = post.getProductName();
        byte[] img = post.getImage();
        String  ProductPrice= post.getPrice();
        String  ProductBrand= post.getBrandName();
        String description=post.getDescription();
        String category=post.getCategory();
        String Warranty=post.getWarrantyPeriod();


        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        name.setText(ProductName);
        BrandName.setText(ProductBrand);
        warranty.setText(Warranty+" Years Warranty");
        Category.setText(category);
        Description.setText(description);
        price.setText("Rs. "+ProductPrice+".00/=");

        imageView.setImageBitmap(bitmap);


        EDIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(view.getContext(), Update_Product_Activity.class);
                    intent.putExtra("PostData", post);
                   // intent.putExtra("UserInfo", account);
                    view.getContext().startActivity(intent);
                    ((Activity) view.getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(view.getContext(), "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);
                alertDialog2.setTitle("Confirm Action...");
                alertDialog2.setMessage("Are you sure you want to Remove Supplier?");

                alertDialog2.setIcon(R.drawable.baseline_warning_24);
                // Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Boolean checkupdatedata=dbHelper.RemoveItem(post.getId());
                                if (checkupdatedata==true){
                                    Toast.makeText(context.getApplicationContext(), "Product Removed ", Toast.LENGTH_SHORT).show();
                                    // Assuming 'arrayList' is your data source
                                    int deletedPosition = arrayList.indexOf(post);
                                    if (deletedPosition != -1) {
                                        arrayList.remove(deletedPosition); // Remove the item from the list
                                        notifyDataSetChanged(); // Notify the adapter that the data set has changed
                                    }
                                }else{
                                    Toast.makeText(context.getApplicationContext(), "Failed to Remove Product ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                // Setting Negative "NO" Btn
                alertDialog2.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });
                // Showing Alert Dialog
                alertDialog2.show();


            }
        });

        return convertView;
    }




}
