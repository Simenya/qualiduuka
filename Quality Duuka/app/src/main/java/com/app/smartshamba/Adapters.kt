package com.app.smartshamba

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView


class ItemAdapter(private val context: Context, private val arrayList:ArrayList<ItemDataModels>):BaseAdapter(){

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return position.toLong()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layout = LayoutInflater.from(context).inflate(R.layout.scanned_item_list,parent,false)
        layout.findViewById<TextView>(R.id.codeContentsId).text = arrayList[position].itemName
        layout.findViewById<TextView>(R.id.itemPriceId).text = (arrayList[position].itemPrice)

        return layout
    }

}