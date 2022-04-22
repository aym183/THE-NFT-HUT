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
import java.util.ArrayList;
import java.util.Arrays;

public class GridAdapter extends BaseAdapter {

    ImageView ivResult;
    private Context context;
    private LayoutInflater inflater;
    private String[] titles;
    private String[] numberImage;
    ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

    public GridAdapter(Context c, String[] titles, String[] numberImage){
        context = c;
        this.titles = titles;
        this.numberImage = numberImage;
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
            Log.d("BIT", String.valueOf(bitmap));
            Log.d("Type", bitmap.getClass().getName());

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){

            bitmapArray.add(bitmap);
            //Log.d("BITMAP", String.valueOf(bitmapArray.get(0)));
            ivResult.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_item, null);
        }


        ivResult = convertView.findViewById(R.id.imageViewGrid);
        TextView textView = convertView.findViewById(R.id.textViewGrid);
        LoadImage newImage = new LoadImage(ivResult);
        newImage.execute(numberImage[position]);

//        for(int i = 0; i<bitmapArray.size(); i++){
//
//            Log.d("BITMAP", String.valueOf(bitmapArray.get(i)));
//        }


        //ivResult.setImageBitmap(bitmapArray.get(0));
        textView.setText(titles[position]);

        return convertView;
    }
}
