package com.example.sample.actionmode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val dataList:MutableList<Item>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    /**
     * 长按点击事件
     */
    var onLongClickListener : (position:Int,view:View)->Unit = { p: Int, view: View -> }

    /**
     * 点击事件
     */
    var onClickListener : (position:Int,view:View)->Unit = {p:Int,view:View->}

    class ViewHolder(val view:View):RecyclerView.ViewHolder(view){
        val textView:TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view,parent,false)
        val holder = ViewHolder(view)
        holder.view.setOnClickListener {
            onClickListener(holder.adapterPosition,it)
        }
        holder.view.setOnLongClickListener {
            onLongClickListener(holder.adapterPosition,it)
            true
        }
        return holder
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataList[position].text
        holder.view.isSelected = dataList[position].isSelected
    }

    /**
     * （取消）选中某项
     */
    fun setSelected(position:Int){
        dataList[position].isSelected = !dataList[position].isSelected
        notifyItemChanged(position)
    }

    /**
     * 清除所有选中状态
     */
    fun clearSelected(){
        dataList.forEach { it.isSelected = false }
        notifyDataSetChanged()
    }

    /**
     * 获取选中的数量
     */
    fun getSelected():Int {
        var i = 0
        dataList.forEach {
            if(it.isSelected)
                i++
        }
        return i
    }

    /**
     * 删除所有选中项
     */
    fun deleteAllSelected(){
        for( i in dataList.size-1 downTo 0){
            if(dataList[i].isSelected){
                dataList.removeAt(i)
                notifyItemRemoved(i)
            }
        }
    }
}