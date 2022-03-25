package com.xianyu.androidlibrary.pull

import android.content.Context
import android.util.AttributeSet
import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger
import com.aspsine.swipetoloadlayout.SwipeTrigger


// todo 需要做可配置
class LoadMoreFooterView : androidx.appcompat.widget.AppCompatTextView, SwipeTrigger, SwipeLoadMoreTrigger {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onLoadMore() {
        text = "LOADING MORE"
    }

    override fun onPrepare() {
        text = ""
    }

    override fun onMove(yScrolled: Int, isComplete: Boolean, automatic: Boolean) {
        text = if (!isComplete) {
            if (yScrolled <= -height) {
                "RELEASE TO LOAD MORE"
            } else {
                "SWIPE TO LOAD MORE"
            }
        } else {
            "LOAD MORE RETURNING"
        }
    }

    override fun onRelease() {
        text = "LOADING MORE"
    }

    override fun onComplete() {
        text = "COMPLETE"
    }

    override fun onReset() {
        text = ""
    }
}