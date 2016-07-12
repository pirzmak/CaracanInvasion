package com.example.ja.caracaninvasion;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * klasa sterujaca graczem
 * Created by pirzmowski on 31.03.16.
 */
public class Player extends GameObject {

    private Bitmap spritesheet;
    private int score;
    private boolean up;
    private int adin;
    private boolean playing;
    private long startTime;
    private int boost;
    private int boostTime;

    public Player(Bitmap res, int w, int h) {
        x = 100;
        dy = 0;
        dx = 0;
        score = 0;
        heigth = h;
        width = w;
        adin = 2;
        boost=0;
        boostTime = 0;
        y = GamePanel.HEiGHT - heigth - 38;

        spritesheet = res;

        startTime = System.nanoTime();
    }

    public void setUp (boolean u) {
        up=u;
    }

    public void update() {
        long elapsed = (System.nanoTime() - startTime)/1000000;

        if(elapsed>100)
        {
            score++;
            if(boostTime>0)boostTime--;
            if(boostTime==0)boost=0;
            startTime = System.nanoTime();
        }

        if(up && adin > 0)
        {
            dy = -16;
            adin--;
            up=false;
        }

        y += dy*2;

        if(y >= GamePanel.HEiGHT - heigth - 38)
        {
            y = GamePanel.HEiGHT - heigth - 38;
            adin=2;
        }
        dy+=2;


    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(spritesheet,x,y,null);
    }

    public int getScore() {
        return score;
    }

    public int getBoost() {
        return boost;
    }

    public void setBoost(int i) {
        boost = i;
        setBoostTime();
    }

    public int getBoostTime() {
        return boostTime;
    }

    public void setBoostTime() {
        boostTime = 30;
    }

    public boolean getPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void resetScore() {
        score = 0;
    }

}
