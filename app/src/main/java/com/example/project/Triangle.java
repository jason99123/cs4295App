package com.example.project;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class Triangle 
{
	Point centerPoint;
	Point leftPoint;
	Point rightPoint;
	String position = null;
	Paint paint = new Paint();
	Path path = new Path();


    public Triangle(){}

	public Triangle(Point centerPoint, int width, String position)
	{
		this.position = position;
		this.centerPoint = new Point(centerPoint.x, centerPoint.y);
		this.paint.setStrokeWidth(2);
		this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
		this.paint.setAntiAlias(true);

		if (position.equals("top"))
		{
			this.paint.setColor(Color.RED);
			this.leftPoint = new Point(centerPoint.x - width/2, centerPoint.y - width/2);
			this.rightPoint = new Point(centerPoint.x + width/2, centerPoint.y - width/2);
			
			this.path.setFillType(Path.FillType.EVEN_ODD);
			this.path.moveTo(centerPoint.x,centerPoint.y);
			this.path.lineTo(this.leftPoint.x,this.leftPoint.y);
			this.path.lineTo(this.rightPoint.x,this.rightPoint.y);
			this.path.close();	
		}
		else if (position.equals("bottom"))
		{
			this.paint.setColor(Color.BLUE);
			this.leftPoint = new Point(centerPoint.x - width/2, centerPoint.y + width/2);
			this.rightPoint = new Point(centerPoint.x + width/2, centerPoint.y + width/2);
			
			this.path.setFillType(Path.FillType.EVEN_ODD);
			this.path.moveTo(centerPoint.x,centerPoint.y);
			this.path.lineTo(this.leftPoint.x,this.leftPoint.y);
			this.path.lineTo(this.rightPoint.x,this.rightPoint.y);
			this.path.close();	
		}
		else if (position.equals("left"))
		{
			this.paint.setColor(Color.GREEN);
			this.leftPoint = new Point(centerPoint.x - width/2, centerPoint.y + width/2);
			this.rightPoint = new Point(centerPoint.x - width/2, centerPoint.y - width/2);
			
			this.path.setFillType(Path.FillType.EVEN_ODD);
			this.path.moveTo(centerPoint.x,centerPoint.y);
			this.path.lineTo(this.leftPoint.x,this.leftPoint.y);
			this.path.lineTo(this.rightPoint.x,this.rightPoint.y);
			this.path.close();	
		}
		else if (position.equals("right"))
		{
			this.paint.setColor(Color.YELLOW);
			this.leftPoint = new Point(centerPoint.x + width/2, centerPoint.y + width/2);
			this.rightPoint = new Point(centerPoint.x + width/2, centerPoint.y - width/2);
			
			this.path.setFillType(Path.FillType.EVEN_ODD);
			this.path.moveTo(centerPoint.x,centerPoint.y);
			this.path.lineTo(this.leftPoint.x,this.leftPoint.y);
			this.path.lineTo(this.rightPoint.x,this.rightPoint.y);
			this.path.close();	
		}
	}

	public Path getPath()
	{
		return path;
	}

	public Paint getPaint()
	{
		return paint;
	}

    public Point getLeftPoint()
    {
        return leftPoint;
    }

    public Point getRightPoint()
    {
        return rightPoint;
    }

    public void setLeftPoint(Point leftPoint)
    {
        this.leftPoint = new Point();
        this.leftPoint = leftPoint;
    }

    public void setRightPoint(Point rightPoint)
    {
        this.rightPoint = new Point();
        this.rightPoint = rightPoint;
    }

    public void setNewPath()
    {
        this.path = new Path();
        this.path.setFillType(Path.FillType.EVEN_ODD);
        this.path.moveTo(centerPoint.x,centerPoint.y);
        this.path.lineTo(this.leftPoint.x,this.leftPoint.y);
        this.path.lineTo(this.rightPoint.x,this.rightPoint.y);
        this.path.close();
    }

    public String getPosition()
    {
        return position;
    }

    public void setColor(String color)
    {
        if (color.equals("green"))
            this.getPaint().setColor(Color.GREEN);
        else if (color.equals("yellow"))
            this.getPaint().setColor(Color.YELLOW);
        else if (color.equals("red"))
            this.getPaint().setColor(Color.RED);
        else
            this.getPaint().setColor(Color.BLUE);
    }

}
