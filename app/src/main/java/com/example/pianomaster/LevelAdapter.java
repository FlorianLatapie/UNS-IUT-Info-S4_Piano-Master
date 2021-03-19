package com.example.pianomaster;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

public class LevelAdapter extends BaseAdapter
{
    private Context mContext;
    private int btn_id = 0;
    private int total_btns = 6;

    public LevelAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return total_btns;
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
    public View getView(final int i, View view, ViewGroup viewGroup)
    {
        Button btn;
        if (view == null) {
            btn = new Button(mContext);
            btn.setText(""+(++btn_id));
            btn.setTextColor(Color.WHITE);
            btn.setTextSize(30);
            btn.setBackgroundResource(R.drawable.button_style);
        } else {
            btn = (Button) view;
        }

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(v.getContext(), "Button #" + (i + 1), Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(mContext, CreerQuestionActivity.class);
                mContext.startActivity(intent);
            }
        });

        return btn;
    }

}
