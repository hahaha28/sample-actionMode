package com.example.sample.actionmode

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val actionModeCallback = object: ActionMode.Callback{
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when(item?.itemId){
                R.id.delete -> {
                    adapter.deleteAllSelected()
                    Toast.makeText(this@MainActivity,"delete",Toast.LENGTH_SHORT).show()
                    actionMode?.finish()
                    true
                }
                else -> false
            }
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.action_mode,menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
            adapter.clearSelected()
        }

    }

    private var actionMode:ActionMode? = null   // ActionMode
    private lateinit var adapter:MyAdapter      // adapter
    private lateinit var dataList:MutableList<Item>    // 数据集

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 设置Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)

        // 设置RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        dataList = MutableList(20){
            Item("第${it}项")
        }
        adapter = MyAdapter(dataList)
        recyclerView.adapter = adapter

        // 设置点击事件
        adapter.onClickListener = {position:Int,view:View ->

            // 如果处于 ActionMode
            if(actionMode != null){
                adapter.setSelected(position)
                actionMode?.title = "已选择${adapter.getSelected()}项"
            }else {
                // 否则是正常的点击
                Toast.makeText(this, "click$position", Toast.LENGTH_SHORT).show()
            }
        }

        // 设置长按点击事件
        adapter.onLongClickListener = { position: Int, view: View ->
            if(actionMode == null){
                actionMode = this.startSupportActionMode(actionModeCallback)
            }
            adapter.setSelected(position)
            actionMode?.title = "已选择${adapter.getSelected()}项"
        }
    }
}
