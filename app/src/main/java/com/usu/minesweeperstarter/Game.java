package com.usu.minesweeperstarter;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game {
    private enum State {
        PLAY,
        WIN,
        LOSE,
    }

    Cell[][] cells;
    int mineCount;
    int rows = 30;
    int cols = 16;
    double cellWidth;
    double cellHeight;
    int screenWidth;
    int screenHeight;
    State state = State.PLAY;

    public Game(String gameMode, int screenWidth, int screenHeight) {
        cells = new Cell[rows][cols];
        if (gameMode.equals("expert")) {
            mineCount = 100;
        } else if (gameMode.equals("intermediate")) {
            mineCount = 50;
        } else {
            mineCount = 10;
        }
        cellHeight = (double)screenHeight / rows;
        cellWidth = (double)screenWidth / cols;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        initCells();
    }

    private void initCells() {

        ArrayList<Boolean> minesList = new ArrayList<Boolean>();
        for (int i = 0; i < rows * cols; i++) {
            if (i < mineCount) {
                minesList.add(true);
            }
            else minesList.add(false);
        }
        Collections.shuffle(minesList);

        for(int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(j*cellWidth, i*cellHeight, cellWidth, cellHeight, Cell.Type.EMPTY );
                if (minesList.remove(0)) {
                    cells[i][j].setType(Cell.Type.MINE);
                };
            }
        }

        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (cells[i][j].getType() == Cell.Type.MINE) continue;
                int neighbors = countNeighbors(i,j);
                if (neighbors > 0) {
                    cells[i][j].setType(Cell.Type.NUMBER);
                    cells[i][j].setNumNeighbors(countNeighbors(i, j));
                }
            }
        }
    }


    private int countNeighbors(int row, int col) {
        //check top row
        int total = 0;
        if (row > 0){
            if (col > 0) {
                if (cells[row - 1][col - 1].getType() == Cell.Type.MINE) total++; //upleft
            }
            if (cells[row - 1][col].getType() == Cell.Type.MINE) total++; //upmid
            if (col < cols - 1) {
                if (cells[row - 1][col + 1].getType() == Cell.Type.MINE) total++; //upright
            }
        }
        //check middle row
        if (col > 0) {
            if (cells[row][col-1].getType() == Cell.Type.MINE) total++; //midleft
        }
        if (col < cols - 1) {
            if (cells[row][col+1].getType() == Cell.Type.MINE) total++; //midright
        }
        //check bottom row
        if (row < rows - 1) {
            if (col > 0) {
                if (cells[row+1][col-1].getType() == Cell.Type.MINE) total++; //downleft
            }
            if (cells[row+1][col].getType() == Cell.Type.MINE) total++; //downmid
            if (col < cols - 1) {
                if (cells[row+1][col+1].getType() == Cell.Type.MINE) total++; //downright
            }
        }


        return total;
    }

    private void revealMines() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].getType() == Cell.Type.MINE) {
                    cells[i][j].select();
                }
            }
        }
    }

    private void explodeBlankCells(int row, int col) {
        if (row < 0 || row > cells.length - 1) return;
        if (col < 0 || col > cells[row].length - 1) return;
        if (cells[row][col].isSelected()) return;
        cells[row][col].select();
        if (cells[row][col].getType() == Cell.Type.MINE) {
            revealMines();
            state = State.LOSE;
        }
        if (cells[row][col].getType() == Cell.Type.NUMBER) return;

        explodeBlankCells(row-1, col-1);
        explodeBlankCells(row-1, col);
        explodeBlankCells(row-1,col+1);
        explodeBlankCells(row,col-1);
        explodeBlankCells(row,col);
        explodeBlankCells(row,col+1);
        explodeBlankCells(row+1,col-1);
        explodeBlankCells(row+1,col);
        explodeBlankCells(row+1,col+1);
    }

    private void checkWin() {
        for(int i = 0; i < cells.length; i++) { //check through cells and check if won
            for (int j = 0; j < cells[i].length; j++) {
                if ((cells[i][j].getType().toString()).equals("MINE") && !cells[i][j].isMarked()) return;
                if (!(cells[i][j].getType().toString()).equals("MINE")&& !cells[i][j].isSelected()) return;
            }
        }
        state = State.WIN;
    }

    public void handleTap(MotionEvent e) {
        int row = (int) (e.getY() / cellHeight);
        int col = (int) (e.getX() / cellWidth);

        if (cells[row][col].isSelected()) {
            return;
        }
        else explodeBlankCells(row,col);
        checkWin();
    }

    public void handleLongPress(MotionEvent e) {

        int row = (int) (e.getY() / cellHeight);
        int col = (int) (e.getX() / cellWidth);
        cells[row][col].toggleMark();
        checkWin();
        }


    public void draw(Canvas canvas, Paint paint) {
        for(int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j].draw(canvas, paint);
                paint.reset();
            }
        }

        if (state == State.WIN) {
            // TODO draw a win screen here
            paint.setTextSize(256);
            paint.setColor(Color.rgb(255,49,150));
            canvas.drawText("You Win!", screenWidth/2, screenHeight/2, paint);
        }
    }
}
