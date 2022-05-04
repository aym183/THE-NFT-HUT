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

/**
 * The GridAdapter class is used to display images with text in the Grid View for home page
 */
public class GridAdapter extends BaseAdapter {

    ImageView ivResult;
    private Context context;
    private LayoutInflater inflater;
    private String[] titles;
    private String[] numberImage;
    ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

    /**
     * This constructor stores all the data required for the methods.
     * @param c This is the context of the system
     * @param titles The titles that are overwitten in the Grid View
     * @param numberImage The image views that are overwritten in the Grid View
     */
    public GridAdapter(Context c, String[] titles, String[] numberImage){
        context = c;
        this.titles = titles;
        this.numberImage = numberImage;
    }

    /**
     * This method is used to Load the images in the respective views
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
            bitmapArray.add(bitmap);
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

    /**
     * This method handles the operations of adding values to textViews and Imageviews
     * @param position This is the call made to the API
     * @param convertView The exception that has occurred on failure
     * @param parent The exception that has occurred on failure
     *
     */
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
        textView.setText(titles[position]);

        return convertView;
    }
}
