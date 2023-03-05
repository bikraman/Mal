package com.beniezsche.mal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beniezsche.mal.adapter.MonthViewAdapter
import com.beniezsche.mal.model.DateUtil
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    lateinit var rvMonths: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMonths = findViewById(R.id.rv_months)
        rvMonths.setHasFixedSize(true)

        val monthViewAdapter = MonthViewAdapter()

        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        monthViewAdapter.monthList = DateUtil.createMonths(currentYear)

        rvMonths.adapter = monthViewAdapter

        val linearLayoutManager  = LinearLayoutManager(this)
        val gridLayoutManager = GridLayoutManager(this, 1)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rvMonths.layoutManager = gridLayoutManager

    }

    override fun onResume() {
        super.onResume()

        if(rvMonths.layoutManager != null){
            rvMonths.layoutManager?.scrollToPosition(DateUtil.getCurrentMonth() - 1)
        }
    }
}