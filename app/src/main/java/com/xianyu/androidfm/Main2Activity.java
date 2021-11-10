package com.xianyu.androidfm;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class Main2Activity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("chenxianyu","2222");
            }
        });
        findViewById(R.id.btn).setOnClickListener(view -> Log.i("chenxianyu","2222"));
    }

    @Override
    public void onClick(View view) {
        Log.i("chenxianyu","gggg");
    }
}
