package com.xianyu.common.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xianyu.androidlibrary.log.LoggerUtil;

public class DemoFrameLayout extends FrameLayout {
    public DemoFrameLayout(@NonNull Context context) {
        super(context);
        init(null);
    }

    public DemoFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DemoFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public DemoFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        Button button = new Button(getContext());
        button.setText("1234567");
        addView(button,1);
        TextView textView = new TextView(getContext());
        textView.setText("bbbbbb");
        addView(textView,2);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        LoggerUtil.i("cxy","addView1 : " + child.getClass().getSimpleName());
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        LoggerUtil.i("cxy","addView2 : " + child.getClass().getSimpleName() + " , index: " + index);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        LoggerUtil.i("cxy","addView3 : " + child.getClass().getSimpleName());

    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        LoggerUtil.i("cxy","addView4 : " + child.getClass().getSimpleName());

    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        LoggerUtil.i("cxy","addView5 : " + child.getClass().getSimpleName() + " , index: " + index);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
