package com.usu.minesweeperstarter;

import android.content.Context;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

public class GameTitle extends AppCompatTextView {
    public GameTitle(String title, Context context) {
        super(context);
        setText(title);
        setTextSize(48);
        setGravity(Gravity.CENTER);
    }
}
