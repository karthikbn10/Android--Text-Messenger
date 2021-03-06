package com.example.android.test1;

/**
 * Created by Karthik on 10/8/2016.
 */
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;


import java.util.Calendar;

import static com.example.android.test1.ClockActivity.is24;

public class HandsOverlay implements DialOverlay {

    private final Drawable mHour;
    private final Drawable mMinute;
    private final boolean mUseLargeFace;
    private float mHourRot;
    private float mMinRot;
    private boolean mShowSeconds;

    public HandsOverlay(Context context, boolean useLargeFace) {
        final Resources r = context.getResources();

        mUseLargeFace = useLargeFace;

        mHour = null;
        mMinute = null;
    }

    public HandsOverlay(Drawable hourHand, Drawable minuteHand) {
        mUseLargeFace = false;

        mHour = hourHand;
        mMinute = minuteHand;
    }

    public HandsOverlay(Context context, int hourHandRes, int minuteHandRes) {
        final Resources r = context.getResources();

        mUseLargeFace = false;

        mHour = r.getDrawable(hourHandRes);
        mMinute = r.getDrawable(minuteHandRes);
    }

    public static float getHourHandAngle(int h, int m) {
        return ClockActivity.is24 ? ((h/48.0f) * 360) % 360 + (m / 60.0f) * 360 / 24.0f : ((h/ 24.0f) * 360) % 360 + (m / 60.0f) * 360 / 12.0f;
    }

    @Override
    public void onDraw(Canvas canvas, int cX, int cY, int w, int h, int hours,int minutes,int seconds,
                       boolean sizeChanged) {

        updateHands(hours,minutes,seconds);

        canvas.save();
        if (!ClockActivity.hourOnTop)
            drawHours(canvas, cX, cY, w, h, hours,minutes,seconds, sizeChanged);
        else
            drawMinutes(canvas, cX, cY, w, h, hours,minutes,seconds, sizeChanged);
        canvas.restore();

        canvas.save();
        if (!ClockActivity.hourOnTop)
            drawMinutes(canvas, cX, cY, w, h, hours,minutes,seconds, sizeChanged);
        else
            drawHours(canvas, cX, cY, w, h, hours,minutes,seconds, sizeChanged);
        canvas.restore();
    }

    private void drawMinutes(Canvas canvas, int cX, int cY, int w, int h, int hours,int minutes,int seconds,
                             boolean sizeChanged) {
        canvas.rotate(mMinRot, cX, cY);

        if (sizeChanged) {
            w = mMinute.getIntrinsicWidth();
            h = mMinute.getIntrinsicHeight();
            mMinute.setBounds(cX - (w / 2), cY - (h / 2), cX + (w / 2), cY + (h / 2));
        }
        mMinute.draw(canvas);
    }

    private void drawHours(Canvas canvas, int cX, int cY, int w, int h, int hours,int minutes,int seconds,
                           boolean sizeChanged) {
        canvas.rotate(mHourRot, cX, cY);

        if (sizeChanged) {
            w = mHour.getIntrinsicWidth();
            h = mHour.getIntrinsicHeight();
            mHour.setBounds(cX - (w / 2), cY - (h / 2), cX + (w / 2), cY + (h / 2));
        }
        mHour.draw(canvas);
    }

    public void setShowSeconds(boolean showSeconds) {
        mShowSeconds = showSeconds;
    }

    private void updateHands(int hours,int minutes,int seconds) {

        final int h = hours;
        final int m = minutes;
        final int s = seconds;

        mHourRot = getHourHandAngle(h, m);
        mMinRot = (m / 60.0f) * 360 + (mShowSeconds ? ((s / 60.0f) * 360 / 60.0f) : 0);
    }
}
