package com.xianyu.androidfm

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.xianyu.common.log.LoggerUtil
import com.xianyu.route.homemodule.HelloRepository
import com.xianyu.route.koin.injectOrNull
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RelativeLayout>(R.id.parent).setOnTouchListener(object :View.OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                LoggerUtil.i("cxy","parent is touch")
                return false
            }

        })

        findViewById<TextView>(R.id.text).setOnTouchListener(object :View.OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                LoggerUtil.i("cxy","text is touch")
                return false
            }

        })

        val helloRepository: HelloRepository? by injectOrNull()
        findViewById<TextView>(R.id.text).text = helloRepository?.giveHello()
        val json = "{\"name\": \"BeJson\",\"age\": 88}"
        val json2 = "{\"age\": 88}"
        val bean = JSON.parseObject(json2,Bean1::class.java)
        LoggerUtil.d("cxy","name : ${bean.name},age:${bean.age}")
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}