package com.xianyu.androidfm

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.xianyu.common.ui.pull.NewSwipeToLoadLayout
import com.xianyu.common.ui.pull.OnNewLoadMoreListener
import com.xianyu.common.ui.pull.OnNewRefreshListener
import java.util.*


class MainActivityPull: AppCompatActivity(), OnNewRefreshListener, OnNewLoadMoreListener {
    var swipeToLoadLayout: NewSwipeToLoadLayout? = null

    var mAdapter: ArrayAdapter<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_pull)
        swipeToLoadLayout = findViewById<View>(R.id.swipeToLoadLayout) as NewSwipeToLoadLayout

        val listView: ListView = findViewById<View>(R.id.swipe_target) as ListView

        swipeToLoadLayout?.setOnNewRefreshListener(this)

        swipeToLoadLayout?.setOnNewLoadMoreListener(this)

        mAdapter = ArrayAdapter(this@MainActivityPull, android.R.layout.simple_expandable_list_item_1)

        listView.adapter = mAdapter

        autoRefresh()
    }

    private fun autoRefresh() {
        swipeToLoadLayout?.post { swipeToLoadLayout?.isRefreshing = true }
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

    override fun onRefresh() {
        swipeToLoadLayout?.postDelayed({
            swipeToLoadLayout?.isRefreshing = false
            mAdapter?.add("REFRESH:\n ${Date()}")
        }, 2000)
    }

    override fun onLoadMore() {
        swipeToLoadLayout?.postDelayed({
            swipeToLoadLayout?.isLoadingMore = false
            mAdapter?.add(
                "LOAD MORE:${Date()}"
            )
        }, 2000)
    }
}