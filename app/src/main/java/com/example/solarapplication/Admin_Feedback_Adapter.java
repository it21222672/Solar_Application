package com.example.solarapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class Admin_Feedback_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Feedback> arrayList;
    private DBHelper dbHelper;
    private Account account; // Add this instance variable
    Customer_Feedback_Adapter adapter;

    public Admin_Feedback_Adapter(Context context, ArrayList<Feedback> arrayList, DBHelper dbHelper) {
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
        convertView  = inflater.inflate(R.layout.customer_feedback_layout, null);

        TextView feedback, Likes, Status ;

        ImageView Delete = convertView.findViewById(R.id.remove);
        feedback = convertView.findViewById(R.id.text1);
        //  Likes=convertView.findViewById(R.id.likes);
        Status=convertView.findViewById(R.id.status);

        Feedback post = arrayList.get(position);
        String Text = post.getFeedback();

        RatingBar rbStars =convertView. findViewById(R.id.rbStars);
        rbStars.setIsIndicator(true);
        float likes = post.getLikes(); // Get the integer value

        // Set the rating on the RatingBar by converting the integer to a float
        rbStars.setRating((float) likes);
        //  String likesText = String.valueOf(number);
        // Likes.setText(String.valueOf(number));

        feedback.setText(Text);
        Status.setText("Feedback Request "+ post.getStatus());

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setTitle("Feedback");
                alertDialog.setMessage(Text);
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

                if (dbHelper.updateFeedbackStatus(post.getId(), newStatus)) {
                    Toast.makeText(context.getApplicationContext(), "Feedback status updated", Toast.LENGTH_SHORT).show();
                    post.setStatus(newStatus); // Update the status in your data object
                    notifyDataSetChanged(); // Notify the adapter that the data set has changed
                } else {
                    Toast.makeText(context.getApplicationContext(), "Failed to update feedback status", Toast.LENGTH_LONG).show();
                }
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);
                alertDialog2.setTitle("Confirm Action...");
                alertDialog2.setMessage("Are you sure you want to delete feedback?");

                alertDialog2.setIcon(R.drawable.baseline_warning_24);
                // Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Boolean checkupdatedata = dbHelper.RemoveFeedback(post.getId());
                                if (checkupdatedata) {
                                    Toast.makeText(context.getApplicationContext(), "Feedback deleted", Toast.LENGTH_SHORT).show();

                                    // Assuming 'arrayList' is your data source
                                    int deletedPosition = arrayList.indexOf(post);
                                    if (deletedPosition != -1) {
                                        arrayList.remove(deletedPosition); // Remove the item from the list
                                        notifyDataSetChanged(); // Notify the adapter that the data set has changed
                                    }
                                } else {
                                    Toast.makeText(context.getApplicationContext(), "Failed to delete feedback", Toast.LENGTH_SHORT).show();
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
