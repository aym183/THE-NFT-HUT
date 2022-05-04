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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The clickedActivity class handles the event once the user clicks on an activity on the home page
 */
public class clickedActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked);

        /* The operation to retrieve the clicked asset details to
        display and perform operations*/

        SharedPreferences sp = getApplicationContext().getSharedPreferences("ClickedDetails", Context.MODE_PRIVATE);
        String titleValue = sp.getString("title", "");
        String imageValue = sp.getString("image", "");
        String token = sp.getString("token_id", "");
        String address = sp.getString("address", "");

        TextView TextName = findViewById(R.id.textName);
        TextName.setText(titleValue);
        ImageView ivResult = findViewById(R.id.clickedImageView);
        LoadImage setImage = new LoadImage(ivResult);
        setImage.execute(imageValue);
        String urlShare;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://opensea13.p.rapidapi.com/asset/" + address + "/" + token + "?include_orders=false")
                .get()
                .addHeader("X-RapidAPI-Host", "opensea13.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "3b11ee2336msh5791c275fc5dbc6p11fa37jsn3cafb1ed4e82")
                .build();

        client.newCall(request).enqueue(new Callback() {
            /**
             * This method is used to handle the API failure event.
             * @param call This is the call made to the API
             * @param e The exception that has occurred on failure
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
                        int[] attributes = {R.id.attributeText1, R.id.attributeText2 ,R.id.attributeText3,
                        R.id.attributeText4, R.id.attributeText5, R.id.attributeText6};
                        JSONObject json = new JSONObject(myResponse);

                        /* The operation to store the external URL of the asset to SharedPreferences */
                        String external_url = json.getString("external_link");
                        SharedPreferences sp = getSharedPreferences("externalURL", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("external_url",external_url);
                        editor.commit();

                        /* On response, traits of an asset are taken */
                        JSONArray traits = json.getJSONArray("traits");
                        Button extButton = findViewById(R.id.externalButton);

                        /* Click listener that handles "LEARN MORE" button event.
                        *  Takes user to external link on click */
                        extButton.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v) {
                                Uri webpage = Uri.parse(external_url);
                                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                                startActivity(webIntent);
                            }
                        });

                        /* Event that assigns traits to assets */
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * This method is used the handle the click of the back arrow button.
     * Takes user back to the home page
     * @param v This is the the id of the view of the element
     */
    public void backArrowEvent(View v){
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        overridePendingTransition(0, 0);
    }

    /**
     * This method is used to handle the click of the share button (ShareActionProvider)
     * @param menu The id of the menu that's displayed on click
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    /**
     * This method is used to display and handle events (ShareActionProvider)
     * related to the share button. Let's user share the link to the asset
     * @param item The id of the entire share view
     */
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
