package com.deathadder.viper.clientapp;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class Tracker extends ActionBarActivity {

    Display display;
    int height, width;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawingView(this));
        display = getWindowManager().getDefaultDisplay();
        height = display.getHeight();
        width = display.getWidth();
    }

    @Override
    public void onBackPressed() {
        Connection.setConnected(false);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN &&
                event.getAction() != MotionEvent.ACTION_UP &&
                event.getAction() != MotionEvent.ACTION_MOVE) {
            return false;
        }
        // Log.d("AMIT", height+" "+width+" Motion detected X:" + event.getX() + " Y:" + event.getY());
        return true;
    }

    class DrawingView extends SurfaceView {

        private final SurfaceHolder surfaceHolder;
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public DrawingView(Context context) {
            super(context);
            surfaceHolder = getHolder();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction() != MotionEvent.ACTION_UP) {
                if (surfaceHolder.getSurface().isValid()) {
                    Canvas canvas = surfaceHolder.lockCanvas();
                    canvas.drawColor(Color.BLACK);

                    canvas.drawLine(0,height/2,width,height/2,paint);
                    canvas.drawLine(width/2,0,width/2,height,paint);

                    if(event.getY() < height/3){
                        paint.setColor(Color.BLUE);
                        canvas.drawCircle(event.getX(), event.getY(), 50, paint);
                        MainActivity.connection.moveUp();
                    }
                    else if(event.getY() > 2*height/3){
                        paint.setColor(Color.GREEN);
                        canvas.drawCircle(event.getX(), event.getY(), 50, paint);
                        MainActivity.connection.moveDown();
                    }
                    else if(event.getX() < width/3){
                        paint.setColor(Color.CYAN);
                        canvas.drawCircle(event.getX(), event.getY(), 50, paint);
                        MainActivity.connection.moveLeft();
                    }
                    else if(event.getX() > 2*width/3){
                        paint.setColor(Color.MAGENTA);
                        canvas.drawCircle(event.getX(), event.getY(), 50, paint);
                        MainActivity.connection.moveRight();
                    }
                    else
                        canvas.drawCircle(event.getX(), event.getY(), 50, paint);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    //Log.d("AMIT"," Motion detected X:" + event.getX() + " Y:" + event.getY());
                    return true;
                }

            }
            else{
                if (surfaceHolder.getSurface().isValid()) {
                    Canvas canvas = surfaceHolder.lockCanvas();
                    canvas.drawColor(Color.BLACK);

                    paint.setColor(Color.RED);
                    canvas.drawLine(0,height/2,width,height/2,paint);
                    canvas.drawLine(width/2,0,width/2,height,paint);

                    canvas.drawCircle( display.getWidth()/2, display.getHeight()/2, 50, paint);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    MainActivity.connection.stop();
                    //Log.d("AMIT"," Motiooooon detected X:" + display.getHeight()/2 + " Y:" + display.getWidth()/2);
                    return true;
                }
            }
            return false;
        }

    }
}
