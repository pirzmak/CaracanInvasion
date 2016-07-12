package com.example.ja.caracaninvasion;

import android.graphics.Rect;

/**
 * Klasa abstarkcyjna dla obiektow wykorzystanych w grze;
 * Podstawowe funkcje do pobierania miejsca,wielkosci
 * Created by pirzmowski on 31.03.16.
 */
public abstract class GameObject {
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int width;
    protected int heigth;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public Rect getRectangle() {
        return new Rect(x, y, x+width, y+heigth);
    }
}

