package com.beniezsche.mal.adapter

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.beniezsche.mal.R
import com.beniezsche.mal.model.DateUtil
import com.beniezsche.mal.model.Day
import com.beniezsche.mal.model.MonthModel

class MonthAdapter: RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {

    var monthList = ArrayList<MonthModel>()
    var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    private var gridSpacingItemDecoration: GridSpacingItemDecoration = GridSpacingItemDecoration(7, 14, true)


    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): MonthAdapter.MonthViewHolder {
        return MonthViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_month, parent, false))
    }

    override fun onBindViewHolder(holder: MonthAdapter.MonthViewHolder, position: Int) {

        val month = monthList[position]
        holder.tvMonth.text = month.name

        val gridLayoutManager = GridLayoutManager(holder.itemView.context, 7 )

        holder.rvDays.layoutManager = gridLayoutManager


        if(holder.rvDays.itemDecorationCount == 0)
            holder.rvDays.addItemDecoration(gridSpacingItemDecoration)


        Log.d("item decorators added", "pos: $position = " + holder.rvDays.itemDecorationCount)



        (holder.rvDays.adapter as DayAdapter).daysList = month.days!!
//        (holder.rvDays.adapter as DayAdapter).notifyDataSetChanged()

//        val dayGridViewadapter = DayGridViewAdapter(holder.itemView.context, month.days)
//        holder.gvDays.adapter = dayGridViewadapter

    }

    override fun getItemCount(): Int {
        return 12
    }

    inner class MonthViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvMonth: TextView = itemView.findViewById(R.id.tv_month)
        val rvDays: RecyclerView = itemView.findViewById(R.id.rv_days)
        val gvDays: GridView = itemView.findViewById(R.id.gv_days)

        init {
            val gridLayoutManager = GridLayoutManager(itemView.context, 7 )

            rvDays.layoutManager = gridLayoutManager

            if(rvDays.itemDecorationCount == 0)
                rvDays.addItemDecoration(gridSpacingItemDecoration)


            Log.d("item decorators added", "pos: $position = " + rvDays.itemDecorationCount)

            val dayAdapter =  DayAdapter()

            rvDays.adapter = dayAdapter

            rvDays.setRecycledViewPool(viewPool)
        }
    }

    class CustomGridLayoutManager(context: Context, spanCount: Int): GridLayoutManager(context, spanCount) {


        override fun isAutoMeasureEnabled(): Boolean {
            return false
        }
    }

    inner class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }


}