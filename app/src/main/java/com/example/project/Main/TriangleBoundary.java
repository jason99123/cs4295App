package com.example.project.Main;

import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Noob on 11/18/2016.
 */

public class TriangleBoundary extends RectF
{
    Paint p = new Paint();
    public TriangleBoundary(float left, float top, float right, float bottom, Paint p)
    {
        super(left, top, right, bottom);
        this.p = p;
    }

    public Paint getPaint()
    {
        return p;
    }
}
