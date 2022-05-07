package com.example.nftapp3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The HomeActivity Class handles all operations relating to the Home page
 * where the user can view their assets and learn ore about them
 */
public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ImageView ivResult;
    GridView gridView;
    String[] numberWord = {"One", "Two", "One", "One", "One", "Two", "One", "One", "One", "One"};
    int[] images = {R.drawable.ic_bookmark, R.drawable.ic_chart, R.drawable.ic_home, R.drawable.ic_person,
            R.drawable.ic_bookmark, R.drawable.ic_chart, R.drawable.ic_home, R.drawable.ic_person, R.drawable.ic_home, R.drawable.ic_home};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gridView = findViewById(R.id.gridView);

        /**
         * Getting username from SharedPreferences to display
         */
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Username", Context.MODE_PRIVATE);
        String value = sp.getString("username_value", "");
        TextView username = findViewById(R.id.home_textview);
        username.setText(value);

        String[] titles2 = new String[10];
        String[] imageDetails2 = new String[10];
        String[] tokens = new String[10];
        String[] address = new String[10];

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://opensea13.p.rapidapi.com/assets?collection_slug=cryptopunks&order_direction=desc&limit=13&include_orders=false")
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
                if(response.isSuccessful()){
                    String myResponse = response.body().string();

                    try {
                        JSONObject json = new JSONObject(myResponse);
                        JSONArray arr = json.getJSONArray("assets");
                        int sale_details = 0;

                        for (int i = 3; i < arr.length(); i++) {

                            /**
                             * After data is recieved from response
                             * the details of the assets are taken to be displayed
                             */
                            String post_id = arr.getJSONObject(i).getString("image_preview_url");
                            String post_name = arr.getJSONObject(i).getString("name");
                            String sale_name = arr.getJSONObject(i).getString("last_sale");
                            String token_id = arr.getJSONObject(i).getString("token_id");
                            String asset_contract = arr.getJSONObject(i).getJSONObject("asset_contract").getString("address");

                            if (sale_name.length() == 4) {
                                ;
                            } else {
                                /**
                                 * Calculating the total price of the user's collection
                                 */
                                JSONObject real_sale_name = arr.getJSONObject(i).getJSONObject("last_sale");
                                sale_details += real_sale_name.getJSONObject("payment_token").getInt("eth_price");
                            }

                            titles2[i - 3] = post_name;
                            imageDetails2[i - 3] = post_id;
                            tokens[i - 3] = token_id;
                            address[i - 3] = asset_contract;
                        }

                        /**
                         * Saving total value of porfolio to SharedPreferences to be used later
                         * and setting total value in Home Page
                         */
                        TextView sales = findViewById(R.id.saleDetails);
                        SharedPreferences sp = getSharedPreferences("Portfolio", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("value", String.valueOf(sale_details));
                        editor.commit();
                        sales.setText("TOTAL VALUE:      " + sale_details);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            /**
                             * Displaying complete collection in GridView
                             * and adding click listener that regsters which asset has been clicked
                             */
                            GridAdapter adapter =  new GridAdapter(HomeActivity.this, titles2, imageDetails2);
                            gridView.setAdapter(adapter);
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                                    Toast.makeText(getApplicationContext(), "You clicked " + titles2[position],
                                            Toast.LENGTH_SHORT).show();

                                    SharedPreferences sp = getSharedPreferences("ClickedDetails", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("title", titles2[position]);
                                    editor.putString("image", imageDetails2[position]);
                                    editor.putString("token_id", tokens[position]);
                                    editor.putString("address", address[position]);
                                    editor.commit();

                                    startActivity(new Intent(getApplicationContext(), clickedActivity.class));
                                    overridePendingTransition(0, 0);

                                }
                            });
                        }
                    });
                }
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
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


    /**
     * This class is used to Load the images in the respective views
     */
    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public LoadImage(ImageView ivResult){
            this.imageView = ivResult;
        }

        /**
         * This method is used to get bit value of the image taken from the URL
         * @param strings This is the URL passed
         */
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
            return bitmap;
        }

        /**
         * This method is used to display the images.
         * @param bitmap The bit value of each image
         */
        @Override
        protected void onPostExecute(Bitmap bitmap){
            ivResult.setImageBitmap(bitmap);
        }
    }
}
