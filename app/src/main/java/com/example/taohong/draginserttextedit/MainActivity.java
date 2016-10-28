package com.example.taohong.draginserttextedit;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private String mNames[] = {
            "welcome", "android", "TextView",
            "apple", "jamy", "kobe bryant",
            "jordan", "layout", "viewgroup",
            "margin", "padding", "text",
            "name", "type", "search", "logcat"
    };
    private ArrayList<View> viewArray = new ArrayList<View>();
    private FlowLayout mFlowLayout;
    int point1, point2, startX, startY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        addChildViews();
    }

    private void addChildViews() {
        mFlowLayout.removeAllViews();
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for (int i = 0; i < viewArray.size(); i++) {
            mFlowLayout.addView(viewArray.get(i), lp);
        }
    }

    private void initViews() {
        mFlowLayout = (FlowLayout) findViewById(R.id.flowlayout);

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

                        viewArray.remove(v);

                        //计算插入位置 位于哪两个相邻View之间
                        int dest = getLocation(v);

                        viewArray.add(dest, v);
                        addChildViews();

                        break;
                }
                return true;
            }

        };


        viewArray.clear();
        for (int i = 0; i < mNames.length; i++) {
            TextView view = (TextView) getLayoutInflater().inflate(R.layout.word_item_layout, null);
            view.setText(mNames[i]);
            view.setOnTouchListener(touchListener);
            viewArray.add(view);
        }
    }

    public int getLocation(View v) {
        int top = v.getTop();
        int left = v.getLeft();
        for (int i = 0; i < viewArray.size(); i++) {
            if (top <= viewArray.get(i).getTop() && left <= viewArray.get(i).getLeft()) {
                return i;
            }
        }
        return viewArray.size();
    }
}