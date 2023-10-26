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

public class Admin_Article_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Article> arrayList;
    private DBHelper dbHelper;
    private Account account; // Add this instance variable
    Customer_Feedback_Adapter adapter;

    public Admin_Article_Adapter(Context context, ArrayList<Article> arrayList, DBHelper dbHelper) {
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
        convertView  = inflater.inflate(R.layout.manage_news_layout, null);

        TextView Title, Description ;

       ImageView Delete = convertView.findViewById(R.id.imageView21);
       Title = convertView.findViewById(R.id.textView27);
       Description=convertView.findViewById(R.id.textView34);

        ImageView NewsImage=convertView.findViewById(R.id.imageView9);
        ImageView Status=convertView.findViewById(R.id.imageView23);

         Article post = arrayList.get(position);
         Title.setText(post.getTitle());
         Description.setText(post.getDescription());

        byte[] img = post.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        NewsImage.setImageBitmap(bitmap);


        if (post.getStatus().equals("Pending")){
            Status.setImageResource(R.drawable.approved);
            String status="approve";
        }else if (post.getStatus().equals("Approved")){
            Status.setImageResource(R.drawable.reject);
            String status="reject";
        }



        Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setTitle("Description");
                alertDialog.setMessage(post.getDescription());
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });

       Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Status = post.getStatus();
                String newStatus = Status.equals("Approved") ? "Pending" : "Approved";

                androidx.appcompat.app.AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);
                alertDialog2.setTitle("Confirm Action...");
                alertDialog2.setMessage("Are you sure you want to keep it as "+newStatus+" ?");

                alertDialog2.setIcon(R.drawable.baseline_warning_24);
                // Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                if (dbHelper.updateNewsStatus(post.getId(), newStatus)) {
                                    Toast.makeText(context.getApplicationContext(), "Status updated", Toast.LENGTH_SHORT).show();
                                    post.setStatus(newStatus); // Update the status in your data object
                                    notifyDataSetChanged(); // Notify the adapter that the data set has changed
                                } else {
                                    Toast.makeText(context.getApplicationContext(), "Failed to update status", Toast.LENGTH_LONG).show();
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
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);
                alertDialog2.setTitle("Confirm Action...");
                alertDialog2.setMessage("Are you sure you want to delete article?");

                alertDialog2.setIcon(R.drawable.baseline_warning_24);
                // Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Boolean checkupdatedata = dbHelper.RemoveNews(post.getId());
                                if (checkupdatedata) {
                                    Toast.makeText(context.getApplicationContext(), "Article deleted", Toast.LENGTH_SHORT).show();

                                    // Assuming 'arrayList' is your data source
                                    int deletedPosition = arrayList.indexOf(post);
                                    if (deletedPosition != -1) {
                                        arrayList.remove(deletedPosition); // Remove the item from the list
                                        notifyDataSetChanged(); // Notify the adapter that the data set has changed
                                    }
                                } else {
                                    Toast.makeText(context.getApplicationContext(), "Failed to delete article", Toast.LENGTH_SHORT).show();
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