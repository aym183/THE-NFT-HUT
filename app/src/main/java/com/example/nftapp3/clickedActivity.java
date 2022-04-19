package com.example.nftapp3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class clickedActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("ClickedDetails", Context.MODE_PRIVATE);
        String titleValue = sp.getString("title", "");
        String imageValue = sp.getString("image", "");
        String token = sp.getString("token_id", "");
        String address = sp.getString("address", "");
        Log.d("token_id", token);
        Log.d("address", address);

        TextView TextName = findViewById(R.id.textName);
        TextName.setText(titleValue);
        ImageView ivResult = findViewById(R.id.clickedImageView);
        LoadImage setImage = new LoadImage(ivResult);
        setImage.execute(imageValue);

        OkHttpClient client = new OkHttpClient();

        //String url = "https://opensea13.p.rapidapi.com/assets?collection_slug=cryptopunks&order_direction=desc&limit=20&include_orders=false";
        Request request = new Request.Builder()
                .url("https://opensea13.p.rapidapi.com/asset/" + address + "/" + token + "?include_orders=false")
                .get()
                .addHeader("X-RapidAPI-Host", "opensea13.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "3b11ee2336msh5791c275fc5dbc6p11fa37jsn3cafb1ed4e82")
                .build();

        // TextView imageView = findViewById(R.id.textView5);
//        String myResponse = "API";
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String myResponse = response.body().string();
                    Log.d("Response", myResponse);
//                    String jsonData = myResponse.body().string();
                    try {
                        JSONObject json = new JSONObject(myResponse);

//                        JSONArray arr = json.getJSONArray("assets");
//                        int sale_details = 0;


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {


                        }
                    });



                }

            }
        });


    }

    public void backArrowEvent(View v){
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        overridePendingTransition(0, 0);
    }
}
