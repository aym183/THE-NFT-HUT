package com.example.nftapp3;

import android.util.Log;

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

public class ImageDetails {

    private String WalletAddress;

    public String[] getTitles2() {
        return titles2;
    }

    public void setTitles2(String[] titles2) {
        this.titles2 = titles2;
    }

    String[] titles2 = new String[10];
    String[] copy_titles2 = new String[10];

    public String[] getImageDetails2() {
        return imageDetails2;
    }

    public void setImageDetails2(String[] imageDetails2) {
        this.imageDetails2 = imageDetails2;
    }

    String[] imageDetails2 = new String[10];



    // Might be needed later when integrating wallet address
    public ImageDetails(String WalletAddress){
        this.WalletAddress = WalletAddress;
    }

    public void getImageDetails(){
        OkHttpClient client = new OkHttpClient();

        //String url = "https://opensea13.p.rapidapi.com/assets?collection_slug=cryptopunks&order_direction=desc&limit=20&include_orders=false";
        Request request = new Request.Builder()
                .url("https://opensea13.p.rapidapi.com/assets?collection_slug=cryptopunks&order_direction=desc&limit=11&include_orders=false")
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

//                    String jsonData = myResponse.body().string();
                    try {
                        JSONObject json = new JSONObject(myResponse);

                        JSONArray arr = json.getJSONArray("assets");
                        for (int i = 1; i < arr.length(); i++)
                        {
                            System.out.println("I am here");
                            String post_id = arr.getJSONObject(i).getString("image_preview_url");
                            String post_name = arr.getJSONObject(i).getString("name");
                            copy_titles2[i-1] = post_name;
                            imageDetails2[i-1] = post_id;

//                            Log.d("DATAbyme"+i, post_id);
//                            Log.d("DATAbyme"+i, post_name);
//                            Log.d("DATAbyme"+i, "--------");

                        }
                        setTitles2(copy_titles2);
                        Log.d("titles", Arrays.deepToString(titles2));
                        Log.d("Details", Arrays.deepToString(imageDetails2));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        });



    }



}


