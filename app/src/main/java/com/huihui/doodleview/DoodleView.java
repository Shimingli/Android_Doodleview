package com.huihui.doodleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gavin
 * Time 2017/8/18  15:27
 * Email:molu_clown@163.com
 */

public class DoodleView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder = null;

    private BaseAction curAction = null;

    private int currentColor = Color.BLACK;

    private int currentSize = 5;

    private Paint mPaint;

    private List<BaseAction> mBaseActions;

    private Bitmap mBitmap;

    private ActionType mActionType = ActionType.Path;


    public DoodleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        this.setFocusable(true);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(currentSize);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Canvas canvas = mSurfaceHolder.lockCanvas();

        canvas.drawColor(Color.WHITE);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
        mBaseActions = new ArrayList<>();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        int action = event.getAction();

        if (action == MotionEvent.ACTION_CANCEL)
            return false;

        float touchX = event.getX();

        float touchY = event.getY();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                reset();
                setCurAction(touchX, touchY);

                break;

            case MotionEvent.ACTION_MOVE:

                Canvas canvas = mSurfaceHolder.lockCanvas();

                canvas.drawColor(Color.WHITE);

                for (BaseAction baseAction : mBaseActions) {
                    baseAction.draw(canvas);
                }

                curAction.move(touchX, touchY);
                curAction.draw(canvas);

                mSurfaceHolder.unlockCanvasAndPost(canvas);


                break;

            case MotionEvent.ACTION_UP:
                mBaseActions.add(curAction);


                curAction = null;


                break;
        }


        return true;
    }

    private void setCurAction(float x, float y) {

        switch (mActionType) {

            case Point:

                curAction = new MyPoint(currentColor, x, y);
                return;

            case Path:
                curAction = new MyPath(x, y, currentColor, currentSize);

                break;
            case Line:
                curAction = new MyLine(x, y, currentColor, currentSize);

                break;
            case Rect:

                curAction = new MyRect(x, y, currentSize, currentColor);

                break;
            case Circle:
                curAction = new MyCircle(currentColor, x, y, currentSize);

                break;
            case FillEcRect:
                curAction = new MyFillRect(x, y, currentSize, currentColor);

                break;
            case FilledCircle:

                curAction = new MyFillCircle(x, y, currentSize, currentSize);

                break;
            default:
                break;
        }

    }

    /**
     * 设置画笔的颜色
     *
     * @param color 颜色
     */
    public void setColor(String color) {
        this.currentColor = Color.parseColor(color);
    }

    /**
     * 设置画笔的粗细
     *
     * @param size 画笔的粗细
     */
    public void setSize(int size) {
        this.currentSize = size;
    }

    /**
     * 设置画笔的形状
     *
     * @param type 画笔的形状
     */
    public void setType(ActionType type) {
        this.mActionType = type;
    }

    /****
     * 将当前画布转换成一个Bitmap
     * @return
     */
    public Bitmap getBitmap() {

        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(mBitmap);
        doDraw(canvas);

        return mBitmap;

    }

    public String saveBitmap(DoodleView doodleView) {

        String path = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/doodleview/" + System.currentTimeMillis() + ".png";
        if (!new File(path).exists()) {
            new File(path).getParentFile().mkdir();
        }

        savePicByPNG(doodleView.getBitmap(), path);

        return path;
    }

    /**
     * 将一个 Bitmap 保存在一个指定的路径中
     *
     * @param bitmap
     * @param filePath
     */
    public static void savePicByPNG(Bitmap bitmap, String filePath) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(filePath);
            if (null != fileOutputStream) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /****
     * 开始进行绘画
     * @param canvas
     */
    private void doDraw(Canvas canvas) {

        canvas.drawColor(Color.TRANSPARENT);
        for (BaseAction action : mBaseActions) {
            action.draw(canvas);
        }
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }

    /****
     *
     * @return 是否回退成功
     */
    public boolean back() {
        if (mBaseActions != null && mBaseActions.size() > 0) {

            mBaseActions.remove(mBaseActions.size() - 1);

            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);

            for (BaseAction action : mBaseActions) {
                action.draw(canvas);
            }

            mSurfaceHolder.unlockCanvasAndPost(canvas);

            return true;
        }


        return false;
    }

    /**
     * 重置签名
     */
    public void reset() {
        if (mBaseActions != null && mBaseActions.size() > 0) {
            mBaseActions.clear();
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            for (BaseAction action : mBaseActions) {
                action.draw(canvas);
            }
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    enum ActionType {
        Point, Path, Line, Rect, Circle, FillEcRect, FilledCircle
    }
}
