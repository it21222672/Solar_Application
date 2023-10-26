package com.example.solarapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class admin_View_Supplier_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Suppliers> arrayList;
    private DBHelper dbHelper;

    public admin_View_Supplier_Adapter(Context context, ArrayList<Suppliers> arrayList, DBHelper dbHelper) {
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
        convertView = inflater.inflate(R.layout.view_supplier_layout, null);

        TextView name, address, contact,Status;

        ImageView imageView = convertView.findViewById(R.id.imageView5);
        ImageView actionremove = convertView.findViewById(R.id.imageView21);
        ImageView actionview = convertView.findViewById(R.id.imageView22);
        ImageView actionupdate = convertView.findViewById(R.id.imageView23);


        name = convertView.findViewById(R.id.text1);
        address = convertView.findViewById(R.id.text2);
        contact = convertView.findViewById(R.id.text3);
        Status=convertView.findViewById(R.id.text4);


        Suppliers post = arrayList.get(position);
        int id=post.getId();
        String status=post.getRequest();


        byte[] img = post.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        imageView.setImageBitmap(bitmap);

         String Name = post.getName();
        String Address = post.getAddress();
        String ContactNo = post.getContact_no();

        Status.setText("Status: "+status);
        name.setText("Name: "+Name);
        address.setText("Address: "+Address);
        contact.setText("Contact Number: "+ContactNo);


        actionremove.setOnClickListener(new View.OnClickListener() {
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
                                Boolean checkupdatedata=dbHelper.RemoveSuppliers(id);
                                if (checkupdatedata==true){
                                    Toast.makeText(context.getApplicationContext(), "Supplier Removed ", Toast.LENGTH_SHORT).show();
                                    int deletedPosition = arrayList.indexOf(post);
                                    if (deletedPosition != -1) {
                                        arrayList.remove(deletedPosition); // Remove the item from the list
                                        notifyDataSetChanged(); // Notify the adapter that the data set has changed
                                    }
                                }else{
                                    Toast.makeText(context.getApplicationContext(), "Failed to Remove Supplier ", Toast.LENGTH_SHORT).show();
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

        actionview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setTitle("Supplier Details");
                alertDialog.setMessage(" Name: "+post.getName()+
                        "\n Position : "+post.getPosition()+
                        "\n Company Name: "+post.getCompany_Name()+
                        "\n Registration No: "+post.getRegistration_No()+
                        "\n Email : "+post.getEmail()+
                        "\n Web Address : "+post.getWeb()+
                        "\n Contact Number : "+post.getContact_no()+
                        "\n Description : "+post.getDescription()

                );
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });

        actionupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);
                alertDialog2.setTitle("Confirm Action...");
                alertDialog2.setMessage("Are you sure you want to Chang Status?");

                alertDialog2.setIcon(R.drawable.baseline_warning_24);
                // Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                String Status = post.getRequest();
                                String newStatus = Status.equals("Approved") ? "Pending" : "Approved";
                                if (dbHelper.updateSuppliersStatus(post.getId(), newStatus)) {
                                    Toast.makeText(context.getApplicationContext(), "Feedback status updated", Toast.LENGTH_SHORT).show();
                                    post.setRequest(newStatus); // Update the status in your data object
                                    notifyDataSetChanged(); // Notify the adapter that the data set has changed
                                } else {
                                    Toast.makeText(context.getApplicationContext(), "Failed to update feedback status", Toast.LENGTH_LONG).show();
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
