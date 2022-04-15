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
    ImageButton imageButton;
    Button rankingsButton;
    private ImageView ivResult;
    private String url;
    private TextView ivView;

    String[] urlArray = {" https://opensea13.p.rapidapi.com/collection/doodles-official",
    "https://opensea13.p.rapidapi.com/collection/cryptopunks",
    "https://opensea13.p.rapidapi.com/collection/boredapeyachtclub",
    "https://opensea13.p.rapidapi.com/collection/mutant-ape-yacht-club",
    "https://opensea13.p.rapidapi.com/collection/azuki"};

    int[] imageViews = {R.id.stats_nft1, R.id.stats_nft2, R.id.stats_nft3,
            R.id.stats_nft4, R.id.stats_nft5};

    int[] textViews = {R.id.stats_nft1Text, R.id.stats_nft2Text, R.id.stats_nft3Text,
            R.id.stats_nft4Text, R.id.stats_nft5Text};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analytics);

        imageButton = findViewById(R.id.stats_nft1);
        imageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

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

    public void getData(String url, ImageView newivResult, TextView newivView){

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
                        JSONObject json = new JSONObject(myResponse);

                        JSONArray arr = json.getJSONArray("assets");


                        for (int j = 0; j < arr.length(); j++) {

                            String post_id = arr.getJSONObject(j).getString("image_preview_url");
                            String post_name = arr.getJSONObject(j).getString("name");


                            titles[j] = post_name;
                            imageDetails[j] = post_id;


                        }

                        Log.d("Details", Arrays.deepToString(imageDetails));
                        Log.d("Titles", Arrays.deepToString(titles));
                        for(int k =0; k<imageDetails.length; k++) {

//                            int imageView = imageViews[index_position][k];
//                            ivResult = findViewById(imageViews[index_position][k]);
//                            String textValue = titles[k];
//                            LoadImage newImage = new LoadImage(ivResult);
//                            newImage.execute(imageDetails[k]);
//                            int position = k;
//
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    TextView textView = findViewById(textViews[index_position][position]);
//                                    textView.setText(textValue);
//
//                                }
//                            });


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        });

    }





}

