package com.beniezsche.mal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beniezsche.mal.R
import com.beniezsche.mal.custom_view.MonthView
import com.beniezsche.mal.model.MonthModel


class MonthViewAdapter: RecyclerView.Adapter<MonthViewAdapter.MonthViewHolder>() {

    var monthList = ArrayList<MonthModel>()

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): MonthViewAdapter.MonthViewHolder {
        return MonthViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_month_custom_view, parent, false))
    }

    override fun onBindViewHolder(holder: MonthViewAdapter.MonthViewHolder, position: Int) {

        val month = monthList[position]
        holder.monthNameTextView.text = month.name

//        Log.d(DateUtil.CURRENT_DEBUG, "${month.name!!} = ${month.days?.size}" )

        holder.monthView.days = month.days?.toTypedArray()

    }

    override fun getItemCount(): Int {
        return monthList.size
    }

    inner class MonthViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val monthView: MonthView = itemView.findViewById(R.id.month_view)
        val monthNameTextView : TextView = itemView.findViewById(R.id.monthNameTextView)
    }
}