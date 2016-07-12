package com.example.ja.caracaninvasion;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by pirzmowski on 12.06.16.
 */
public class Help extends GameObject{

    private Bitmap spritesheet;
    private long startTime;
    private Random rand;
    private int type;

    public Help(Bitmap res, int w, int h, int numFrames) {
        x = 863;
        y = 80;
        dy = 0;
        dx = 10;
        type = 1;
        heigth = h;
        width = w;

        spritesheet = res;

    }

    public void update() {
        long elapsed = (System.nanoTime() - startTime)/1000000;
        rand = new Random();

        if(elapsed>100)
            startTime = System.nanoTime();

        x -= dx*2;
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(spritesheet,x,y,null);
    }

    public int getType() {return type;}
}

