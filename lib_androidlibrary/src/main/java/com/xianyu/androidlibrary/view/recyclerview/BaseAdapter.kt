package com.xianyu.androidlibrary.view.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xianyu.androidlibrary.R
import java.util.*

class BaseAdapter(val context: Context) : RecyclerView.Adapter<BaseViewHolder>(),OnlineReportListCallback.ItemTouchMoveListener {
    private val dataList: MutableList<BaseItemData> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_recycleview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.name.text = dataList[position].name

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    /**
     * 设置数据
     */
    fun setData(sources: List<BaseItemData>) {
        dataList.clear()
        dataList.addAll(sources)
        notifyDataSetChanged()
    }

    data class BaseItemData(val name: String?)

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(dataList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }
}