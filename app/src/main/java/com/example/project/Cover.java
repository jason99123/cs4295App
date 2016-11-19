package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Cover extends Activity {
    CanvasSetting canvasSetting;

//    private int canvasWidth ;
//    private int canvasHeight ;
//    private int centerPointX = 0;
//    private int centerPointY = 0;
//    private int rectWidth = 200;
//    private int rectHeight = 200;
//    private float left = 0;
//    private float right = 0;
//    private float top=0;
//    private float bottom =0;
//
//    class BlankView extends View {
//            public BlankView (Context context) {
//                super(context);
//            }
//
//        @Override
//        protected void onSizeChanged(int newCanvasWidth, int newCanvasHeight, int oldCanvasWidth, int oldCanvasHeight) {
//            canvasWidth = newCanvasWidth;
//            canvasHeight = newCanvasHeight;
//            super.onSizeChanged(newCanvasWidth, newCanvasHeight, oldCanvasWidth, oldCanvasHeight);
//        }
//
//        @Override
//        protected void onDraw(Canvas canvas){
//            canvas.drawColor(Color.parseColor("#B35555FF"));
//            Paint p = new Paint(Color.RED);
//            p.setStyle(Paint.Style.STROKE);
////            canvasWidth = canvas.getWidth();
////            canvasHeight = canvas.getHeight();
//            Point centerPoint = new Point(canvasWidth / 2, canvasHeight / 2);
//            centerPointX = centerPoint.x;
//            centerPointY = centerPoint.y;
//            Log.d("CenterPointX:", String.valueOf(centerPointX));
//            Log.d("CenterPointY:", String.valueOf(centerPointY));
//            //CanvasSetting canvasSetting = new CanvasSetting(centerPointX, centerPointY, rectWidth, rectHeight);
//            left = centerPoint.x - (rectWidth / 2);
//            top = centerPoint.y - (rectHeight / 2);
//            right = centerPoint.x + (rectWidth / 2);
//            bottom = centerPoint.y + (rectHeight / 2);
//            canvas.drawRect(left, top, right, bottom, p);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        Bundle data = getIntent().getExtras();
        this.canvasSetting = (CanvasSetting) data.getParcelable("CanvasSetting");
    }

    public void onStart(View v)
    {
        Intent i = new Intent(this,MainActivity.class);
        Log.d("CanvasObjectPtX", String.valueOf(canvasSetting.getCenterPointX()));
        Log.d("CanvasObjectPtY", String.valueOf(canvasSetting.getCenterPointY()));
        i.putExtra("CanvasSetting",canvasSetting);
        TextView t = (TextView) findViewById(R.id.playername);
        MainActivity.PLAYER_NAME = t.getText().toString();
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cover, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
