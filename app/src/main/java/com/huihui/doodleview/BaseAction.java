package com.huihui.doodleview;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Created by gavin
 * Time 2017/8/18  14:21
 * Email:molu_clown@163.com
 * 动作基础类
 */

public abstract class BaseAction {


    public int color;

    public BaseAction() {

        color = Color.WHITE;
    }

    public BaseAction(int color) {
        this.color = color;
    }

    public abstract void draw(Canvas canvas);

    public abstract void move(float mx, float my);
}
