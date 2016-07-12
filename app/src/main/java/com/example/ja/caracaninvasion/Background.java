package com.example.ja.caracaninvasion;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Zaladowanie tla i jego przesuwanie
 * Created by pirzmowski on 28.03.16.
 */
public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res){

        image=res;
        dx=GamePanel.GameSpeed;
    }

    public void update(){

        x+=dx;

        if(x<-GamePanel.WIDTH)
            x = 0;
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(image,x,y,null);

        if(x<0)
            canvas.drawBitmap(image,x+GamePanel.WIDTH,y,null);
    }

}

