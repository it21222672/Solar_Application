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

import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;

public class View_All_FeedBack_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Feedback> arrayList;
    private DBHelper dbHelper;
    private Account account; // Add this instance variable
    Customer_Feedback_Adapter adapter;

    public View_All_FeedBack_Adapter(Context context, ArrayList<Feedback> arrayList, DBHelper dbHelper, Account account) {
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
        convertView  = inflater.inflate(R.layout.customer_all_feedback_layout, null);

        TextView feedback;


        feedback = convertView.findViewById(R.id.text1);

        Feedback post = arrayList.get(position);
        String Text = post.getFeedback();
        feedback.setText(Text);

        RatingBar rbStars =convertView. findViewById(R.id.rbStars);
        rbStars.setIsIndicator(true);
        float likes = post.getLikes(); // Get the integer value
        // Set the rating on the RatingBar by converting the integer to a float
        rbStars.setRating((float) likes);

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





        return convertView;
    }
}