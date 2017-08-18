package com.huihui.doodleview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by gavin
 * Time 2017/8/18  14:30
 * Email:molu_clown@163.com
 * 自由曲线
 */

public class MyPath extends BaseAction{

    private Path path;

    private int size;


    public MyPath() {
        path=new Path();

        size=1;
    }

    public MyPath(float x,float y,int color,int size) {
        super(color);
        this.path=new Path();

        this.size=size;

        path.moveTo(x,y);

        path.lineTo(x,y);
    }


    @Override
    public void draw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //该方法是设置防抖动。
        paint.setDither(true);
        paint.setColor(color);
        paint.setStrokeWidth(size);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPath(path, paint);
    }

    @Override
    public void move(float mx, float my) {

        path.lineTo(mx,my);
    }
}
