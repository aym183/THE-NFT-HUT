package com.example.nftapp3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.lang.Object;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnalyticsActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    ImageView doodleimageView;
    TextView spentValue;
    ImageView BAYCimageView;
    ImageView MAYCimageView;
    Button rankingsButton;
    Button activityButton;
    private ImageView ivResult;
    private String url;
    private TextView ivView;
    int detailsValues[];
    int Doodlecount , BAYCcount, MAYCcount = 0;
    int ranking_count = 0;

    String[] collections = {"Doodles", "MAYC", "BAYC" };
    HashMap<String, Integer> floorRankings = new HashMap<String, Integer>();
    HashMap<String, Integer> MarketCap = new HashMap<String, Integer>();

    String[] urlArray = {"https://opensea13.p.rapidapi.com/collection/doodles-official",
    "https://opensea13.p.rapidapi.com/collection/boredapeyachtclub",
    "https://opensea13.p.rapidapi.com/collection/mutant-ape-yacht-club"};

    int[] imageButtons = {R.id.stats_nft1, R.id.stats_nft2, R.id.stats_nft3};

    int[] textViews = {R.id.stats_nft1Text, R.id.stats_nft2Text, R.id.stats_nft3Text};

    int[] collectionDetailViews = {R.id.totalSales, R.id.floorPrice, R.id.sevenSales, R.id.sevenAverage,
    R.id.thirtySales, R.id.marketCap, R.id.owners};

    int[] rankingsButtons = {R.id.rankingResult3, R.id.rankingResult2, R.id.rankingresult1,
            R.id.rankingResult6, R.id.rankingResult5, R.id.rankingResult4};

    String[] textValues = {"Total Sales: ", "Floor Price      ", "7 Day Sales: ", "7 Day Average: ", "30 Day Sales: ", "Market Cap: ",
    "Owners: "};

    ArrayList<Integer> rankingValues = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analytics);

        for(int i = 0; i < urlArray.length; i++){
            getData(urlArray[i], imageButtons[i], textViews[i], i);
        }

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Portfolio", Context.MODE_PRIVATE);
        String value = sp.getString("value", "");
        spentValue = findViewById(R.id.spent_textview);

        // Multiplying with value of eth in dollars to get original dollar value
        spentValue.setText("TOTAL SPENT: $" + (Integer.parseInt(value)*3050));

        doodleimageView = findViewById(R.id.stats_nft1);
        doodleimageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Toast.makeText(AnalyticsActivity.this, "YOU CLICKED IT!", Toast.LENGTH_SHORT).show();
                Doodlecount+=1;
                if(Doodlecount % 2==0){

                    Doodlecount = 0;
                    BAYCcount = 0;
                    MAYCcount = 0;

                    ImageView ethView = findViewById(R.id.imageView11);
                    ImageView verticalLine = findViewById(R.id.imageView9);
                    ImageView verticalLine2 = findViewById(R.id.imageView10);
                    ethView.setVisibility(View.INVISIBLE);
                    verticalLine.setVisibility(View.INVISIBLE);
                    verticalLine2.setVisibility(View.INVISIBLE);


                    for (int i = 0; i < collectionDetailViews.length; i++) {
                        TextView detailsSet = findViewById(collectionDetailViews[i]);
                        detailsSet.setVisibility(View.INVISIBLE);
                    }

                }
                else{
                    collectionData(urlArray[0]+"/stats");
                }


            }
        });

        BAYCimageView = findViewById(R.id.stats_nft2);
        BAYCimageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Toast.makeText(AnalyticsActivity.this, "YOU CLICKED IT!", Toast.LENGTH_SHORT).show();
                BAYCcount+=1;
                if(BAYCcount % 2==0){

                    Doodlecount = 0;
                    BAYCcount = 0;
                    MAYCcount = 0;

                    ImageView ethView = findViewById(R.id.imageView11);
                    ImageView verticalLine = findViewById(R.id.imageView9);
                    ImageView verticalLine2 = findViewById(R.id.imageView10);
                    ethView.setVisibility(View.INVISIBLE);
                    verticalLine.setVisibility(View.INVISIBLE);
                    verticalLine2.setVisibility(View.INVISIBLE);


                    for (int i = 0; i < collectionDetailViews.length; i++) {
                        TextView detailsSet = findViewById(collectionDetailViews[i]);
                        detailsSet.setVisibility(View.INVISIBLE);
                    }

                }
                else{
                    collectionData(urlArray[1]+"/stats");
                }

            }
        });

        MAYCimageView = findViewById(R.id.stats_nft3);
        MAYCimageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Toast.makeText(AnalyticsActivity.this, "YOU CLICKED IT!", Toast.LENGTH_SHORT).show();
                MAYCcount+=1;
                if(MAYCcount % 2==0){

                    Doodlecount = 0;
                    BAYCcount = 0;
                    MAYCcount = 0;

                    ImageView ethView = findViewById(R.id.imageView11);
                    ImageView verticalLine = findViewById(R.id.imageView9);
                    ImageView verticalLine2 = findViewById(R.id.imageView10);
                    ethView.setVisibility(View.INVISIBLE);
                    verticalLine.setVisibility(View.INVISIBLE);
                    verticalLine2.setVisibility(View.INVISIBLE);


                    for (int i = 0; i < collectionDetailViews.length; i++) {
                        TextView detailsSet = findViewById(collectionDetailViews[i]);
                        detailsSet.setVisibility(View.INVISIBLE);
                    }

                }
                else{
                    collectionData(urlArray[2]+"/stats");
                }

            }
        });

        activityButton = findViewById(R.id.activity_button);
        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontalScrollView);
                horizontalScrollView.setVisibility(View.VISIBLE);
            }
        });

        rankingsButton = findViewById(R.id.rankings_button);
        rankingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rankingsButton.setPressed(true);
                ranking_count+=1;

                TextView floor_text = findViewById(R.id.ranking_floor);
                TextView marketcap = findViewById(R.id.marketcap_ranking);
                ImageView eth1 = findViewById(R.id.third_rankicon);
                ImageView eth2 = findViewById(R.id.second_rankingicon);
                ImageView eth3 = findViewById(R.id.first_rankingicon);

                if(ranking_count%2 ==0){

                    floor_text.setVisibility(View.INVISIBLE);
                    marketcap.setVisibility(View.INVISIBLE);
                    eth1.setVisibility(View.INVISIBLE);
                    eth2.setVisibility(View.INVISIBLE);
                    eth3.setVisibility(View.INVISIBLE);

                    for(int i = 0; i< rankingsButtons.length; i++){

                        TextView visibilityView = findViewById(rankingsButtons[i]);
                        visibilityView.setVisibility(View.INVISIBLE);
                    }

                }

                else{
                    floor_text.setVisibility(View.VISIBLE);
                    marketcap.setVisibility(View.VISIBLE);
                    eth1.setVisibility(View.VISIBLE);
                    eth2.setVisibility(View.VISIBLE);
                    eth3.setVisibility(View.VISIBLE);

                    for(int i = 0; i< rankingsButtons.length; i++){

                        TextView visibilityView = findViewById(rankingsButtons[i]);
                        visibilityView.setVisibility(View.VISIBLE);
                    }

                }



                for(int i =0; i< urlArray.length; i++) {
                    rankingsData(urlArray[i], i);
                }
                HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontalScrollView);
                horizontalScrollView.setVisibility(View.INVISIBLE);
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


    public void rankingsData(String urls, int position){


        String[] titles = new String[5];
        String[] imageDetails = new String[5];




            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(urls+"/stats")
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
                            int marketCap = json.getInt("market_cap");

                            detailsValues = new int[]{floorPrice, marketCap};

                            floorRankings.put(collections[position], floorPrice);
                            MarketCap.put(collections[position], marketCap);

                            System.out.println(floorRankings);

                            if(floorRankings.size() == 3){


                                sortByValue(floorRankings, "floor");

                                sortByValue(MarketCap, "market");


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        for(int i = 0; i<3; i++){

                                            TextView textView = findViewById(rankingsButtons[i]);
                                            textView.setText(String.valueOf(i+1 + ". "+ collections[i] + ":     " +  rankingValues.get(i)));
                                        }

                                        for(int i = 3; i<6; i++){

                                            TextView textView = findViewById(rankingsButtons[i]);
                                            textView.setText(String.valueOf(i-2 + ". "+ collections[i-3] + ": " + rankingValues.get(i)));
                                        }


                                    }
                                });

                                System.out.println("WORKK FOR ME" + rankingValues);
                                System.out.println("Ready");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }
            });

    }

    public Map<String, Integer> sortByValue(Map<String, Integer> scores, String value){

        Map<String, Integer> sortedbyValue = new LinkedHashMap<>();

        Set<Map.Entry<String, Integer>> entrySet = scores.entrySet();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(entrySet);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            entryList.sort((x, y) -> x.getValue().compareTo(y.getValue()));
        }
        for(Map.Entry<String, Integer> e: entryList){
            sortedbyValue.put(e.getKey(), e.getValue());
        }


        for (String i : sortedbyValue.keySet()) {
            System.out.println("key: " + i + " value: " + sortedbyValue.get(i));
            rankingValues.add(sortedbyValue.get(i));
        }

        System.out.println("WORKK" + rankingValues);
        return sortedbyValue;


    }



}

