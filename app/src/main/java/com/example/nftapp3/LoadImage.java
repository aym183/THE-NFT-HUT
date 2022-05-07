package com.example.nftapp3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;


public class LoadImage extends AsyncTask<String, Void, Bitmap> {

        ImageView ivResult;
        ImageView imageView;
        public LoadImage(ImageView ivResult){
            this.imageView = ivResult;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Log.d("ImageView", String.valueOf(this.imageView));


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

            Log.d("Test ImageView", String.valueOf(this.imageView));
            Log.d("Test ImageView2", String.valueOf(ivResult));
            this.imageView.setImageBitmap(bitmap);
        }
    }
