package com.usu.minesweeperstarter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {

    // FEEL FREE TO CHANGE THESE!
    private int[] colors = {
            Color.BLUE,
            Color.GREEN,
            Color.RED,
            Color.rgb(0,0,100),
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA,
            Color.BLACK
    };

    public enum Type {
        MINE,
        NUMBER,
        EMPTY
    }

    private boolean isMarked = false;
    private boolean isSelected = false;
    private Type type;
    private double xPos;
    private double yPos;
    private double width;
    private double height;
    private int numNeighbors = 0;

    public Cell(double xPos, double yPos, double width, double height, Type type) {
        this.type = type;
        this.yPos = yPos;
        this.xPos = xPos;
        this.width = width;
        this.height = height;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void toggleMark() {
        isMarked = !isMarked;
    }

    public void select() {
        isSelected = true;
        isMarked = false;
    }

    public void setNumNeighbors(int numNeighbors) {
        this.numNeighbors = numNeighbors;
    }

    public void draw(Canvas canvas, Paint paint) {
        // TODO: Draw the cell at its position depending on the state it is in
        paint.setTextSize((float) height);
        paint.setTextAlign(Paint.Align.CENTER);

        if (isSelected) {
            if (type == Type.NUMBER) {
                paint.setColor(colors[numNeighbors-1]);
                canvas.drawText(Integer.toString(numNeighbors),
                        (float) (xPos + width/2),
                        (float) (yPos + 0.9*height),
                        paint);
            }
            if (type == Type.MINE) {
                paint.setColor(Color.RED);
                canvas.drawText("X",
                        (float) (xPos + width/2),
                        (float) (yPos + 0.8*height),
                        paint);

            }
        }
        else {
            if (isMarked) {
                paint.setColor(Color.RED);
            }
            else paint.setColor(Color.GRAY);

            canvas.drawRect((float) xPos,
                    (float) yPos,
                    (float) (xPos + width),
                    (float) (yPos + height),
                    paint);
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        canvas.drawRect((float) xPos,
                (float) yPos,
                (float) (xPos + width),
                (float) (yPos + height),
                paint);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
