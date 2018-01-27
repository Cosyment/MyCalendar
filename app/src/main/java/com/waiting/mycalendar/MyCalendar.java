package com.waiting.mycalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by hechao on 2018/1/27.
 */

public class MyCalendar extends LinearLayout {

    private LayoutInflater mInflater;
    private Button mBtnPrevious, mBtnNext;
    private TextView mTextDate;
    private GridView mGridView;
    private List<Date> mDates;
    private Calendar mCurrentCalendar;

    public MyCalendar(Context context) {
        super(context);
    }

    public MyCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("WrongConstant")
    private void init(final Context context) {

        mCurrentCalendar = Calendar.getInstance();
        mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.calendar_view, this, true);

        mBtnPrevious = view.findViewById(R.id.btn_pre);
        mBtnNext = view.findViewById(R.id.btn_next);
        mTextDate = view.findViewById(R.id.text_date);
        mGridView = view.findViewById(R.id.grid_view);

        mBtnPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentCalendar.add(Calendar.MONTH, -1);
                drawCalendar(context);
            }
        });

        mBtnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentCalendar.add(Calendar.MONTH, 1);
                drawCalendar(context);
            }
        });

        drawCalendar(context);
    }

    private void drawCalendar(Context context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
        mTextDate.setText(simpleDateFormat.format(mCurrentCalendar.getTime()));

        Calendar calendar = (Calendar) mCurrentCalendar.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int previousDays = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -previousDays);

        mDates = new ArrayList<>();
        int cells = 6 * 7;
        while (mDates.size() < cells) {
            mDates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        mGridView.setAdapter(new CalendarAdapter(context, mDates));

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DayTextView textView = (DayTextView) view;
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
                mTextDate.setText(sdf.format(mDates.get(position)));

                for (int i = 0; i < parent.getCount(); i++) {
                    if (parent.getChildAt(i) instanceof DayTextView) {
                        DayTextView dayTextView = (DayTextView) parent.getChildAt(i);
                        dayTextView.setToday(false);
                    }
                }
                textView.setToday(true);
            }
        });
    }

    private class CalendarAdapter extends ArrayAdapter<Date> {

        public CalendarAdapter(@NonNull Context context, List<Date> dates) {
            super(context, R.layout.calendar_textview, dates);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.calendar_textview, parent, false);
            }
            final DayTextView textView = (DayTextView) convertView;
            final Date date = getItem(position);
            textView.setText(String.valueOf(date.getDate()));

            final Date curDate = new Date();
            if (curDate.getMonth() == date.getMonth() && curDate.getYear() == date.getYear()) {
                if (curDate.getDate() == date.getDate()) {
                    textView.setToday(true);
                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                } else {
                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                }
            } else {
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
            }
            return convertView;
        }
    }
}
