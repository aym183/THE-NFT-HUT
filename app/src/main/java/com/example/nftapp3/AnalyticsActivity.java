package com.example.nftapp3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AnalyticsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ImageView doodleimageView;
    ImageView BAYCimageView;
    ImageView MAYCimageView;
    Button rankingsButton;
    private ImageView ivResult;
    private String url;
    private TextView ivView;
    int detailsValues[];

    String[] urlArray = {"https://opensea13.p.rapidapi.com/collection/doodles-official",
    "https://opensea13.p.rapidapi.com/collection/boredapeyachtclub",
    "https://opensea13.p.rapidapi.com/collection/mutant-ape-yacht-club"};

    int[] imageButtons = {R.id.stats_nft1, R.id.stats_nft2, R.id.stats_nft3};

    int[] textViews = {R.id.stats_nft1Text, R.id.stats_nft2Text, R.id.stats_nft3Text};

    int[] collectionDetailViews = {R.id.totalSales, R.id.floorPrice, R.id.sevenSales, R.id.sevenAverage,
    R.id.thirtySales, R.id.marketCap, R.id.owners};

    String[] textValues = {"Total Sales: ", "Floor Price      ", "7 Day Sales: ", "7 Day Average: ", "30 Day Sales: ", "Market Cap: ",
    "Owners: "};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analytics);

        for(int i = 0; i < urlArray.length; i++){
            getData(urlArray[i], imageButtons[i], textViews[i], i);
        }
//        for(int i = 2; i < 5; i++){
//            getData(urlArray[i], imageButtons[i], textViews[i], i);
//        }

        doodleimageView = findViewById(R.id.stats_nft1);
        doodleimageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                collectionData(urlArray[0]+"/stats");
                Toast.makeText(AnalyticsActivity.this, "YOU CLICKED IT!", Toast.LENGTH_SHORT).show();
            }
        });

        BAYCimageView = findViewById(R.id.stats_nft2);
        BAYCimageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                collectionData(urlArray[1]+"/stats");
                Toast.makeText(AnalyticsActivity.this, "YOU CLICKED IT!", Toast.LENGTH_SHORT).show();
            }
        });

        MAYCimageView = findViewById(R.id.stats_nft3);
        MAYCimageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                collectionData(urlArray[2]+"/stats");
                Toast.makeText(AnalyticsActivity.this, "YOU CLICKED IT!", Toast.LENGTH_SHORT).show();
            }
        });



        rankingsButton = findViewById(R.id.rankings_button);
        rankingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rankingsButton.setPressed(true);


                Toast.makeText(AnalyticsActivity.this, "YOU CLICKED RANKINGS!", Toast.LENGTH_SHORT).show();

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.chart);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch(item.getItemId()){

                    case R.id.home:

                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.explore:

                        startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.chart:


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

    public void getData(String url, int newivResult, int newivView, int position){

        Log.d("URLS"+position, url);
        String[] titles = new String[5];
        String[] imageDetails = new String[5];

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
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
                        JSONObject json = new JSONObject(myResponse).getJSONObject("collection");

                        System.out.println("YOU2 "+ String.valueOf(json));

                        String post_id = json.getString("image_url");
                        String post_name = json.getString("name");

//                        titles[position] = post_name;
//                        imageDetails[position] = post_id;

                        ivResult = findViewById(imageButtons[position]);
                        LoadImage newImage = new LoadImage(ivResult);
                        newImage.execute(post_id);
                        String textValue = post_name;

                        runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    TextView textView = findViewById(textViews[position]);
                                    textView.setText(textValue);

                                }
                            });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        });

    }


    public void collectionData(String url){
        String[] titles = new String[5];
        String[] imageDetails = new String[5];

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
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
                        JSONObject json = new JSONObject(myResponse).getJSONObject("stats");



                        int floorPrice = json.getInt("floor_price");
                        int sevenDay = json.getInt("seven_day_sales");
                        int thirtyDay = json.getInt("thirty_day_sales");
                        int noOwners = json.getInt("num_owners");
                        int marketCap = json.getInt("market_cap");
                        int sevenDayAvg = json.getInt("seven_day_average_price");
                        int totalSales = json.getInt("total_sales");

                        detailsValues = new int[]{totalSales, floorPrice, sevenDay, sevenDayAvg, thirtyDay, marketCap, noOwners};

                        System.out.println("YOU2 " + String.valueOf(detailsValues));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ImageView ethView = findViewById(R.id.imageView11);
                                ImageView verticalLine = findViewById(R.id.imageView9);
                                ImageView verticalLine2 = findViewById(R.id.imageView10);
                                ethView.setVisibility(View.VISIBLE);
                                verticalLine.setVisibility(View.VISIBLE);
                                verticalLine2.setVisibility(View.VISIBLE);


                                for (int i = 0; i < collectionDetailViews.length; i++) {
                                    TextView detailsSet = findViewById(collectionDetailViews[i]);
                                    detailsSet.setVisibility(View.VISIBLE);
                                    detailsSet.setText(textValues[i] + " " + detailsValues[i]);
                                }

                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        });
    }




}

