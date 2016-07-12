package com.example.ja.caracaninvasion;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Klasa przeciwnikow
 * Created by pirzmowski on 04.06.16.
 */
public class Caracan extends GameObject {

    private Bitmap spritesheet;
    private long startTime;
    private Random rand;
    private int level;

    public Caracan(Bitmap res, int w, int h, int lvl) {
        x = 863;
        dy = 0;
        dx = 8;
        heigth = h;
        width = w;
        level = lvl;
        y = GamePanel.HEiGHT - heigth - 38;

        spritesheet = res;

        startTime = System.nanoTime();
    }

    public void update() {
        long elapsed = (System.nanoTime() - startTime)/1000000;
        rand = new Random();

        if(elapsed>100)
            startTime = System.nanoTime();

        if(level>100 && rand.nextBoolean() && y >= GamePanel.HEiGHT - heigth - 38)
            x -= dx*3;
        else
            x -= dx*2;
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(spritesheet,x,y,null);
    }
}

