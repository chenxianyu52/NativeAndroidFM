package com.xianyu.androidlibrary.view.recyclerview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


/**
 * 用来完成RecyclerView长按拖拽的关键接口
 * 1.getMovementFlags里面表示设置为上下拖动
 * 2.onSelectedChanged里面表示拖动状态下改变背景色，拖动完成后恢复背景色
 * 3.拖动完成的时候viewHolder的值为空！！！所以要用srcHolder
 */
class OnlineReportListCallback(private val moveListener: ItemTouchMoveListener) :
    ItemTouchHelper.Callback() {
    private val drawable: ColorDrawable
    private var srcHolder: RecyclerView.ViewHolder? = null

    interface ItemTouchMoveListener {
        fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags, ItemTouchHelper.ACTION_STATE_IDLE)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        srcHolder: RecyclerView.ViewHolder,
        targetHolder: RecyclerView.ViewHolder
    ): Boolean {
        this.srcHolder = srcHolder
        return srcHolder.itemViewType == targetHolder.itemViewType && moveListener.onItemMove(
            srcHolder.adapterPosition,
            targetHolder.adapterPosition
        )
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            viewHolder!!.itemView.background = null
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
            srcHolder!!.itemView.background = drawable
        }
    }

    init {
        val rgb: Int = Color.rgb(0xff, 0xff, 0xff)
        drawable = ColorDrawable(rgb)
    }
}