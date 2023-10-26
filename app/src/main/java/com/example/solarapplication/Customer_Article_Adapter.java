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

public class Customer_Article_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Article> arrayList;
    private DBHelper dbHelper;
    private Account account; // Add this instance variable
    Customer_Feedback_Adapter adapter;

    public Customer_Article_Adapter(Context context, ArrayList<Article> arrayList, DBHelper dbHelper) {
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
        convertView  = inflater.inflate(R.layout.customer_view_articles_layout, null);

        TextView Title, Description ;


        Title = convertView.findViewById(R.id.textView27);
        Description=convertView.findViewById(R.id.textView34);

        ImageView NewsImage=convertView.findViewById(R.id.imageView9);


        Article post = arrayList.get(position);
        Title.setText(post.getTitle());
        Description.setText(post.getDescription());

        byte[] img = post.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        NewsImage.setImageBitmap(bitmap);



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

        return convertView;
    }
}
