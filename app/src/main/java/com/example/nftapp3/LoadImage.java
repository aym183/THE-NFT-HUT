package com.example.nftapp3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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



//        ivResult = convertView.findViewById(R.id.imageViewGrid);
//        TextView textView = convertView.findViewById(R.id.textViewGrid);
//        HorizontalViewAdapter.LoadImage newImage = new HorizontalViewAdapter.LoadImage(ivResult);
//        newImage.execute(numberImage[position]);
////        Log.d("Titles"+position, String.valueOf(position));
////        Log.d("Titles2"+position, numberImage[position]);
//
//        // ivResult.setImageResource(numberImage[position]);
//        textView.setText(titles[position]);
//
//        return convertView;
//


