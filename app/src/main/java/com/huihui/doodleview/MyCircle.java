package com.huihui.doodleview;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by gavin
 * Time 2017/8/18  15:06
 * Email:molu_clown@163.com
 * 圆框
 */

public class MyCircle extends BaseAction {

    private float startX;

    private float startY;

    private float stopX;

    private  float stopY;

    private float radius;

    private int size;

    public MyCircle( ) {
        this.startX = 0;
        this.startY = 0;
        this.stopX = 0;
        this.stopY = 0;
        this.radius = 0;

    }

    public MyCircle(int color, float startX, float startY,int size) {
        super(color);
        this.startX = startX;
        this.startY = startY;
        this.stopX = startX;
        this.stopY = startY;
        this.radius = 0;
        this.size = size;
    }

    @Override
    public void draw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(size);
        canvas.drawCircle((startX + stopX) / 2, (startY + stopY) / 2, radius, paint);
    }

    @Override
    public void move(float mx, float my) {

        stopX=mx;

        stopY=my;

        radius=(float)((Math.sqrt((mx-startX)*(mx-startX)+(my-startY)*(my-startY)))/2);
    }
}
