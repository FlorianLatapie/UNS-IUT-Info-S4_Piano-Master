package com.example.pianomaster;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class LevelAdapter extends BaseAdapter {
    private List<Button> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public LevelAdapter(Context aContext,  List<Button> list) {
        this.context = aContext;
        this.list = list;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;

        if(convertView==null){
            layoutItem = (LinearLayout) layoutInflater.inflate(R.layout.activity_level_grid, parent, false);
        }else{
            layoutItem = (LinearLayout) convertView;
        }

        return layoutItem;
    }
}
