package com.example.solarapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ChatBot_Activity extends AppCompatActivity {
     TextView WelcomeText;
     EditText messageEditText;
     ImageView sendButton;
    RecyclerView recyclerView;
    List<Message>messagesList;
    MessageAdapter messageAdapter;

    public static  final MediaType JSON
            =MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        messagesList=new ArrayList<>();

        recyclerView=findViewById(R.id.recycle_view);
        messageEditText=findViewById(R.id.message_edit_text);
        WelcomeText=findViewById(R.id.welcome_text);
        sendButton=findViewById(R.id.send_btn);

        //setup recycler View
        messageAdapter=new MessageAdapter(messagesList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setStackFromEnd(true);//to scroll
        recyclerView.setLayoutManager(llm);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question= messageEditText.getText().toString().trim();

                addToChat(question,Message.SENT_BY_ME);
                messageEditText.setText("");
                callAPI(question);
                WelcomeText.setVisibility(View.GONE);
            }
        });

    }

    void addToChat(String message,String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messagesList.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });

    }
    void addResponse(String response){
        addToChat(response,Message.SENT_BY_BOT);
    }

    void callAPI(String question){
    //okhtp
        JSONObject jsonBody=new JSONObject();
        try {

            jsonBody.put("model","gpt-3.5-turbo");
            JSONArray messageArr= new JSONArray();
            JSONObject obj=new JSONObject();
            obj.put("role","user");
            obj.put("content",question);
            messageArr.put(obj);
            jsonBody.put("messages",messageArr);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body=RequestBody.create(jsonBody.toString(),JSON);
        Request request=new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load Response due to"+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject jsonObject= null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray=jsonObject.getJSONArray("choices");
                        String result= jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }else {
                    addResponse("Failed to load Response due to"+response.body().toString());
                }
            }






        });

    }

}