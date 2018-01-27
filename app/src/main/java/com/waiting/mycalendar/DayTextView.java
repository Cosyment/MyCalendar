package com.waiting.mycalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

/**
 * Created by hechao on 2018/1/27.
 */

public class DayTextView extends android.support.v7.widget.AppCompatTextView {

    public boolean mToday = false;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DayTextView(Context context) {
        super(context);
    }

    public DayTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isToday()) {
            canvas.translate(getWidth()/2,getHeight()/2);
            canvas.drawCircle(0,0, getWidth()/2, mPaint);
        }
    }

    public boolean isToday() {
        return mToday;
    }

    public void setToday(boolean today) {
        mToday = today;
    }
}
