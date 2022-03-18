package com.usu.minesweeperstarter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;

public class GameView extends View {
    Game game;
    Paint paint = new Paint();
    String gameMode;
    public GameView(Context context, String gameMode) {
        super(context);
        this.gameMode = gameMode;

        GestureDetectorCompat detectorCompat = new GestureDetectorCompat(
                context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        game.handleTap(e);
                        invalidate();
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        game.handleLongPress(e);
                        invalidate();
                    }
                });

        setOnTouchListener((view, motionEvent) -> {
            return detectorCompat.onTouchEvent(motionEvent);
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        game = new Game(gameMode, getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        game.draw(canvas, paint);
    }
}
