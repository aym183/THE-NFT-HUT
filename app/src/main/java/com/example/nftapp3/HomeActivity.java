package com.example.nftapp3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.io.InputStream;


public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ImageView ivResult;
    GridView gridView;
    String[] numberWord = {"One", "Two", "One", "One", "Two", "One"};
    int[] images = {R.drawable.ic_bookmark, R.drawable.ic_chart, R.drawable.ic_home, R.drawable.ic_person,
    R.drawable.ic_home, R.drawable.ic_bookmark};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        gridView = findViewById(R.id.gridView);
        GridAdapter adapter =  new GridAdapter(HomeActivity.this, numberWord, images);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(getApplicationContext(), "You clicked " + numberWord[position],
                        Toast.LENGTH_SHORT).show();
            }
        });
//        Button btnFetch = findViewById(R.id.fetch_image);
//        ivResult = findViewById(R.id.image_URL);

//        btnFetch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                String urlLink = "https://lh3.googleusercontent.com/I5rFdPt-FPsGsjF7oaoPGhdLq22jW6JCOTMUB5yvdF7JK9xdUQZxZp1_fwlZGApBjEploJXkr_k4b0nc_hWDeEqqrQ=s250";
//                LoadImage newImage = new LoadImage(ivResult);
//                newImage.execute(urlLink);
//            }
//
//        });



        String urlLink = "https://lh3.googleusercontent.com/I5rFdPt-FPsGsjF7oaoPGhdLq22jW6JCOTMUB5yvdF7JK9xdUQZxZp1_fwlZGApBjEploJXkr_k4b0nc_hWDeEqqrQ=s250";
        LoadImage newImage = new LoadImage(ivResult);
        newImage.execute(urlLink);
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
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            ivResult.setImageBitmap(bitmap);
        }
    }





}
