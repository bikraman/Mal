package com.beniezsche.mal.custom_view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.beniezsche.mal.model.DateUtil
import java.util.Calendar
import kotlin.math.ceil
import kotlin.math.round

class MonthView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    lateinit var daysInTheMonth: Array<Int>
    var isCurrentMonth = false
    private val dateTextPaint = Paint()
    private val boxPaint = Paint()
    private val currentDatePaint = Paint()

    private val textBox = Rect()

    private val saturdayAndSundayPaint = Paint()

    private var viewWidth = 0
    private var viewHeight = 0

    private val numberOfDaysInAWeek = 7

    private val defaultCellHeight = 60f //dpToPx(25)
    private val cellMargin = 0f //dpToPx(5)

    private var currentDay = Calendar.getInstance()[Calendar.DAY_OF_MONTH]

    private val dateTextRects : ArrayList<RectF> = ArrayList()
    private val weekNamesRects: ArrayList<RectF> = ArrayList()

    init {

        dateTextPaint.textSize = 20f//dpToPx(10).toFloat()
        dateTextPaint.color = Color.BLACK

        saturdayAndSundayPaint.textSize = 20f //dpToPx(10).toFloat()
        saturdayAndSundayPaint.color = Color.RED

        currentDatePaint.style = Paint.Style.STROKE
        currentDatePaint.color = Color.RED
        currentDatePaint.strokeWidth = 2f

        boxPaint.style = Paint.Style.STROKE
        boxPaint.color = Color.RED
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        //canvas?.drawRect(0F, 0F, viewWidth.toFloat(), viewHeight.toFloat(), boxPaint)

        dateTextRects.clear()
        weekNamesRects.clear()

        val cellWidth: Float = (viewWidth).toFloat()/numberOfDaysInAWeek.toFloat()
        val cellHeight = defaultCellHeight
        val xOfFirstCell  = 0f //dpToPx(5)
        val yOfFirstCell  = 0f //dpToPx(5)

        var left = xOfFirstCell
        var top = yOfFirstCell
        var right = cellWidth
        var bottom = cellHeight

        for (weekNamesCounter in 1 .. numberOfDaysInAWeek) {

            val rect = RectF(left, top, right, bottom)

            weekNamesRects.add(rect)

            if (weekNamesCounter % numberOfDaysInAWeek == 0) {
                top += (cellHeight)
                bottom += (cellHeight)
                left = xOfFirstCell
                right = cellWidth
            }
            else {
                left += cellWidth
                right += cellWidth
            }
        }

        for (daysInMonthCounter in 1..daysInTheMonth.size) {

            val rect = RectF(left, top, right, bottom)

            dateTextRects.add(rect)

            if (daysInMonthCounter % numberOfDaysInAWeek == 0) {
                top += cellHeight //+ cellMargin
                left = xOfFirstCell
                bottom += cellHeight// + cellMargin
                right = cellWidth
            }
            else {
                left += cellWidth
                right += cellWidth
            }

        }

        //draw the days of week
        for ((index,rect) in weekNamesRects.withIndex()) {
            //canvas?.drawRect(rect, boxPaint)

            dateTextPaint.measureText(index.toString())
            dateTextPaint.getTextBounds(index.toString(),0, index.toString().length, textBox)
            dateTextPaint.textAlign = Paint.Align.CENTER

            saturdayAndSundayPaint.measureText(index.toString())
            saturdayAndSundayPaint.getTextBounds(index.toString(),0, index.toString().length, textBox)
            saturdayAndSundayPaint.textAlign = Paint.Align.CENTER

            val nameOfDay = getDayOfWeek(index + 1)

            if (nameOfDay == "Sat" || nameOfDay == "Sun")
                canvas?.drawText(nameOfDay, (rect.left + ((rect.right - rect.left)/2)).toFloat(), (rect.top + ((rect.bottom - rect.top)/2)).toFloat(), saturdayAndSundayPaint)
            else
                canvas?.drawText(nameOfDay, (rect.left + ((rect.right - rect.left)/2)).toFloat(), (rect.top + ((rect.bottom - rect.top)/2)).toFloat(), dateTextPaint)
        }

        //draw the dates
        for ((index,rect) in dateTextRects.withIndex()) {
            //canvas?.drawRect(rect, boxPaint)

            dateTextPaint.measureText(index.toString())
            dateTextPaint.getTextBounds(index.toString(),0, index.toString().length, textBox)
            dateTextPaint.textAlign = Paint.Align.CENTER

            val height = textBox.height()
            val width = textBox.width()

            //Log.d(DateUtil.CURRENT_DEBUG, "${textBox.left} ${textBox.top}")
            Log.d(DateUtil.CURRENT_DEBUG, "left: ${rect.left} top: ${rect.top} right: ${rect.right} bottom: ${rect.bottom}")


            val date = if (daysInTheMonth?.get(index) == 0) " " else daysInTheMonth?.get(index).toString()

            canvas?.drawText(date, (rect.left + ((rect.right - rect.left)/2)), (rect.top + ((rect.bottom - rect.top)/2)), dateTextPaint)

            if (isCurrentMonth && currentDay == daysInTheMonth?.get(index)) {
                canvas?.drawCircle((rect.left + ((rect.right - rect.left)/2)), (rect.top + ((rect.bottom - rect.top)/2)) - 7f, 20f, currentDatePaint )
                canvas?.drawText(date, (rect.left + ((rect.right - rect.left)/2)), (rect.top + ((rect.bottom - rect.top)/2)), dateTextPaint)
            }
            else {
                canvas?.drawText(date, (rect.left + ((rect.right - rect.left)/2)), (rect.top + ((rect.bottom - rect.top)/2)), dateTextPaint)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val totalHeightOfEachCell = defaultCellHeight
        val noOfRowsOfCells = ceil(daysInTheMonth.size.toDouble()/(7).toDouble()).toInt() + 1
        val approximateHeightOfView = ceil((totalHeightOfEachCell * noOfRowsOfCells))

        val heightSpec = MeasureSpec.makeMeasureSpec(approximateHeightOfView.toInt(), MeasureSpec.UNSPECIFIED)

        Log.d(DateUtil.CURRENT_DEBUG, "totalHeightOfEachCell:$totalHeightOfEachCell * noOfRowsOfCells:$noOfRowsOfCells = approximateHeightOfView:$approximateHeightOfView")

        setMeasuredDimension(widthMeasureSpec, heightSpec)

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
        //Log.d(DateUtil.CURRENT_DEBUG, "on size changed called")
        super.onSizeChanged(w, h, oldw, oldh)

        viewWidth = w
        viewHeight = h
    }

    private fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    private fun dpToPx(dp: Int): Float {
        return (dp * Resources.getSystem().displayMetrics.density)
    }

}