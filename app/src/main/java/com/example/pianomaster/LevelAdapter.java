package com.example.pianomaster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;

public class LevelAdapter extends BaseAdapter {
    private final Context mContext;
    private int btn_id = 1;
    private int nbNiveaux = 10;

    public LevelAdapter(Context context) {
        this.mContext = context;
    }

    public LevelAdapter(Context context, int nbNiveaux) {
        this.mContext = context;
        this.nbNiveaux = nbNiveaux;
    }

    @Override
    public int getCount() {
        return nbNiveaux;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Button btn;
        if (view == null) {
            btn = new Button(mContext);
            btn.setText(++btn_id +"");
            btn.setTextColor(Color.WHITE);
            btn.setTextSize(30);
            btn.setBackgroundResource(R.drawable.button_style);
        } else {
            btn = (Button) view;
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.retressicement);
                animation.setDuration(100);
                btn.startAnimation(animation);

                Intent intent = new Intent(mContext, CreerQuestionActivity.class);
                intent.putExtra("numNiveau", (i+1));
                mContext.startActivity(intent);
            }
        });
        return btn;
    }
}

