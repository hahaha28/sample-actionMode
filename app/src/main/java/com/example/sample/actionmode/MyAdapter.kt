package com.example.sample.actionmode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val dataList:MutableList<String>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    /**
     * 用来记录哪些项被点击了
     */
    private val selectedItem:MutableList<Int> = mutableListOf()

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
        holder.textView.text = dataList[position]
        holder.view.isSelected = position in selectedItem
    }

    /**
     * （取消）选中某项
     */
    fun setSelected(position:Int){
        if(position in selectedItem){
            selectedItem.remove(position)
        }else{
            selectedItem.add(position)
        }
        // 为什么这里不用notifyItemChanged()? ,因为亲测用这个
        // 方法的话速度慢一截，即点击之后有一个短暂延迟才会有改变
        // 出乎意料的是notifyDataSetChanged()反而没有延迟
//        notifyItemChanged(position)
        notifyDataSetChanged()
    }

    /**
     * 清除所有选中状态
     */
    fun clearSelected(){
        selectedItem.clear()
        notifyDataSetChanged()
    }

    /**
     * 获取选中的数量
     */
    fun getSelected():Int = selectedItem.size

    /**
     * 删除所有选中项
     */
    fun deleteAllSelected(){
        selectedItem.sort()
        for( i in selectedItem.size-1 downTo 0){
            dataList.removeAt(selectedItem[i])
        }
    }
}