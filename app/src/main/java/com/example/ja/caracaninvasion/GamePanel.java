package com.example.ja.caracaninvasion;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Glowny panel z gra. Tworzenie gry uaktualnianie
 * ekranu, punktacji, stanu gry.
 * Created by pirzmowski on 23.03.161.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final int HEiGHT = 480;
    public static final int WIDTH = 863;
    public static final int GameSpeed = -5;
    private MainThread thread;
    private Background bg;
    private Player player;
    private Random rand = new Random();
    private long carTime;
    private ArrayList<Caracan> caracans;
    private ArrayList<Help> helps;
    private int best=0;
    private boolean start=true;

    public GamePanel(Context context){
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder , int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public void  surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.szejm));
        player = new Player(BitmapFactory.decodeResource(getResources(),R.drawable.a),75,71);
        caracans = new ArrayList<>();
        helps = new ArrayList<>();
        carTime=  System.nanoTime();

        thread.setRunning(true);
        thread.start();
    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(!player.getPlaying())
            {
                reset();
                start=false;
                player.setPlaying(true);
            }
            else
            {
                player.setUp(true);
            }
            return true;
        }

        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            player.setUp(false);
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update(){

        if(player.getPlaying())
        {
            bg.update();
            player.update();
            rand = new Random();

            long carElapsed = (System.nanoTime()-carTime)/1000000;

            if(carElapsed >(1000 - (player.getScore()%100*5)) && rand.nextBoolean()){

                caracans.add(new Caracan(BitmapFactory.decodeResource(getResources(),R.drawable.car),50, 90,player.getScore()));

                carTime = System.nanoTime();
            }

            if(player.getScore()>30 && rand.nextInt(100)==0 && helps.size()==0){

                helps.add(new Help(BitmapFactory.decodeResource(getResources(),R.drawable.car),50,50,1));

                carTime = System.nanoTime();
            }

            for(int i = 0; i<caracans.size();i++)
            {
                caracans.get(i).update();

                if(caracans.get(i).getX()<-50)
                {
                    caracans.remove(i);
                    break;
                }

                if(collision(caracans.get(i),player))
                {
                    if(player.getBoost()==0)
                    {
                        if(player.getScore()>best)best=player.getScore();
                        player.setPlaying(false);
                        break;
                    }
                    else if(player.getBoost()==1)
                    {
                        caracans.remove(i);
                    }
                }

            }


            for(int i = 0; i<helps.size();i++)
            {
                helps.get(i).update();

                if(helps.get(i).getX()<-50)
                {
                    helps.remove(i);
                    break;
                }

                if(collision(helps.get(i),player))
                {
                    player.setBoost(helps.get(i).getType());
                    helps.remove(i);
                }
            }
        }
    }

    public void drawText(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        canvas.drawText("Score : " + player.getScore(), 10 , HEiGHT - 400 , paint);
        canvas.drawText("Best : " + best, WIDTH - 200 , HEiGHT - 400 , paint);
        if(player.getBoost()!=0)
        {
            paint.setColor(Color.YELLOW);
            paint.setTextSize(60);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            canvas.drawText("Super Stonoga : " + player.getBoostTime(),WIDTH/2 - 80 , HEiGHT/2 , paint);
        }
        if(start)
        {
            paint.setColor(Color.RED);
            paint.setTextSize(60);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            canvas.drawText("CARACAN",WIDTH/2 , HEiGHT/2 , paint);
            canvas.drawText("   INVASION", WIDTH/2 , HEiGHT/2 + 70, paint);
        }
    }

    public boolean collision(GameObject a, GameObject b){

        if(Rect.intersects(a.getRectangle(),b.getRectangle()))return true;
        return false;

    }



    @Override
    public void draw(Canvas canvas){
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEiGHT*1.f);

        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX,scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);
            for(Caracan m: caracans)
            {
                m.draw(canvas);
            }
            for(Help m: helps)
            {
                m.draw(canvas);
            }
            canvas.restoreToCount(savedState);
            drawText(canvas);
        }

    }

    public void reset()
    {
        player.resetScore();
        caracans.clear();
        helps.clear();
    }

}
