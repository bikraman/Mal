package com.beniezsche.mal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beniezsche.mal.adapter.MonthViewAdapter
import com.beniezsche.mal.model.DateUtil

class MainActivity : AppCompatActivity() {

    lateinit var rvMonths: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMonths = findViewById(R.id.rv_months)
        rvMonths.setHasFixedSize(true)

//        val monthAdapter = MonthAdapter()
//        monthAdapter.monthList = DateUtil.createMonths(2022)

        val monthViewAdapter = MonthViewAdapter()
        monthViewAdapter.monthList = DateUtil.createMonths(2022)

        rvMonths.adapter = monthViewAdapter

        val linearLayoutManager  = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rvMonths.layoutManager = linearLayoutManager

    }

    override fun onResume() {
        super.onResume()

        if(rvMonths.layoutManager != null){
            rvMonths.layoutManager?.scrollToPosition(DateUtil.getCurrentMonth() - 1)
        }
    }
}