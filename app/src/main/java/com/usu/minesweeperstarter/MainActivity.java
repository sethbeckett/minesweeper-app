package com.usu.minesweeperstarter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mainLayout.setLayoutParams(params);

        GameTitle gameTitle = new GameTitle("Mine Sweeper", this);
        gameTitle.setLayoutParams(params);
        mainLayout.addView(gameTitle);

        ModeButton easyButton = new ModeButton("easy", this);

        ModeButton intermediateButton = new ModeButton("intermediate", this);

        ModeButton expertButton = new ModeButton("expert", this);

        mainLayout.addView(easyButton);
        mainLayout.addView(intermediateButton);
        mainLayout.addView(expertButton);

        setContentView(mainLayout);
    }
}