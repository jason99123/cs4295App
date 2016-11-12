package com.example.chunhliu9.scoreboard;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private List<Items> item = new ArrayList<Items>();
    private ListView listView;
    private CustomListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase DB = null;
        //create DB
        DB = this.openOrCreateDatabase("leaderboard", MODE_PRIVATE, null);

        DB.execSQL("CREATE TABLE IF NOT EXISTS scores (name TEXT, score TEXT);");

        Cursor cursor = DB.rawQuery("SELECT * FROM scores", null);
        // add one entry example
        DB.execSQL("INSERT INTO scores (name, score) VALUES ('Andy', '7');");
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



}
