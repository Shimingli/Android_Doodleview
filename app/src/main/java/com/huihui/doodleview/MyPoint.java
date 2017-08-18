package com.huihui.doodleview;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by gavin
 * Time 2017/8/18  14:26
 * Email:molu_clown@163.com
 * 绘制点
 */

public class MyPoint extends BaseAction {

    private float x;

    private  float y;

    public MyPoint(int color, float x, float y) {
        super(color);
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Canvas canvas) {

        Paint paint =new Paint();

        paint.setColor(color);

        canvas.drawPoint(x,y,paint);
    }

    @Override
    public void move(float mx, float my) {

    }
}
