package com.example.project;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.example.project.Main.TriangleBoundary;
import com.rits.cloning.Cloner;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity{
    private int scoreCount = 0;
    private int rectangleCounter = 2;
    CanvasSetting canvasSetting;
    public static String PLAYER_NAME;
    private List<Items> item = new ArrayList<Items>();
    private ListView listView;
    private CustomListAdapter adapter;

    class MyGraphicsView extends View  implements SensorEventListener{
        private SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);;
        private Sensor am = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        private long lastUpdate = 0;
        private float last_x,last_y,last_z;
        private static final int SHAKE_THRESHOLD = 80;
        Point centerPoint;
        Paint topPaint =new Paint();
        Paint bottomPaint =new Paint();
        Paint leftPaint =new Paint();
        Paint rightPaint =new Paint();
        Paint centerPaint =new Paint();
        MovingRectangle centerRect;
        MovingRectangle movingRectFromLeft;
        MovingRectangle movingRectFromRight;
        MovingRectangle movingRectFromTop;
        MovingRectangle movingRectFromBottom;
        ArrayList<MovingRectangle> animatedRectangleList = new ArrayList<>();
        ArrayList<Triangle> triangleList = new ArrayList<>();
        boolean isReadDB = false;

        float left = 0;
        float right = 0;
        float top = 0;
        float bottom = 0;

        public void setDimension()
        {
            centerPoint = new Point(canvasSetting.getCenterPointX(),canvasSetting.getCenterPointY());
            left = canvasSetting.getCenterPointX() - (canvasSetting.getRectWidth() / 2);
            Log.d("Left", String.valueOf(left));
            right = canvasSetting.getCenterPointX() + (canvasSetting.getRectWidth() / 2);
            Log.d("Right", String.valueOf(right));
            top = canvasSetting.getCenterPointY() - (canvasSetting.getRectHeight() / 2);
            Log.d("Top", String.valueOf(top));
            bottom = canvasSetting.getCenterPointY() + (canvasSetting.getRectHeight() / 2);
            Log.d("Bottom", String.valueOf(bottom));
        }

        public void setPaint()
        {
            centerPaint.setColor(Color.TRANSPARENT);
            centerPaint.setStyle(Paint.Style.FILL);
            topPaint.setColor(Color.RED);
            topPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            bottomPaint.setColor(Color.BLUE);
            bottomPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            leftPaint.setColor(Color.GREEN);
            leftPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            rightPaint.setColor(Color.YELLOW);
            rightPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }

        public void setCenterSquare()
        {
            Triangle triangleLeft = new Triangle(centerPoint, canvasSetting.getRectWidth(), "left");
            Triangle triangleRight = new Triangle(centerPoint, canvasSetting.getRectWidth(), "right");
            Triangle triangleTop = new Triangle(centerPoint, canvasSetting.getRectWidth(), "top");
            Triangle triangleBottom = new Triangle(centerPoint, canvasSetting.getRectWidth(), "bottom");
            triangleList.add(triangleLeft);
            triangleList.add(triangleTop);
            triangleList.add(triangleRight);
            triangleList.add(triangleBottom);
        }

        public MyGraphicsView(Context context)
        {
            super(context);
            setDimension();
            setPaint();
            setCenterSquare();
            sm.registerListener(this, am, sm.SENSOR_DELAY_NORMAL);
            centerRect = new MovingRectangle(left,top,right,bottom, centerPaint);
            movingRectFromLeft = new MovingRectangle(0, top+10, 0 + (canvasSetting.getRectWidth()/4), bottom-10, leftPaint);
            movingRectFromRight = new MovingRectangle((canvasSetting.getCanvasWidth()-(canvasSetting.getRectWidth()/4)), top+10, canvasSetting.getCanvasWidth(), bottom-10, rightPaint);
            movingRectFromTop = new MovingRectangle((canvasSetting.getCenterPointX()-(canvasSetting.getRectWidth()/2))+10, 0, (canvasSetting.getCenterPointX()+(canvasSetting.getRectWidth()/2))-10, canvasSetting.getRectHeight()/4, topPaint);
            movingRectFromBottom = new MovingRectangle((canvasSetting.getCenterPointX()-(canvasSetting.getRectWidth()/2))+10, canvasSetting.getCanvasHeight()-(canvasSetting.getRectHeight()/4), (canvasSetting.getCenterPointX()+(canvasSetting.getRectWidth()/2))-10, canvasSetting.getCanvasHeight(), bottomPaint);

            animatedRectangleList.add(movingRectFromLeft);
            animatedRectangleList.add(movingRectFromRight);
            animatedRectangleList.add(movingRectFromTop);
            animatedRectangleList.add(movingRectFromBottom);

            setupAnimateCenter(centerRect);
            setupAnimateFromLeft(movingRectFromLeft);

            this.postDelayed(new Runnable() {
                public void run() {
                    setupAnimateFromRight(movingRectFromRight);
                }
            }, 1000);

            this.postDelayed(new Runnable() {
                public void run() {
                    setupAnimateFromTop(movingRectFromTop);
                }
            }, 4000);

            this.postDelayed(new Runnable() {
                public void run() {
                    setupAnimateFromBottom(movingRectFromBottom);
                }
            }, 8000);

        }

        public void rotate()
        {
            Cloner cloner = new Cloner();
            Triangle tempLeftTriangle = cloner.deepClone(triangleList.get(0));
            cloner = new Cloner();
            Triangle tempTopTriangle = cloner.deepClone(triangleList.get(1));
            cloner = new Cloner();
            Triangle tempRightTriangle = cloner.deepClone(triangleList.get(2));
            cloner = new Cloner();
            Triangle tempBottomTriangle = cloner.deepClone(triangleList.get(3));

                triangleList.get(0).setLeftPoint(tempTopTriangle.getLeftPoint());
                triangleList.get(0).setRightPoint(tempTopTriangle.getRightPoint());
            System.out.println(triangleList.get(0).getLeftPoint().toString());
            System.out.println(triangleList.get(0).getRightPoint().toString());
            System.out.println(triangleList.get(0).getPaint().getColor());
            triangleList.get(0).setNewPath();
            triangleList.get(0).setColor("red");


                triangleList.get(1).setLeftPoint(tempRightTriangle.getLeftPoint());
                triangleList.get(1).setRightPoint(tempRightTriangle.getRightPoint());
            System.out.println(triangleList.get(1).getPaint().getColor());
            triangleList.get(1).setNewPath();
            triangleList.get(1).setColor("yellow");


                triangleList.get(2).setLeftPoint(tempBottomTriangle.getLeftPoint());
                triangleList.get(2).setRightPoint(tempBottomTriangle.getRightPoint());
            triangleList.get(2).setNewPath();
            triangleList.get(2).setColor("blue");


                triangleList.get(3).setLeftPoint(tempLeftTriangle.getLeftPoint());
                triangleList.get(3).setRightPoint(tempLeftTriangle.getRightPoint());
            triangleList.get(3).setNewPath();
            triangleList.get(3).setColor("green");

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int touchX = (int) event.getX();
            int touchY = (int) event.getY();
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    System.out.println("Touching down!");
                        if(centerRect.contains(touchX,touchY)){
                            System.out.println("Touched Center Rectangle, Rotate View!!!.");
                            rotate();
                        }
                    break;
                case MotionEvent.ACTION_UP:
                    System.out.println("Touching up!");
                    break;
                case MotionEvent.ACTION_MOVE:
                    System.out.println("Sliding your finger around on the screen.");
                    break;
            }
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas){
            //canvas.drawColor(Color.TRANSPARENT);
            //canvas.drawRect(centerRect, centerPaint);

            for(Triangle t : triangleList)
            {
                canvas.drawPath(t.getPath(), t.getPaint());
            }

            //Coillision detection
            for(MovingRectangle mr : animatedRectangleList)
            {
                for(Triangle t : triangleList)
                {
                    TriangleBoundary triangleBoundary;
                    if(t.getLeftPoint().x == t.getRightPoint().x)
                        if(t.getLeftPoint().y < t.getRightPoint().y)
                            triangleBoundary= new TriangleBoundary(t.getLeftPoint().x, t.getLeftPoint().y, t.getRightPoint().x+1, t.getRightPoint().y, t.getPaint());
                        else
                            triangleBoundary= new TriangleBoundary(t.getRightPoint().x, t.getRightPoint().y, t.getLeftPoint().x+1, t.getLeftPoint().y, t.getPaint());
                    else
                        if(t.getLeftPoint().x < t.getRightPoint().x)
                            triangleBoundary= new TriangleBoundary(t.getLeftPoint().x, t.getLeftPoint().y, t.getRightPoint().x, t.getRightPoint().y+1, t.getPaint());
                        else
                            triangleBoundary= new TriangleBoundary(t.getRightPoint().x, t.getRightPoint().y, t.getLeftPoint().x, t.getLeftPoint().y+1, t.getPaint());


                    if (!mr.intersect(triangleBoundary)) {
                        canvas.drawRect(mr, mr.getPaint());
                        canvas.drawRect(triangleBoundary, topPaint);
                    } else {
                        if(String.valueOf(mr.getPaint().getColor()).equals(String.valueOf(triangleBoundary.getPaint().getColor())))
                        {
                            mr.setIsCollied(true);
                            canvas.drawRect(mr, mr.getPaint());
                            //System.out.println("Color code: "+String.valueOf(mr.getPaint().getColor())+", "+String.valueOf(triangleBoundary.getPaint().getColor()));
                            if (!mr.isScored()) {
                                scoreCount += 100;
                            }
                                mr.setScored(true);
                            //rectangleCounter -= 1;
                        }
                        else
                        {
                            mr.setIsCollied(true);
                            canvas.drawRect(mr, mr.getPaint());
                            if(!isReadDB) {
                                readDB();
                            }
                            isReadDB = true;
                        }
                        /*
                        if (singleCounter == 0) {
                            canvas.drawRect(mr, centerPaint);
                            Toast.makeText(MainActivity.this, "Intersected!!!", Toast.LENGTH_SHORT)
                                    .show();
                            singleCounter++;
                        }
                        */
                    }
                }
            }
            //Log.d("Moving Rect status",movingRect.toString());
        }

        protected void setupCenterSquare(Point centerPoint, int width) {
            Triangle topTriangle = new Triangle(centerPoint, width, "top");
            Triangle bottomTriangle = new Triangle(centerPoint, width, "bottom");
            Triangle leftTriangle = new Triangle(centerPoint, width, "left");
            Triangle rightTriangle = new Triangle(centerPoint, width, "right");
        }

        protected void setupAnimateCenter(final MovingRectangle animatedRect){
            float translateX = 0.0f;
            float translateY = 0.0f;
            final AnimatorSet rectAnimation = new AnimatorSet();
            ObjectAnimator animateLeft = ObjectAnimator.ofFloat(animatedRect, "left", animatedRect.left, animatedRect.left + translateX);
            animatedRect.setLeft(animatedRect.left + translateX);
            ObjectAnimator animateRight = ObjectAnimator.ofFloat(animatedRect, "right", animatedRect.right, animatedRect.right+translateX);
            animatedRect.setRight(animatedRect.right + translateX);
            ObjectAnimator animateTop = ObjectAnimator.ofFloat(animatedRect, "top", animatedRect.top, animatedRect.top+translateY);
            animatedRect.setTop(animatedRect.top + translateY);
            ObjectAnimator animateBottom = ObjectAnimator.ofFloat(animatedRect, "bottom", animatedRect.bottom, animatedRect.bottom+translateY);
            animatedRect.setBottom(animatedRect.bottom + translateY);
            animateBottom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        postInvalidate();
                }
            });
            rectAnimation.playTogether(animateLeft, animateRight, animateTop, animateBottom);
            rectAnimation.setDuration(50000).start();
        }

        protected void setupAnimateFromRight(final MovingRectangle animatedRect){
            float translateX = -canvasSetting.getRectWidth();
            float translateY = 0.0f;
            final AnimatorSet rectAnimation = new AnimatorSet();
            ObjectAnimator animateLeft = ObjectAnimator.ofFloat(animatedRect, "left", animatedRect.left, animatedRect.left + translateX);
            animatedRect.setLeft(animatedRect.left + translateX);
            ObjectAnimator animateRight = ObjectAnimator.ofFloat(animatedRect, "right", animatedRect.right, animatedRect.right+translateX);
            animatedRect.setRight(animatedRect.right + translateX);
            ObjectAnimator animateTop = ObjectAnimator.ofFloat(animatedRect, "top", animatedRect.top, animatedRect.top+translateY);
            animatedRect.setTop(animatedRect.top + translateY);
            ObjectAnimator animateBottom = ObjectAnimator.ofFloat(animatedRect, "bottom", animatedRect.bottom, animatedRect.bottom+translateY);
            animatedRect.setBottom(animatedRect.bottom + translateY);
            animateBottom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if(animatedRect.getIsCollied())
                    {
                        rectAnimation.cancel();
                    }
                    else
                    {
                        postInvalidate();
                    }
                }
            });

            rectAnimation.playTogether(animateLeft, animateRight, animateTop, animateBottom);
            rectAnimation.setDuration(5000).start();
        }

        protected void setupAnimateFromLeft(final MovingRectangle animatedRect){
            float translateX = canvasSetting.getRectWidth();
            float translateY = 0.0f;
            final AnimatorSet rectAnimation = new AnimatorSet();
            ObjectAnimator animateLeft = ObjectAnimator.ofFloat(animatedRect, "left", animatedRect.left, animatedRect.left + translateX);
            animatedRect.setLeft(animatedRect.left + translateX);
            ObjectAnimator animateRight = ObjectAnimator.ofFloat(animatedRect, "right", animatedRect.right, animatedRect.right+translateX);
            animatedRect.setRight(animatedRect.right + translateX);
            ObjectAnimator animateTop = ObjectAnimator.ofFloat(animatedRect, "top", animatedRect.top, animatedRect.top+translateY);
            animatedRect.setTop(animatedRect.top + translateY);
            ObjectAnimator animateBottom = ObjectAnimator.ofFloat(animatedRect, "bottom", animatedRect.bottom, animatedRect.bottom+translateY);
            animatedRect.setBottom(animatedRect.bottom + translateY);
            animateBottom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if(animatedRect.getIsCollied())
                    {
                        rectAnimation.cancel();
                    }
                    else
                    {
                        postInvalidate();
                    }
                }
            });
            rectAnimation.playTogether(animateLeft, animateRight, animateTop, animateBottom);
            rectAnimation.setDuration(5000).start();
        }

        protected void setupAnimateFromTop(final MovingRectangle animatedRect){
            float translateX = 0.0f;
            float translateY = canvasSetting.getCanvasHeight();
            final AnimatorSet rectAnimation = new AnimatorSet();
            ObjectAnimator animateLeft = ObjectAnimator.ofFloat(animatedRect, "left", animatedRect.left, animatedRect.left + translateX);
            animatedRect.setLeft(animatedRect.left + translateX);
            ObjectAnimator animateRight = ObjectAnimator.ofFloat(animatedRect, "right", animatedRect.right, animatedRect.right+translateX);
            animatedRect.setRight(animatedRect.right + translateX);
            ObjectAnimator animateTop = ObjectAnimator.ofFloat(animatedRect, "top", animatedRect.top, animatedRect.top+translateY);
            animatedRect.setTop(animatedRect.top + translateY);
            ObjectAnimator animateBottom = ObjectAnimator.ofFloat(animatedRect, "bottom", animatedRect.bottom, animatedRect.bottom+translateY);
            animatedRect.setBottom(animatedRect.bottom + translateY);
            animateBottom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if(animatedRect.getIsCollied())
                    {
                        rectAnimation.cancel();
                    }
                    else
                    {
                        postInvalidate();
                    }
                }
            });

            rectAnimation.playTogether(animateLeft, animateRight, animateTop, animateBottom);
            rectAnimation.setDuration(5000).start();
        }

        protected void setupAnimateFromBottom(final MovingRectangle animatedRect){
            float translateX = 0.0f;
            float translateY = -canvasSetting.getCanvasHeight();
            final AnimatorSet rectAnimation = new AnimatorSet();
            ObjectAnimator animateLeft = ObjectAnimator.ofFloat(animatedRect, "left", animatedRect.left, animatedRect.left + translateX);
            animatedRect.setLeft(animatedRect.left + translateX);
            ObjectAnimator animateRight = ObjectAnimator.ofFloat(animatedRect, "right", animatedRect.right, animatedRect.right+translateX);
            animatedRect.setRight(animatedRect.right + translateX);
            ObjectAnimator animateTop = ObjectAnimator.ofFloat(animatedRect, "top", animatedRect.top, animatedRect.top+translateY);
            animatedRect.setTop(animatedRect.top + translateY);
            ObjectAnimator animateBottom = ObjectAnimator.ofFloat(animatedRect, "bottom", animatedRect.bottom, animatedRect.bottom+translateY);
            animatedRect.setBottom(animatedRect.bottom + translateY);
            animateBottom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if(animatedRect.getIsCollied())
                    {
                        rectAnimation.cancel();
                    }
                    else
                    {
                        postInvalidate();
                    }
                }
            });

            rectAnimation.playTogether(animateLeft, animateRight, animateTop, animateBottom);
            rectAnimation.setDuration(5000).start();
        }

        @Override
        public void onSensorChanged(SensorEvent event)
        {

            Sensor mySensor = event.sensor;
            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER){


                long curTime = System.currentTimeMillis();

                if ((curTime - lastUpdate)>500) {
                    long diffTime = (curTime - lastUpdate);
                    lastUpdate = curTime;
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 30000;

                    if (speed > SHAKE_THRESHOLD) {

                        rotate();
                    }
                    last_x = x;
                    last_y = y;
                    last_z = z;
                }
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy){

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle data = getIntent().getExtras();
        this.canvasSetting = (CanvasSetting) data.getParcelable("CanvasSetting");
        Log.d("CanvasSetting", this.canvasSetting.toString());
        setContentView(new MyGraphicsView(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.example.project.R.menu.main, menu);
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
    public void readDB(){
/*
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(this,R.layout.activity_main,null);
        */
        SQLiteDatabase DB = null;
        //create DB
        DB = this.openOrCreateDatabase("leaderboard", MODE_PRIVATE, null);

        DB.execSQL("CREATE TABLE IF NOT EXISTS scores (name TEXT, score TEXT);");

        Cursor cursor = DB.rawQuery("SELECT * FROM scores", null);
        // add one entry example
        DB.execSQL("INSERT INTO scores (name, score) VALUES ('"+PLAYER_NAME+"','"+String.valueOf(scoreCount)+"');");
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.scoreBoard);
        adapter = new CustomListAdapter(this, item);
        listView.setAdapter(adapter);

        cursor = DB.rawQuery("SELECT * FROM scores", null);

        // display the scoreboard
        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                Items items = new Items();

                items.setName(cursor.getString(0));
                items.setScore(cursor.getString(1));

                item.add(items);
                cursor.moveToNext();


            }
        }
        adapter.notifyDataSetChanged();
    }
}
