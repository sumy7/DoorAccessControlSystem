package com.sumy.dooraccesscontrolsystem.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author <b>caiwei</b>
 * @author sumy
 * @version
 */
public class LockScreenView extends View {

    private int endX;
    private int endY;
    // private int moveX;
    // private int moveY;
    private int startX;
    private int startY;
    private Paint linePaint;
    private Paint circlePaint;
    private int viewWidth; // 鎺т欢鐨勫
    private int viewHeight; // 鎺т欢鐨勯珮
    private int radius;

    private Context context;
    private PointF[][] centerCxCy; // 鍦嗗績鐭╅樀
    private int[][] data; // 瀵嗙爜鏁版嵁鐭╅樀
    private boolean[][] selected; // 宸查�鐭╅樀 閫変腑涔嬪悗涓嶈兘閲嶉�
    private List<PointF> selPointList; // 閫変腑鐨勫渾涓偣闆嗗悎

    private boolean isPressedDown = false;

    private String lockPin = ""; // 缁撴灉閿佸睆瀵嗙爜瀛楃涓�

    private OnDrawLockPinFinishedListener listener = null;

    public LockScreenView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public LockScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public LockScreenView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        linePaint = new Paint();
        // linePaint.setColor(Color.rgb(255, 110, 2));
        // linePaint.setStrokeWidth(15); //鐢荤瑪瀹藉害
        linePaint.setStyle(Style.FILL); // 鐢荤瑪鏍峰紡
        linePaint.setAntiAlias(true);

