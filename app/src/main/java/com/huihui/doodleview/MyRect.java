package com.huihui.doodleview;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by gavin
 * Time 2017/8/18  15:01
 * Email:molu_clown@163.com
 * 绘制方框
 */

public class MyRect  extends BaseAction{

    private float startX;
    private float startY;
    private float stopX;
    private float stopY;
    private int size;

    MyRect() {
        this.startX = 0;
        this.startY = 0;
        this.stopX = 0;
        this.stopY = 0;
    }

    MyRect(float x, float y, int size, int color) {
        super(color);
        this.startX = x;
        this.startY = y;
        this.stopX = x;
        this.stopY = y;
        this.size = size;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(size);
        canvas.drawRect(startX, startY, stopX, stopY, paint);
    }

    @Override
    public void move(float mx, float my) {
        stopX = mx;
        stopY = my;
    }
}
