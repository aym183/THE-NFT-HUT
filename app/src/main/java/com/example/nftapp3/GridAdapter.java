package com.example.nftapp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private String[] titles;
    private int[] numberImage;

    public GridAdapter(Context c, String[] titles, int[] numberImage){
        context = c;
        this.titles = titles;
        this.numberImage = numberImage;
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

        ImageView imageView = convertView.findViewById(R.id.imageViewGrid);
        TextView textView = convertView.findViewById(R.id.textViewGrid);
        imageView.setImageResource(numberImage[position]);
        textView.setText(titles[position]);
        return convertView;
    }
}
