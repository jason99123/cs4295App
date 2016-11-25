package com.example.project;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chokilo3 on 11/16/2016.
 */
public class CanvasSetting implements Parcelable
{
    private int centerPointX ;
    private int centerPointY;
    private int rectWidth;
    private int rectHeight;
    private int canvasWidth;
    private int canvasHeight;

    public CanvasSetting (int centerPointX, int centerPointY, int rectWidth, int rectHeight, int canvasWidth, int canvasHeight)
    {
        this.centerPointX = centerPointX;
        this.centerPointY = centerPointY;
        this.rectWidth = rectWidth;
        this.rectHeight = rectHeight;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
    }

    // Parcelling part
    public CanvasSetting(Parcel in){
        int[] data = new int[6];

        in.readIntArray(data);
        this.centerPointX = data[0];
        this.centerPointY = data[1];
        this.rectWidth = data[2];
        this.rectHeight = data[3];
        this.canvasWidth = data[4];
        this.canvasHeight = data[5];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(new int[]{this.centerPointX,
                this.centerPointY,
                this.rectWidth,
                this.rectHeight,
                this.canvasWidth,
                this.canvasHeight});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CanvasSetting createFromParcel(Parcel in) {
            return new CanvasSetting(in);
        }

        public CanvasSetting[] newArray(int size) {
            return new CanvasSetting[size];
        }
    };

    public int getCenterPointX(){
        return centerPointX;
    }

    public int getCenterPointY() {
        return centerPointY;
    }

    public int getRectWidth() {
        return rectWidth;
    }

    public int getRectHeight() {
        return rectHeight;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }
}
