package com.example.nftapp3;

import android.content.Intent;
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


public class ExploreActivity extends AppCompatActivity {

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

//        ivResult = findViewById(R.id.nft1);
//        ivResult.setImageResource(R.id.ic);
//
//        String uri = "@drawable/ic_home";  // where myresource (without the extension) is the file
//
//        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
//
//        ivResult= (ImageView)findViewById(R.id.nft1);
//        Drawable res = getResources().getDrawable(imageResource);
//        ivResult.setImageDrawable(res);

        String[][] titles = new String[3][5];
        String[][] imageDetails = new String[3][5];

        OkHttpClient client = new OkHttpClient();

        String recently_addedurl = "https://opensea13.p.rapidapi.com/assets?collection_slug=cryptopunks&order_direction=asc&limit=5&include_orders=false";
        String most_viewedurl = "https://opensea13.p.rapidapi.com/assets?owner=0x276CD56089E7576Fb80d39a763aA0d213B98E948&order_direction=desc&limit=5&include_orders=false";
        String hot_new_itemsurl = "https://opensea13.p.rapidapi.com/assets?owner=0x30d58a2e004170f839295c3f37D7E7dfd0ef2310&order_direction=desc&limit=5&include_orders=false";

        String[] urlLists = {recently_addedurl, most_viewedurl, hot_new_itemsurl};
        for(int i = 0; i<urlLists.length; i++){

            int index_position = i;
            Request request = new Request.Builder()
                    .url(urlLists[i])
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
                    if (response.isSuccessful()) {
                        String myResponse = response.body().string();

    //                    String jsonData = myResponse.body().string();
                        try {
                            JSONObject json = new JSONObject(myResponse);

                            JSONArray arr = json.getJSONArray("assets");


                            for (int j = 0; j < arr.length(); j++) {

                                String post_id = arr.getJSONObject(j).getString("image_preview_url");
                                String post_name = arr.getJSONObject(j).getString("name");


                                titles[index_position][j] = post_name;
                                imageDetails[index_position][j] = post_id;


                            }

                            Log.d("Details", Arrays.deepToString(imageDetails));
                            Log.d("Titles", Arrays.deepToString(titles));
                            for(int k =0; k<imageDetails[0].length; k++) {

                                int imageView = imageViews[index_position][k];
                                ivResult = findViewById(imageViews[index_position][k]);
                                String textValue = titles[index_position][k];
                                Log.d("Text Value", textValue);
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
                        Log.d("Title", String.valueOf(value.getText()));

                        Toast.makeText(getApplicationContext(), "You clicked " + String.valueOf(value.getText()),
                                Toast.LENGTH_SHORT).show();
                        for(int i = 0; i<titles.length; i++){
                            for(int j = 0; j< titles[0].length; j++){

                                if(titles[i][j] == String.valueOf(value.getText())){
                                    String index1 = String.valueOf(i);
                                    String index2 = String.valueOf(j);
                                    Log.d("YOU NEED INDEX", index1 + " " + index2);

                                }
                            }

                        }


                    }

                });

            }
        }





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