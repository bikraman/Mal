package com.beniezsche.mal.model

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

object DateUtil {

    private val monthNameList = arrayOf("January","February","March","April","May","June","July","August","September","October","November","December")
    private val monthDaysList = arrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

//    private val daysList = ArrayList<Day>()

    private const val NO_OF_MONTHS = 12

    const val CURRENT_DEBUG = "performance_issue"


    fun getCurrentDay(): Int {
        return Calendar.getInstance(Locale.getDefault()).get(Calendar.DAY_OF_MONTH)
    }

    fun getDayOfYear(day: Int, month: Int): Int {

        val cal = Calendar.getInstance(Locale.getDefault())
        cal.set(Calendar.DAY_OF_MONTH,day)
        cal.set(Calendar.MONTH,month)

        return cal.get(Calendar.DAY_OF_YEAR)
    }

    fun getCurrentMonth(): Int {
        return Calendar.getInstance(Locale.getDefault()).get(Calendar.MONTH) + 1
    }

    fun createMonths(year: Int): ArrayList<MonthModel>{

        var i = 0
        val monthList = ArrayList<MonthModel>()

        while (i < 12) {

            val month = MonthModel()

            month.name = monthNameList[i]
            month.days = getDaysFromMonth(i, year, monthDaysList[i])

            monthList.add(month)

            i++
        }

        return monthList
    }

    private fun getMonthStartPosition(month: Int, year: Int): Int {

        val cal = Calendar.getInstance(Locale.getDefault())
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.DAY_OF_MONTH, 1)


        return cal.get(Calendar.DAY_OF_WEEK)

    }

    private fun getDaysFromMonth(month: Int, year: Int, totalDays: Int) : ArrayList<Int> {

        var days = 1
        var actualDay = 1

        val daysList = arrayListOf<Int>()

        val startPos = getMonthStartPosition(month, year)
        while (days < totalDays + startPos){

            if (days < startPos)
                daysList.add(0)
            else {
                daysList.add(actualDay)
                actualDay++
            }
            days++
        }

        return daysList

    }

    private fun divideDaysIntoWeeks(month: Int, year: Int, totalDays: Int) {


        val weekArrayList = ArrayList<String>()





    }


}