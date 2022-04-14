package com.example.nftapp3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
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

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ExploreActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_explore);


        String[] titles = new String[5];
        String[] imageDetails = new String[5];

        OkHttpClient client = new OkHttpClient();

        String recently_addedurl = "https://opensea13.p.rapidapi.com/assets?collection_slug=cryptopunks&order_direction=asc&limit=5&include_orders=false";
        String most_viewedurl = "https://opensea13.p.rapidapi.com/assets?owner=0x276CD56089E7576Fb80d39a763aA0d213B98E948&order_direction=desc&limit=5&include_orders=false";
        String hot_new_itemsurl = "https://opensea13.p.rapidapi.com/assets?owner=0xE21DC18513e3e68a52F9fcDaCfD56948d43a11c6&order_direction=desc&limit=5&include_orders=false";

        String[] urlLists = {recently_addedurl, most_viewedurl, hot_new_itemsurl};
        for(int i = 0; i<urlLists.length; i++){

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


                            for (int i = 0; i < arr.length(); i++) {

                                String post_id = arr.getJSONObject(i).getString("image_preview_url");
    //                            String post_name = arr.getJSONObject(i).getString("name");


    //                            titles[i] = post_name;
                                imageDetails[i] = post_id;

    //                            Log.d("DATAbyme"+i, post_id);
    //                            Log.d("DATAbyme"+i, post_name);
    //                            Log.d("DATAbyme"+i, "--------");

                            }
                            //setTitles2(copy_titles2);
    //                        Log.d("titles", Arrays.deepToString(titles));
                            Log.d("Details", Arrays.deepToString(imageDetails));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

    //                    runOnUiThread(new Runnable() {
    //
    //                        @Override
    //                        public void run() {
    //
    //                            HorizontalViewAdapter adapter =  new HorizontalViewAdapter(ExploreActivity.this, titles, imageDetails);
    //                            HorizontalScrollView.setAdapter(adapter);
    //                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
    //                                @Override
    //                                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    //                                    //Toast.makeText(getApplicationContext(), "You clicked " + titles2[position],
    //                                            Toast.LENGTH_SHORT).show();
    //                                }
    //                            });
    //
    //                        }


                    }

                }
            });
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