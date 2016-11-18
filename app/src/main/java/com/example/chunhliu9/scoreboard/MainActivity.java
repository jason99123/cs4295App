package com.example.chunhliu9.scoreboard;

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

// code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125
public class MainActivity extends Activity implements SensorEventListener{

    private List<Items> item = new ArrayList<Items>();
    private ListView listView;
    private CustomListAdapter adapter;
    private SensorManager sm;
    private Sensor am;
    private long lastUpdate = 0;
    private float last_x,last_y,last_z;
    private static final int SHAKE_THRESHOLD = 10;

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        TextView t = (TextView) findViewById(R.id.testmotion);
        t.setText("Undetected.");
        Sensor mySensor = event.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER){


            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate)>50){
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                float speed = Math.abs(x+y+z-last_x-last_y-last_z)/diffTime * 10000;

                if (speed > SHAKE_THRESHOLD){
                    t.setText("Detected.");
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up accelerometer
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        am = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, am, sm.SENSOR_DELAY_NORMAL);

        SQLiteDatabase DB = null;
        //create DB
        DB = this.openOrCreateDatabase("leaderboard", MODE_PRIVATE, null);

        DB.execSQL("CREATE TABLE IF NOT EXISTS scores (name TEXT, score TEXT);");

        Cursor cursor = DB.rawQuery("SELECT * FROM scores", null);
        // add one entry example
        //DB.execSQL("INSERT INTO scores (name, score) VALUES ('Andy', '7');");
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    protected void onPause(){
        super.onPause();
        sm.unregisterListener(this);
    }
    protected void onResume(){
        super.onResume();
        sm.registerListener(this, am, sm.SENSOR_DELAY_NORMAL);
    }


}
