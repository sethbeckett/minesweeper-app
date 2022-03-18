package com.usu.minesweeperstarter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

public class ModeButton extends AppCompatButton {
    public ModeButton(String mode, Context context) {
        super(context);
        setText(mode);
        setTextSize(36);
        setPadding(10,10,10,10);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GameActivity.class);
                intent.putExtra("Mode", mode);
                context.startActivity(intent);
            }
        });
    }
}
