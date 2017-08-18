package com.huihui.doodleview;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by gavin
 * Time 2017/8/18  14:45
 * Email:molu_clown@163.com
 */

public class MyLine extends BaseAction {

    private float startX;
    private float startY;

    private float stopX;
    private float stopY;

    private int size;

    public MyLine() {
        startX=0;
        startY=0;
        stopX=0;
        stopY=0;
    }

    public MyLine(float x,float y,int color,int size) {
        super(color);
        this.startX=x;
        this.startY=y;
        this.stopX=x;
        this.stopY=y;
        this.size=size;
    }

    @Override
    public void draw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(size);

        canvas.drawLine(startX,startY,stopX,stopY,paint);
    }

    @Override
    public void move(float mx, float my) {

        this.stopY=my;
        this.stopX=mx;
    }
}
