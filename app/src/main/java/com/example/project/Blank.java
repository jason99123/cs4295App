package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Blank extends Activity {

    private int canvasWidth;
    private int canvasHeight;
    private int centerPointX;
    private int centerPointY;
    private int rectWidth;
    private int rectHeight;
    private float left ;
    private float right ;
    private float top;
    private float bottom;
    protected boolean sendIntent = true;

    class BlankView extends View {
        public BlankView (Context context) {
            super(context);
        }

        @Override
        protected void onSizeChanged(int newCanvasWidth, int newCanvasHeight, int oldCanvasWidth, int oldCanvasHeight) {
            canvasWidth = newCanvasWidth;
            canvasHeight = newCanvasHeight;
            super.onSizeChanged(newCanvasWidth, newCanvasHeight, oldCanvasWidth, oldCanvasHeight);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.parseColor("#FFFFFF"));
            Paint p = new Paint(Color.RED);
            p.setStyle(Paint.Style.STROKE);
            Point centerPoint = new Point(canvasWidth / 2, canvasHeight / 2);
            rectWidth = canvasWidth / 3;
            rectHeight = canvasWidth / 3;
            centerPointX = centerPoint.x;
            centerPointY = centerPoint.y;
            Log.d("CenterPointX:", String.valueOf(centerPointX));
            Log.d("CenterPointY:", String.valueOf(centerPointY));

            left = centerPoint.x - (rectWidth / 2);
            top = centerPoint.y - (rectHeight / 2);
            right = centerPoint.x + (rectWidth / 2);
            bottom = centerPoint.y + (rectHeight / 2);
            canvas.drawRect(left, top, right, bottom, p);

            if(sendIntent)
            {
                sendIntent = false;
                Intent i = new Intent(getContext(), Cover.class);
                CanvasSetting canvasSetting = new CanvasSetting(centerPointX, centerPointY, rectWidth, rectHeight, canvasWidth, canvasHeight);
                Log.d("CanvasObjectPtX", String.valueOf(canvasSetting.getCenterPointX()));
                Log.d("CanvasObjectPtY", String.valueOf(canvasSetting.getCenterPointY()));
                i.putExtra("CanvasSetting", canvasSetting);
                startActivity(i);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BlankView(this));
    }
}
