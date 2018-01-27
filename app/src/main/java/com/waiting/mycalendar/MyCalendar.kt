package com.waiting.mycalendar

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by hechao on 2018/1/27.
 */

class MyCalendar : LinearLayout {

    private var mInflater: LayoutInflater? = null
    private var mBtnPrevious: Button? = null
    private var mBtnNext: Button? = null
    private var mTextDate: TextView? = null
    private var mGridView: GridView? = null
    private var mDates: MutableList<Date>? = mutableListOf()
    private var mCurrentCalendar: Calendar? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    @SuppressLint("WrongConstant")
    private fun init(context: Context) {

        mCurrentCalendar = Calendar.getInstance()
        mInflater = LayoutInflater.from(context)
        val view = mInflater!!.inflate(R.layout.calendar_view, this, true)

        mBtnPrevious = view.findViewById(R.id.btn_pre)
        mBtnNext = view.findViewById(R.id.btn_next)
        mTextDate = view.findViewById(R.id.text_date)
        mGridView = view.findViewById(R.id.grid_view)

        mBtnPrevious!!.setOnClickListener {
            mCurrentCalendar!!.add(Calendar.MONTH, -1)
            drawCalendar(context)
        }

        mBtnNext!!.setOnClickListener {
            mCurrentCalendar!!.add(Calendar.MONTH, 1)
            drawCalendar(context)
        }

        drawCalendar(context)
    }

    private fun drawCalendar(context: Context) {
        val simpleDateFormat = SimpleDateFormat("dd-MM-YYYY")
        mTextDate!!.text = simpleDateFormat.format(mCurrentCalendar!!.time)

        val calendar = mCurrentCalendar!!.clone() as Calendar
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val previousDays = calendar.get(Calendar.DAY_OF_WEEK) - 1
        calendar.add(Calendar.DAY_OF_MONTH, -previousDays)

        mDates = ArrayList()
        val cells = 6 * 7
        while (mDates!!.size < cells) {
            mDates!!.add(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        mGridView!!.adapter = CalendarAdapter(context, mDates!!)

        mGridView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val textView = view as DayTextView
            val sdf = SimpleDateFormat("dd-MM-YYYY")
            mTextDate!!.text = sdf.format(mDates!![position])

            for (i in 0 until parent.count) {
                if (parent.getChildAt(i) is DayTextView) {
                    val dayTextView = parent.getChildAt(i) as DayTextView
                    dayTextView.isToday = false
                }
            }
            textView.isToday = true
        }
    }

    private inner class CalendarAdapter(context: Context, dates: List<Date>) : ArrayAdapter<Date>(context, R.layout.calendar_textview, dates) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            if (convertView == null) {
                convertView = mInflater!!.inflate(R.layout.calendar_textview, parent, false)
            }
            val textView = convertView as DayTextView?
            val date = getItem(position)
            textView!!.text = date!!.date.toString()

            val curDate = Date()
            if (curDate.month == date.month && curDate.year == date.year) {
                if (curDate.date == date.date) {
                    textView.isToday = true
                    textView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                } else {
                    textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                }
            } else {
                textView.setTextColor(ContextCompat.getColor(context, R.color.gray))
            }
            return convertView!!
        }
    }
}
