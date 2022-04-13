package com.example.nftapp3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ImageView ivResult;
    GridView gridView;
    String[] numberWord = {"One", "Two", "One", "One", "One", "Two", "One", "One", "One", "One"};
    int[] images = {R.drawable.ic_bookmark, R.drawable.ic_chart, R.drawable.ic_home, R.drawable.ic_person,
            R.drawable.ic_bookmark, R.drawable.ic_chart, R.drawable.ic_home, R.drawable.ic_person, R.drawable.ic_home, R.drawable.ic_home};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        ImageDetails testDets = new ImageDetails("WALLETADDRESS");
//        testDets.getImageDetails();
        //beginWorkAndSendAck();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gridView = findViewById(R.id.gridView);

        String[] titles2 = new String[10];
        String[] imageDetails2 = new String[10];



        OkHttpClient client = new OkHttpClient();

        //String url = "https://opensea13.p.rapidapi.com/assets?collection_slug=cryptopunks&order_direction=desc&limit=20&include_orders=false";
        Request request = new Request.Builder()
                .url("https://opensea13.p.rapidapi.com/assets?collection_slug=cryptopunks&order_direction=desc&limit=13&include_orders=false")
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
                        int sale_details = 0;

                        for (int i = 3; i < arr.length(); i++)
                        {

                            String post_id = arr.getJSONObject(i).getString("image_preview_url");
                            String post_name = arr.getJSONObject(i).getString("name");
                            String sale_name = arr.getJSONObject(i).getString("last_sale");


                            if(sale_name.length() == 4){
                                ;
                            }
                            else{
                                JSONObject real_sale_name = arr.getJSONObject(i).getJSONObject("last_sale");
//                                JSONObject json2 = new JSONObject(sale_name);
//                                JSONArray arr2 = json.getJSONArray("payment_token");
                                //String sale_price = arr.getJSONObject(i).getString("eth_price");
                                sale_details += real_sale_name.getJSONObject("payment_token").getInt("eth_price");
//                                Log.d("SUCCESS", String.valueOf(real_sale_name.getJSONObject("payment_token").getString("eth_price")));
                                Log.d("SUCCESS2", String.valueOf(sale_details));
                            }

                            titles2[i-3] = post_name;
                            imageDetails2[i-3] = post_id;

//                            Log.d("DATAbyme"+i, post_id);
//                            Log.d("DATAbyme"+i, post_name);
//                            Log.d("DATAbyme"+i, "--------");

                        }
                        //setTitles2(copy_titles2);
                        Log.d("titles", Arrays.deepToString(titles2));
                        Log.d("Details", Arrays.deepToString(imageDetails2));
                        TextView sales = findViewById(R.id.saleDetails);
                        sales.setText("TOTAL VALUE:      " + sale_details);






                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            GridAdapter adapter =  new GridAdapter(HomeActivity.this, titles2, imageDetails2);
                            gridView.setAdapter(adapter);
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                                    Toast.makeText(getApplicationContext(), "You clicked " + titles2[position],
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });



                }

            }
        });







//        String urlLink = "https://lh3.googleusercontent.com/I5rFdPt-FPsGsjF7oaoPGhdLq22jW6JCOTMUB5yvdF7JK9xdUQZxZp1_fwlZGApBjEploJXkr_k4b0nc_hWDeEqqrQ=s250";


//        Button btnFetch = findViewById(R.id.fetch_image);
//

//        btnFetch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//
//            }
//
//        });

//        ivResult = findViewById(R.id.imageView5);
//        String urlLink = "https://lh3.googleusercontent.com/I5rFdPt-FPsGsjF7oaoPGhdLq22jW6JCOTMUB5yvdF7JK9xdUQZxZp1_fwlZGApBjEploJXkr_k4b0nc_hWDeEqqrQ=s250";
//        LoadImage newImage = new LoadImage(ivResult);
//        newImage.execute(urlLink);




       // System.out.println(Arrays.deepToString);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);


//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch(item.getItemId()){

                    case R.id.home:

                        return true;


                    case R.id.explore:

                        startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
                        overridePendingTransition(0, 0);
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

//    public String[] beginWorkAndSendAck(){
//
//
//    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public LoadImage(ImageView ivResult){
            this.imageView = ivResult;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try{
                InputStream inputStream = new java.net.URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);


            } catch(IOException e) {
                e.printStackTrace();

            }
//            Log.d("BIT", String.valueOf(bitmap));
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            ivResult.setImageBitmap(bitmap);
        }
    }





}
