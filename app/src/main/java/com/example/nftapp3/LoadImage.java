package com.example.nftapp3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class is used to Load the images in the respective views
 */
public class LoadImage extends AsyncTask<String, Void, Bitmap> {

        ImageView ivResult;
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
            this.imageView.setImageBitmap(bitmap);
        }
    }
