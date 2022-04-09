package com.example.nftapp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NavbarRedirect extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        OkHttpClient client = new OkHttpClient();

        //String url = "https://opensea13.p.rapidapi.com/assets?collection_slug=cryptopunks&order_direction=desc&limit=20&include_orders=false";
        Request request = new Request.Builder()
                .url("https://opensea13.p.rapidapi.com/assets?collection_slug=cryptopunks&order_direction=desc&limit=20&include_orders=false")
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
                        Log.d("WHAT WORKS", "YOU DO");
                        Log.d("WHAT WORKS2", "YOU DO2");

                        Log.d("DATA", String.valueOf(json.get("assets")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch(item.getItemId()){

                    case R.id.home:

                        onAPICreate();
                        fragment = new HomeFragment();
                        System.out.println("HOME");
                        break;

                    case R.id.explore:

                        fragment = new ExploreFragment();
                        System.out.println("EXPLORE");
                        break;


                    case R.id.chart:

                        fragment = new AnalyticsFragment();
                        System.out.println("ANALYTICS");
                        break;


                    case R.id.account:

                        fragment = new AccountFragment();
                        System.out.println("ACCOUNT");
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                return true;
            }
        });
    }

    public void onAPICreate(){
        OkHttpClient client = new OkHttpClient();

        //String url = "https://opensea13.p.rapidapi.com/assets?collection_slug=cryptopunks&order_direction=desc&limit=20&include_orders=false";
        Request request = new Request.Builder()
                .url("https://opensea13.p.rapidapi.com/assets?collection_slug=cryptopunks&order_direction=desc&limit=20&include_orders=false")
                .get()
                .addHeader("X-RapidAPI-Host", "opensea13.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "3b11ee2336msh5791c275fc5dbc6p11fa37jsn3cafb1ed4e82")
                .build();

        TextView imageView = findViewById(R.id.home_textview);
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
                    System.out.println(response.body().string());
                    imageView.setText(myResponse);

                }

            }
        });

    }
}