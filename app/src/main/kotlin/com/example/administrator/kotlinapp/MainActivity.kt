package com.example.administrator.kotlinapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var list : ArrayList<String> ? = null
    var layoutManager : GridLayoutManager ? = null
    var adapter : RecyclerAdapter ? = null
    var screenWid = 0//屏幕宽度
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        screenWid = resources.displayMetrics.widthPixels
        list = ArrayList()
        for (i in 1..50) {
          list!!.add(i.toString())
        }
        layoutManager = GridLayoutManager(this, 4)
        layoutManager!!.spanSizeLookup = SpanLookup()
        recyclerview.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        recyclerview.adapter = adapter
        Toast.makeText(this, list!!.size.toString(), Toast.LENGTH_SHORT).show()
    }

    inner class SpanLookup : SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            if(position == 5) {
                return 2
            }
            return 1
        }

    }
    inner class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
        var viewholder : Holder ? = null
        override fun onBindViewHolder(holder: Holder?, position: Int) {
            viewholder = holder
            holder!!.textView!!.text = list!!.get(position)
            if(position == 5){
                holder!!.textView!!.measure(0, 0)
                var layoutParams = holder!!.textView!!.layoutParams as LinearLayout.LayoutParams
                //layoutParams.leftMargin = - (screenWid / 2 - holder.textView!!.measuredWidth) / 2
                layoutParams.leftMargin = 250
                holder!!.textView!!.layoutParams = layoutParams
                holder.testTV!!.layoutParams = layoutParams
            } else {
                holder!!.linearLayout!!.gravity = Gravity.CENTER
            }
            holder!!.linearLayout!!.setPadding(30, 30, 30, 30)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
            var view = LayoutInflater.from(this@MainActivity).inflate(R.layout.layout_item, parent, false)
            return Holder(view)
        }

        override fun getItemCount(): Int {
            return list!!.size
        }

        inner class Holder : RecyclerView.ViewHolder {
            var textView : TextView ? = null
            var linearLayout : LinearLayout ? = null
            var testTV : TextView ? = null
            constructor(itemview : View) : super(itemview) {
                textView = itemview.findViewById(R.id.text) as TextView
                linearLayout = itemview.findViewById(R.id.item_ll) as LinearLayout
                testTV = itemview.findViewById(R.id.test_tv) as TextView
            }
        }
    }
}
