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

import java.util.ArrayList;

public class Admin_Notification_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Inquiries> arrayList;
    private DBHelper dbHelper;

    public Admin_Notification_Adapter(Context context, ArrayList<Inquiries> arrayList, DBHelper dbHelper) {
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
        convertView = inflater.inflate(R.layout.admin_notification_layout, null);

        TextView name, email,text;
        ImageView imageremove = convertView.findViewById(R.id.removeButton);

        name = convertView.findViewById(R.id.usernameTextView);
        email = convertView.findViewById(R.id.emailTextView);
        text=convertView.findViewById(R.id.inquiryTextView);
        Inquiries post = arrayList.get(position);
        name.setText(post.getUsername());
        email.setText("Email: "+post.getEmail());
        text.setText(post.getInquiry());


            imageremove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean checkupdatedata = dbHelper.RemoveInqury(post.getId());
                    if (checkupdatedata) {
                        Toast.makeText(context.getApplicationContext(), "Inquiry deleted", Toast.LENGTH_SHORT).show();

                        // Assuming 'arrayList' is your data source
                        int deletedPosition = arrayList.indexOf(post);
                        if (deletedPosition != -1) {
                            arrayList.remove(deletedPosition); // Remove the item from the list
                            notifyDataSetChanged(); // Notify the adapter that the data set has changed
                        }
                    } else {
                        Toast.makeText(context.getApplicationContext(), "Failed to delete Inquiry", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        return convertView;

    }

}