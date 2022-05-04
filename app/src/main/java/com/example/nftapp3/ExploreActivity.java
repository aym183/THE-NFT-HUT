package com.example.nftapp3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

/**
 * The ExploreActivity class is used to handle operations
 */
public class ExploreActivity extends AppCompatActivity {

    /* Arrays containing all of the text and image views to display data */
    ImageView ivResult;
    int[][] imageViews = {{R.id.nft1, R.id.nft2, R.id.nft3, R.id.nft4, R.id.nft5},
            {R.id.second_nft1, R.id.second_nft2, R.id.second_nft3, R.id.second_nft4, R.id.second_nft5},
            {R.id.third_nft1, R.id.third_nft2, R.id.third_nft3, R.id.third_nft4, R.id.third_nft5}};

    int[][] textViews = {{R.id.nft1Text, R.id.nft2Text, R.id.nft3Text, R.id.nft4Text, R.id.nft5Text},
            {R.id.second_nft1Text, R.id.second_nft2Text, R.id.second_nft3Text, R.id.second_nft4Text, R.id.second_nft5Text},
            {R.id.third_nft1Text, R.id.third_nft2Text, R.id.third_nft3Text, R.id.third_nft4Text, R.id.third_nft5Text}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_explore);
        String[][] titles = new String[3][5];
        String[][] imageDetails = new String[3][5];
        String[][] tokens = new String[3][5];
        String[][] address = new String[3][5];

        OkHttpClient client = new OkHttpClient();

        /* URL's for each of the sections in the explore page */
        String recently_addedurl = "https://opensea13.p.rapidapi.com/assets?collection_slug=cryptopunks&order_direction=asc&limit=5&include_orders=false";
        String most_viewedurl = "https://opensea13.p.rapidapi.com/assets?owner=0x276CD56089E7576Fb80d39a763aA0d213B98E948&order_direction=desc&limit=5&include_orders=false";
        String hot_new_itemsurl = "https://opensea13.p.rapidapi.com/assets?owner=0xd7c318E9F9129239F6bA4E10994137113dcF6244&order_direction=desc&limit=5&include_orders=false";

        String[] urlLists = {recently_addedurl, most_viewedurl, hot_new_itemsurl};
        for(int i = 0; i<urlLists.length; i++){

            int index_position = i;
            Request request = new Request.Builder()
                    .url(urlLists[i])
                    .get()
                    .addHeader("X-RapidAPI-Host", "opensea13.p.rapidapi.com")
                    .addHeader("X-RapidAPI-Key", "3b11ee2336msh5791c275fc5dbc6p11fa37jsn3cafb1ed4e82")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                /**
                 * This method is used to handle the API failure event.
                 * @param call This is the call made to the API
                 * @param e The exception that has occurred on failure
                 *
                 */
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                /**
                 * This method is used to handle the API success event.
                 * @param call This is the call made to the API
                 * @param response This is the response received from the API call containing the data
                 */
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String myResponse = response.body().string();
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            JSONArray arr = json.getJSONArray("assets");
                            for (int j = 0; j < arr.length(); j++) {

                                /* Each of the details needed for the on click event are taken from the reponse */
                                String post_id = arr.getJSONObject(j).getString("image_preview_url");
                                String post_name = arr.getJSONObject(j).getString("name");
                                String token_id = arr.getJSONObject(j).getString("token_id");
                                String asset_contract = arr.getJSONObject(j).getJSONObject("asset_contract").getString("address");

                                titles[index_position][j] = post_name;
                                imageDetails[index_position][j] = post_id;
                                tokens[index_position][j] = token_id;
                                address[index_position][j] = asset_contract;
                            }

                            for(int k =0; k<imageDetails[0].length; k++) {

                                /* Displaying images and names of each asset */
                                int imageView = imageViews[index_position][k];
                                ivResult = findViewById(imageViews[index_position][k]);
                                String textValue = titles[index_position][k];
                                LoadImage newImage = new LoadImage(ivResult);
                                newImage.execute(imageDetails[index_position][k]);
                                int position = k;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                            TextView textView = findViewById(textViews[index_position][position]);
                                            textView.setText(textValue);
                                    }
                                    });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
    }

        for(int i = 0; i< imageViews.length;i++){
            for(int j = 0; j< imageViews[0].length; j++){
                int positioni = i;
                int positionj = j;

                ImageView Image = findViewById(imageViews[i][j]);
                Image.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {

                        TextView value = findViewById(textViews[positioni][positionj]);
                        Toast.makeText(getApplicationContext(), "You clicked " + String.valueOf(value.getText()),
                                Toast.LENGTH_SHORT).show();
                        for(int i = 0; i<titles.length; i++){
                            for(int j = 0; j< titles[0].length; j++){

                                if(titles[i][j] == String.valueOf(value.getText())){
                                    String index1 = String.valueOf(i);
                                    String index2 = String.valueOf(j);

                                    /* Storing of relevant details on click of a particular asset
                                    * to be used in clickedActivity.java */
                                    SharedPreferences sp = getSharedPreferences("ClickedDetails", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("title", titles[i][j]);
                                    editor.putString("image", imageDetails[i][j]);
                                    editor.putString("token_id", tokens[i][j]);
                                    editor.putString("address", address[i][j]);
                                    editor.commit();

                                    startActivity(new Intent(getApplicationContext(), clickedActivityExplore.class));
                                    overridePendingTransition(0, 0);
                                }
                            }
                        }
                    }
                });
            }
        }

        /**
         * This method handles click on the bottom navbar and where to redirect the user
         * @param item This is the item in the navbar that the user clicks
         * @return 'true' is returned once user clicks a particular item and is redirected correctly
         */
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.explore);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){

                    case R.id.home:

                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.explore:
                        return true;

                    case R.id.chart:

                        startActivity(new Intent(getApplicationContext(), AnalyticsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.account:

                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}