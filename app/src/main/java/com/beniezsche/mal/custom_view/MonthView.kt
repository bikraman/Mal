package com.beniezsche.mal.custom_view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.beniezsche.mal.model.DateUtil
import java.util.Calendar

class MonthView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    var daysInTheMonth: Array<Int>? = null
    var isCurrentMonth = false
    private val dateTextPaint = Paint()
    private val boxPaint = Paint()
    private val currentDatePaint = Paint()

    private var textBox = Rect()

    private var saturdayAndSundayPaint = Paint()

    private var viewWidth = 0
    private var viewHeight = 0

    private var currentDay = Calendar.getInstance()[Calendar.DAY_OF_MONTH]

    private val dateTextRectangles : ArrayList<Rect> = ArrayList()
    private val weekNamesRectangles : ArrayList<Rect> = ArrayList()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        dateTextRectangles.clear()
        weekNamesRectangles.clear()

        dateTextPaint.textSize = dpToPx(10).toFloat()
        dateTextPaint.color = Color.BLACK

        saturdayAndSundayPaint.textSize = dpToPx(10).toFloat()
        saturdayAndSundayPaint.color = Color.RED

        currentDatePaint.style = Paint.Style.STROKE
        currentDatePaint.color = Color.RED
        currentDatePaint.strokeWidth = 2f

        boxPaint.style = Paint.Style.STROKE
        boxPaint.color = Color.RED

        val numberOfDivisions = 7

        val firstWidth = viewWidth/numberOfDivisions
        val firstHeight = dpToPx(30)
        val firstX = dpToPx(5)
        val firstY = dpToPx(0)

        var left = firstX
        var top = firstY
        var right = firstWidth
        var bottom = dpToPx(30)

        val verticalGap = dpToPx(5)

        var weekNamesCounter = 1
        while (weekNamesCounter <= 7 ) {

            val rect = Rect(left, top, right, bottom)

            weekNamesRectangles.add(rect)

            if (weekNamesCounter % numberOfDivisions == 0) {
                top += dpToPx(30) + verticalGap
                bottom += dpToPx(30) + verticalGap
                left = firstX
                right = firstWidth
            }
            else {
                left += firstWidth
                right += firstWidth
            }

            weekNamesCounter++
        }

        var daysInMonthCounter = 1
        while (daysInMonthCounter <= daysInTheMonth!!.size ) {

            val rect = Rect(left, top, right, bottom)

            dateTextRectangles.add(rect)

            if (daysInMonthCounter % numberOfDivisions == 0) {
                top += dpToPx(30) + verticalGap
                bottom += dpToPx(30) + verticalGap
                left = firstX
                right = firstWidth
            }
            else {
                left += firstWidth
                right += firstWidth
            }
            daysInMonthCounter++
        }

        //draw the days of week
        for ((index,rect) in weekNamesRectangles.withIndex()) {
            //canvas?.drawRect(rect, boxPaint)

            dateTextPaint.measureText(index.toString())
            dateTextPaint.getTextBounds(index.toString(),0, index.toString().length, textBox)
            dateTextPaint.textAlign = Paint.Align.CENTER

            saturdayAndSundayPaint.measureText(index.toString())
            saturdayAndSundayPaint.getTextBounds(index.toString(),0, index.toString().length, textBox)
            saturdayAndSundayPaint.textAlign = Paint.Align.CENTER

            val height = textBox.height()
            val width = textBox.width()

//            Log.d(DateUtil.CURRENT_DEBUG, "${textBox.left} ${textBox.top}")

//            canvas?.drawRect(textBox, textBoxPaint)

            val nameOfDay = getDayOfWeek(index + 1)

            if (nameOfDay == "Sat" || nameOfDay == "Sun")
                canvas?.drawText(nameOfDay, (rect.left + ((rect.right - rect.left)/2)).toFloat(), (rect.top + ((rect.bottom - rect.top)/2)).toFloat(), saturdayAndSundayPaint)
            else
                canvas?.drawText(nameOfDay, (rect.left + ((rect.right - rect.left)/2)).toFloat(), (rect.top + ((rect.bottom - rect.top)/2)).toFloat(), dateTextPaint)
        }

        //draw the dates
        for ((index,rect) in dateTextRectangles.withIndex()) {
            //canvas?.drawRect(rect, boxPaint)

            dateTextPaint.measureText(index.toString())
            dateTextPaint.getTextBounds(index.toString(),0, index.toString().length, textBox)
            dateTextPaint.textAlign = Paint.Align.CENTER

            val height = textBox.height()
            val width = textBox.width()

//            Log.d(DateUtil.CURRENT_DEBUG, "${textBox.left} ${textBox.top}")

//            canvas?.drawRect(textBox, textBoxPaint)

            val date = if (daysInTheMonth?.get(index) == 0) " " else daysInTheMonth?.get(index).toString()

            canvas?.drawText(date, (rect.left + ((rect.right - rect.left)/2)).toFloat(), (rect.top + ((rect.bottom - rect.top)/2)).toFloat(), dateTextPaint)

            if (isCurrentMonth && currentDay == daysInTheMonth?.get(index)) {
                canvas?.drawCircle((rect.left + ((rect.right - rect.left)/2)).toFloat(), (rect.top + ((rect.bottom - rect.top)/2)).toFloat() - 7f, 20f, currentDatePaint )
                canvas?.drawText(date, (rect.left + ((rect.right - rect.left)/2)).toFloat(), (rect.top + ((rect.bottom - rect.top)/2)).toFloat(), dateTextPaint)
            }
            else {
                canvas?.drawText(date, (rect.left + ((rect.right - rect.left)/2)).toFloat(), (rect.top + ((rect.bottom - rect.top)/2)).toFloat(), dateTextPaint)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(DateUtil.CURRENT_DEBUG, "onMeasureCalled" )
    }

    private fun getDayOfWeek(position: Int) : String {

        when(position) {
            1 -> return "Sun"
            2 -> return "Mon"
            3 -> return "Tue"
            4 -> return "Wed"
            5 -> return "Thu"
            6 -> return "Fri"
            7 -> return "Sat"
        }

        return "0"
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        Log.d(DateUtil.CURRENT_DEBUG, "on size changed called")
        super.onSizeChanged(w, h, oldw, oldh)

        viewWidth = w
        viewHeight = h
    }

    private fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

}