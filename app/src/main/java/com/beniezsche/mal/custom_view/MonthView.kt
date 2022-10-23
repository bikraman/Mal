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

class MonthView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    var days: Array<Int>? = null
    private val paint = Paint()
    private val boxPaint = Paint()


    var textBox = Rect()

    var textBoxPaint = Paint()

    private val dateTextRectangles : ArrayList<Rect> = ArrayList()

    init {
        Log.d(DateUtil.CURRENT_DEBUG, "month view created" )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        dateTextRectangles.clear()


        paint.textSize = dpToPx(10).toFloat()
        paint.color = Color.BLACK

        boxPaint.style = Paint.Style.STROKE;
        boxPaint.color = Color.RED

        textBoxPaint.style = Paint.Style.STROKE;
        textBoxPaint.color = Color.GREEN

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

        var i = 1

        while (i <= days!!.size ) {

            val rect = Rect(left, top, right, bottom)

            dateTextRectangles.add(rect)

            if (i % numberOfDivisions == 0) {
                top += dpToPx(30) + verticalGap
                bottom += dpToPx(30) + verticalGap
                left = firstX
                right = firstWidth
            }
            else {
                left += firstWidth
                right += firstWidth
            }
            i++
        }


        for ((index,rect) in dateTextRectangles.withIndex()) {
//            canvas?.drawRect(rect, boxPaint)


            paint.measureText(index.toString())
            paint.getTextBounds(index.toString(),0, index.toString().length, textBox)
            paint.textAlign = Paint.Align.CENTER

            val height = textBox.height()
            val width = textBox.width()

//            Log.d(DateUtil.CURRENT_DEBUG, "${textBox.left} ${textBox.top}")

//            canvas?.drawRect(textBox, textBoxPaint)

            val date = if (days?.get(index) == 0) " " else days?.get(index).toString()

            canvas?.drawText(date, (rect.left + ((rect.right - rect.left)/2)).toFloat(), (rect.top + ((rect.bottom - rect.top)/2)).toFloat(), paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(DateUtil.CURRENT_DEBUG, "onMeasureCalled" )
//        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
//        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
//        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
//        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
//        var width = paddingLeft + paddingRight
//        var height = paddingTop + paddingBottom
//
//        if (widthMode == MeasureSpec.EXACTLY) {
//            width = widthSize
//        } else {
//            // here really measure the view
//            width = Math.max(width, suggestedMinimumWidth)
//            width = width
//            if (widthMode == MeasureSpec.AT_MOST) width = Math.min(widthSize, width)
//        }
//
//        if (heightMode == MeasureSpec.EXACTLY) {
//            height = heightSize
//        } else {
//            // here really measure the view when its width is known
//            height = Math.max(height, suggestedMinimumHeight)
//            height = height
//            if (heightMode == MeasureSpec.AT_MOST) height = Math.min(height, heightSize)
//        }
//
//        setMeasuredDimension(width, height)
    }



    var viewWidth = 0
    var viewHeight = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        Log.d(DateUtil.CURRENT_DEBUG, "on size changed called")
        super.onSizeChanged(w, h, oldw, oldh)

        viewWidth = w
        viewHeight = h

//        val numberOfDivisions = 7
//
//        val firstWidth = viewWidth/numberOfDivisions
//        val firstHeight = dpToPx(30)
//        val firstX = dpToPx(5)
//        val firstY = dpToPx(0)
//
//        var left = firstX
//        var top = firstY
//        var right = firstWidth
//        var bottom = dpToPx(30)
//
//        val verticalGap = dpToPx(5)
//
//        var i = 1
//
//        while (i <= days!!.size ) {
//
//            val rect = Rect(left, top, right, bottom)
//
//            dateTextRectangles.add(rect)
//
//            if (i % numberOfDivisions == 0) {
//               top += dpToPx(30) + verticalGap
//               bottom += dpToPx(30) + verticalGap
//               left = firstX
//               right = firstWidth
//            }
//            else {
//                left += firstWidth
//                right += firstWidth
//            }
//            i++
//        }

//        Log.d(DateUtil.CURRENT_DEBUG, "boxes : ${dateTextRectangles.size}")

    }

    private fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

}