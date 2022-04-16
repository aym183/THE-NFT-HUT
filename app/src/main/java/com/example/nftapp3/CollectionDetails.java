package com.example.nftapp3;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CollectionDetails extends AnalyticsActivity {

    private int[] textViews;
    private String url;
    int[] detailsValues;

    public CollectionDetails(String url) {

        this.url = url;

    }

    public void getCollectionDetails(String[] textValues, int[] textViews) {


        String[] titles = new String[5];
        String[] imageDetails = new String[5];

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(this.url)
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
//                                ImageView ethView = findViewById(R.id.imageView11);
//                                ImageView verticalLine = findViewById(R.id.imageView9);
//                                ImageView verticalLine2 = findViewById(R.id.imageView10);
//                                ethView.setVisibility(View.VISIBLE);
//                                verticalLine.setVisibility(View.VISIBLE);
//                                verticalLine2.setVisibility(View.VISIBLE);


                                for (int i = 0; i < textViews.length; i++) {
//                                    TextView detailsSet = ANfindViewById(textViews[i]);
//                                    detailsSet.setVisibility(View.VISIBLE);
//                                    detailsSet.setText(textValues[i] + " " + detailsValues[i]);
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