        circlePaint = new Paint();
        // circlePaint.setColor(Color.rgb(155, 160, 170));
        circlePaint.setStrokeWidth(4);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Style.STROKE); // 鐢荤瑪鏍峰紡绌哄績

        centerCxCy = new PointF[3][3];
        data = new int[3][3];
        selected = new boolean[3][3];
        selPointList = new ArrayList<PointF>();
        initData();
    }

    // 娓呴櫎閫変腑璁板綍
    private void clearSelected() {
        for (int i = 0; i < selected.length; i++) {
            for (int j = 0; j < selected.length; j++) {
                selected[i][j] = false;
            }
        }
    }

    private void initData() {
        /**
         * 1 2 3 4 5 6 7 8 9
         */
        int num = 1;
        for (int i = 0; i < data[0].length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[j][i] = num;
                num++;
            }
        }
    }

    // 鍒ゆ柇鏄惁鍦ㄦ煇涓渾鍐�
    private boolean isInCircle(PointF p, int x, int y) {
        int distance = (int) Math.sqrt((p.x - x) * (p.x - x) + (p.y - y)
                * (p.y - y));
        if (distance <= radius) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        linePaint.setStrokeWidth(2 * radius / 3 - 2); // 鐢荤瑪瀹藉害

        // 鎵嬪娍寮�鍓嶇殑鐢诲渾
        for (int i = 0; i < selected[0].length; i++) {
            for (int j = 0; j < selected[0].length; j++) {
                PointF center = centerCxCy[i][j];
                if (selected[i][j]) { // 鏄惁閫変腑
                    circlePaint.setColor(Color.rgb(255, 110, 2)); // 婊戝姩鍒板渾鍐呮椂璁剧疆榛勮壊鐢荤瑪
                    linePaint.setColor(Color.rgb(255, 110, 2));
                    canvas.drawCircle(center.x, center.y, radius / 3, linePaint); // 閫変腑鏃剁殑瀹炲績鍐呭渾
                    linePaint.setColor(Color.argb(96, 255, 110, 2));
                    canvas.drawCircle(center.x, center.y, radius, linePaint); // 閫変腑鏃剁殑澶栧渾鍗婇�鏄庡～鍏�
                } else {
                    circlePaint.setColor(Color.rgb(155, 160, 170)); // 鍚﹀垯璁剧疆鐏扮櫧鑹茬敾绗�
                }
                canvas.drawCircle(center.x, center.y, radius, circlePaint); // 鐢诲鍦�
            }
        }

        linePaint.setColor(Color.argb(96, 255, 110, 2));
        // 鐢荤嚎鏉¤矾寰�
        if (isPressedDown) {
            for (int i = 0; i < selPointList.size() - 1; i++) { // 鐢诲凡閫変腑鍦嗕箣闂寸殑璺緞
                PointF preCenter = selPointList.get(i); // 鍓嶄竴涓渾涓偣
                PointF curCenter = selPointList.get(i + 1); // 鐜板湪鍦嗕腑鐐�
                canvas.drawLine(preCenter.x, preCenter.y, curCenter.x,
                        curCenter.y, linePaint);
            }

            if (selPointList.size() > 0) {
                PointF center = selPointList.get(selPointList.size() - 1); // 鏈�悗涓�釜閫変腑鍦嗕腑鐐�
                canvas.drawLine(center.x, center.y, endX, endY, linePaint);
            }

        }

        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        if (changed) {
            viewWidth = getWidth();
            viewHeight = getHeight();
            setRadius();

            Log.i("info", "viewWidth=" + viewWidth + "viewHeight=" + viewHeight);
        }

        super.onLayout(changed, left, top, right, bottom);
    }

    // 璁剧疆鍦嗙殑鍗婂緞鍜屽渾蹇�
    private void setRadius() {
        /**
         * | | | | | w | w | w | h |-----------------| | | | | | | | | h
         * |-----------------| | | | | h | | | |
         */
        int w = viewWidth / 3;
        int h = viewHeight / 3;
        radius = w / 4;
        for (int i = 0; i < centerCxCy[0].length; i++) {
            for (int j = 0; j < centerCxCy[0].length; j++) {
                PointF p = new PointF();
                p.x = (i * w) + w / 2;
                p.y = (j * h) + h / 2;
                centerCxCy[i][j] = p;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 寰楀埌瑙︽懜鐐圭殑鍧愭爣
        int pin = 0;
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            lockPin = "";
            selPointList.clear();

            isPressedDown = true;
            startX = (int) event.getX();
            startY = (int) event.getY();
            // Log.i("info", "pressedX="+pressedX+";pressedY="+pressedY);
            pin = getLockPinData(startX, startY);
            if (pin > 0) {
                lockPin += pin;
                invalidate();
            }
            break;

        case MotionEvent.ACTION_MOVE:
            endX = (int) event.getX();
            endY = (int) event.getY();
            // Log.i("info", "moveX="+moveX+";moveY="+moveY);
            pin = getLockPinData(endX, endY);
            if (pin > 0) {
                lockPin += pin;
            }
            invalidate();
            break;

        case MotionEvent.ACTION_UP:
            endX = (int) event.getX();
            endY = (int) event.getY();
            isPressedDown = false;
            // Log.i("info", "upX="+upX+";upY="+upY);
            // Log.i("info", "lockPin = " + lockPin);
            // Toast.makeText(context, "lockPin = " + lockPin,
            // Toast.LENGTH_SHORT)
            // .show();
            // clearSelected();
            if (listener != null) {
                listener.onFinish(lockPin);
            }
            invalidate();
            break;
        case MotionEvent.ACTION_CANCEL:
            break;
        }
        return true;
    }

    private int getLockPinData(int x, int y) {
        for (int i = 0; i < data[0].length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                PointF center = centerCxCy[i][j];
                if (isInCircle(center, x, y)) { // 鍒ゆ柇鏄惁鍦ㄥ渾鍐�
                    if (!selected[i][j]) {
                        selected[i][j] = true;
                        selPointList.add(center);
                        return data[i][j];
                    }
                }
            }
        }
        return 0;
    }

    public String getLockPin() {
        return lockPin;
    }

    public void reDrawLockPin() {
        clearSelected();
    }

    /**
     * 设置手势结束监听器
     * 
     * @param listener
     *            监听器
     */
    public void setOnDrawLockPinFinishedListener(
            OnDrawLockPinFinishedListener listener) {
        this.listener = listener;
    }

    public interface OnDrawLockPinFinishedListener {
        void onFinish(String lockPin);
    }

}
