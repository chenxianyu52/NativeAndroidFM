package com.xianyu.common.ui.pull

import android.content.Context
import android.util.AttributeSet
import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger
import com.aspsine.swipetoloadlayout.SwipeTrigger


class RefreshHeaderView : androidx.appcompat.widget.AppCompatTextView, SwipeRefreshTrigger, SwipeTrigger {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onRefresh() {
        text = "REFRESHING"
    }

    override fun onPrepare() {
        text = ""
    }

    override fun onMove(yScrolled: Int, isComplete: Boolean, automatic: Boolean) {
        text = if (!isComplete) {
            if (yScrolled >= height) {
                "RELEASE TO REFRESH"
            } else {
                "SWIPE TO REFRESH"
            }
        } else {
            "REFRESH RETURNING"
        }
    }

    override fun onRelease() {}
    override fun onComplete() {
        text = "COMPLETE"
    }

    override fun onReset() {
        text = ""
    }
}