package com.example.android.test1;

/**
 * Created by Karthik on 10/8/2016.
 */
import java.util.Calendar;

import android.graphics.Canvas;

public interface DialOverlay {

    public abstract void onDraw(Canvas canvas, int cX, int cY, int w, int h, int hours,int minutes,int seconds,
                                boolean sizeChanged);

}
