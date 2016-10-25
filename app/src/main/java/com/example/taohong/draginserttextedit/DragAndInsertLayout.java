package com.example.taohong.draginserttextedit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by taohong on 16/10/25.
 */
public class DragAndInsertLayout extends LinearLayout {
    int point1, point2, startX, startY;

    public DragAndInsertLayout(Context context) {
        this(context, null);
    }

    public DragAndInsertLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public DragAndInsertLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void init() {
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();

                int x = (int) event.getRawX();
                int y = (int) event.getRawY();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        point1 = v.getTop();

                        startX = (int) event.getX();
                        startY = y - v.getTop();

                        break;

                    case MotionEvent.ACTION_MOVE:
                        v.layout(x - startX, y - startY, x + v.getWidth()
                                - startX, y - startY + v.getHeight());

                        break;
                    case MotionEvent.ACTION_UP:
                        point2 = v.getTop();

                        DragAndInsertLayout.this.removeView(v);

                        //计算插入位置 位于哪两个相邻View之间
                        int dest = getLocation(v);

                        DragAndInsertLayout.this.addView(v, dest);

                        break;
                }
                return true;
            }

        };

        for (int i = 0; i < getChildCount(); i++) {
            View tv = getChildAt(i);
            if (tv != null) {

                tv.setOnTouchListener(touchListener);
            }
        }
    }

    public int getLocation(View v) {
        int top = v.getTop();
        int length = getChildCount();
        for (int i = 0; i < length; i++) {
            View item = getChildAt(i);
            if (i == 0 && top <= item.getTop()) {//小于第一项的top，插入最前面
                return 0;
            }
            if (i == length - 1 && top >= item.getTop()) {//大于最后一项的top，插入到最后
                return length;
            }
            if (i < length - 1) {
                View nextItem = getChildAt(i + 1);
                if (top > item.getTop() && top < nextItem.getTop())//view的top值在两项之间
                    return i + 1;
            }
        }
        return length;
    }
}
