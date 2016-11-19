package com.example.project;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by chokilo3 on 11/11/2016.
 */
public class MovingRectangle extends RectF {
    Paint p;
    public MovingRectangle() {
        super();
    }

    public MovingRectangle(float left, float top, float right, float bottom, Paint p) {
        super(left, top, right, bottom);
        this.p = p;
    }

    public MovingRectangle(RectF r) {
        super(r);
    }

    public MovingRectangle(Rect r) {
        super(r);
    }

    public void setTop(float top){
        this.top = top;
    }
    public void setBottom(float bottom){
        this.bottom = bottom;
    }
    public void setRight(float right){
        this.right = right;
    }
    public void setLeft(float left){
        this.left = left;
    }

    public float getLeft()
    {
        return super.left;
    }

    public float getTop()
    {
        return super.top;
    }

    public float getRight()
    {
        return super.right;
    }

    public float getBottom()
    {
        return super.bottom;
    }

    public Paint getPaint()
    {
        return p;
    }
}


