package com.example.nftapp3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class clickedActivityExplore extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_explore);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("ClickedDetails", Context.MODE_PRIVATE);
        String titleValue = sp.getString("title", "");
        String imageValue = sp.getString("image", "");
        String token = sp.getString("token_id", "");
        String address = sp.getString("address", "");
        Log.d("token_id", token);
        Log.d("address", address);

        TextView TextName = findViewById(R.id.textName);
        TextName.setText(titleValue);
        ImageView ivResult = findViewById(R.id.clickedImageView);
        LoadImage setImage = new LoadImage(ivResult);
        setImage.execute(imageValue);

        OkHttpClient client = new OkHttpClient();

        //String url = "https://opensea13.p.rapidapi.com/assets?collection_slug=cryptopunks&order_direction=desc&limit=20&include_orders=false";
        Request request = new Request.Builder()
                .url("https://opensea13.p.rapidapi.com/asset/" + address + "/" + token + "?include_orders=false")
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
                    Log.d("Response", myResponse);
//                    String jsonData = myResponse.body().string();
                    try {
                        int[] attributes = {R.id.attributeText1, R.id.attributeText2 ,R.id.attributeText3,
                                R.id.attributeText4, R.id.attributeText5, R.id.attributeText6};
                        JSONObject json = new JSONObject(myResponse);

                        String external_url = json.getString("permalink");
                        SharedPreferences sp = getSharedPreferences("externalURL", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("external_url",external_url);
                        editor.commit();

                        JSONArray traits = json.getJSONArray("traits");

                        Button extButton = findViewById(R.id.externalButton);
                        extButton.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v) {

                                Uri webpage = Uri.parse(external_url);
                                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                                startActivity(webIntent);

                            }

                        });

                        for(int i = 0; i<6; i++){
                            int position = i;
                            String attribute = traits.getJSONObject(i).getString("value");
                            Log.d("attribute", attribute);

                            if(attribute.contains("attributes")){
                                ;
                            }
                            else{

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        TextView attrValue = findViewById(attributes[position]);
                                        attrValue.setText(attribute);


                                    }

                                });

                            }

                        }


//                        Log.d("external_url", external_url);



//                        JSONArray arr = json.getJSONArray("assets");
//                        int sale_details = 0;

                        // Take link to show on open sea with external intent (permalink)
                        // external link (external_link)
                        // traits



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }





                }

            }
        });


    }

    public void backArrowEvent(View v){
        startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
        overridePendingTransition(0, 0);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.share){
            SharedPreferences sp = getApplicationContext().getSharedPreferences("externalURL", Context.MODE_PRIVATE);
            String externalShare = sp.getString("external_url", "");
            ApplicationInfo api =  getApplicationContext().getApplicationInfo();
            String apkpath = api.sourceDir;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");

            intent.putExtra(Intent.EXTRA_TEXT, "Check this NFT out! " + externalShare);
            startActivity(Intent.createChooser(intent, "ShareVia"));
        }
        return true;
    }

}
