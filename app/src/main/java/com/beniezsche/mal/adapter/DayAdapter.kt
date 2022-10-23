package com.beniezsche.mal.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.beniezsche.mal.R
import com.beniezsche.mal.model.DateUtil
import com.beniezsche.mal.model.Month
import com.beniezsche.mal.model.MonthModel
import java.lang.Math.abs
import java.util.ArrayList

class DayAdapter : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    lateinit var daysList: ArrayList<Int>

    private lateinit var context: Context


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DayAdapter.DayViewHolder {
        return DayViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_day,parent,false))
    }

    override fun onBindViewHolder(holder: DayAdapter.DayViewHolder, position: Int) {

        val day = daysList[position]

        if(day == 0)
            holder.tvDay.text = ""
        else
            holder.tvDay.text = day.toString()

        if(DateUtil.getCurrentDay() == day && DateUtil.getCurrentMonth() == position){
            holder.tvDay.setTextColor(Color.WHITE)
            holder.tvDay.background = ContextCompat.getDrawable(context,R.drawable.circular_background)
        }


        holder.tvDay.setOnClickListener {
            Toast.makeText(context,
                "${
                    kotlin.math.abs(
                        DateUtil.getDayOfYear(
                            holder.tvDay.text.toString().toInt(),
                            position
                        ) - DateUtil.getDayOfYear(
                            DateUtil.getCurrentDay(),
                            DateUtil.getCurrentMonth()
                        )
                    )
                } days apart",
                Toast.LENGTH_SHORT).show()

        }

    }

    override fun getItemCount(): Int {
        return daysList.size
    }

    inner class DayViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvDay: TextView = itemView.findViewById(R.id.tv_day)
    }

}