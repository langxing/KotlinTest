package com.example.administrator.kotlinapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

class SecondActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var stringList: MutableList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.recyclerview) as RecyclerView
        stringList = ArrayList<String>()
        for (i in 0..49) {
            stringList!!.add(i.toString() + "item")
        }
        mRecyclerView!!.adapter = CustomAdapter()
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
    }

    private inner class CustomAdapter : RecyclerView.Adapter<CustomAdapter.Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val view = LayoutInflater.from(this@SecondActivity).inflate(R.layout.layout_item, parent, false)
            return Holder(view)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.textView.text = stringList!![position]
        }

        override fun getItemCount(): Int {
            return stringList!!.size
        }

        internal inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var textView: TextView

            init {
                textView = itemView.findViewById(R.id.text) as TextView
            }
        }
    }
}
