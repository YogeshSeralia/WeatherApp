package com.example.weatherapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.network.model.ListItem

class WeeklyForcastAdapter(var list: List<ListItem>) : RecyclerView.Adapter<WeeklyForcastAdapter.WeekDayVH>() {

    fun setData(list: List<ListItem>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekDayVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weekday_view_holder_layout, parent, false)
        return WeekDayVH(view)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WeekDayVH, position: Int) {

    }


    inner class WeekDayVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
//            itemView.findViewById(R.id.)
        }
    }
}
