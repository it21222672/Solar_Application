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

public class Journalist_Manage_Articles_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Article> arrayList;
    private DBHelper dbHelper;
    private Journalist account; // Add this instance variable

    public Journalist_Manage_Articles_Adapter(Context context, ArrayList<Article> arrayList, DBHelper dbHelper, Journalist account) {
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
        convertView  = inflater.inflate(R.layout.manage_news_layout, null);

        TextView Title, Description;

        ImageView imageView = convertView.findViewById(R.id.imageView9);

        Description= convertView.findViewById(R.id.textView34);
        Title=convertView.findViewById(R.id.textView27);
        ImageView  EDIT=convertView.findViewById(R.id.imageView23);
        ImageView Delete=convertView.findViewById(R.id.imageView21);

        Article post = arrayList.get(position);

        byte[] img = post.getImage();

        String  title= post.getTitle();
        String description=post.getDescription();


        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        Title.setText(title);
        Description.setText(description);
        imageView.setImageBitmap(bitmap);

        Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setTitle(title);
                alertDialog.setMessage(description);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });


        EDIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), Journalist_Update_Articles_Activity.class);
                intent.putExtra("PostData", post);
                intent.putExtra("UserInfo", account);
                view.getContext().startActivity(intent);
                ((Activity) view.getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);
                alertDialog2.setTitle("Confirm Action...");
                alertDialog2.setMessage("Are you sure you want to Remove Article?");

                alertDialog2.setIcon(R.drawable.baseline_warning_24);
                // Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                               Boolean checkupdatedata=dbHelper.RemoveNews(post.getId());
                                if (checkupdatedata==true){
                                    Toast.makeText(context.getApplicationContext(), "Article Removed ", Toast.LENGTH_SHORT).show();
                                    // Assuming 'arrayList' is your data source
                                    int deletedPosition = arrayList.indexOf(post);
                                    if (deletedPosition != -1) {
                                        arrayList.remove(deletedPosition); // Remove the item from the list
                                        notifyDataSetChanged(); // Notify the adapter that the data set has changed
                                    }
                                }else{
                                    Toast.makeText(context.getApplicationContext(), "Failed to Remove Article ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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