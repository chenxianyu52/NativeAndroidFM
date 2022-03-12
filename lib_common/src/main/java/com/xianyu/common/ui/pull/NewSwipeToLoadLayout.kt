package com.xianyu.common.ui.pull

import android.content.Context
import android.util.AttributeSet
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout

open class NewSwipeToLoadLayout : SwipeToLoadLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    open fun setOnNewRefreshListener(listener: OnNewRefreshListener?) {
        setOnRefreshListener(listener)
    }

    open fun setOnNewLoadMoreListener(listener:OnNewLoadMoreListener?){
        setOnLoadMoreListener(listener)
    }
}