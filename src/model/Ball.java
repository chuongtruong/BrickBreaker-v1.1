package model;

import javafx.scene.paint.Color;
import view.GameViewManager;

public class Ball
{
    private double x, y, xv, yv;
    private Color color;

    public Ball(double x, double y, double xv, double yv)
    {
        this.x = x;
        this.y = y;
        this.xv = xv;
        this.yv = yv;
        color = Color.YELLOW;
    }

    public Ball(double x, double y, double xv, double yv, Color color)
    {
        this.x = x;
        this.y = y;
        this.xv = xv;
        this.yv = yv;
        this.color = color;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
    }
    public double getXV()
    {
        return xv;
    }
    public double getYV()
    {
        return yv;
    }
    public void reverseY()
    {
        yv*=-1;
    }

    public void setBallColor(String c){
        System.out.println(c);
        switch (c){
            case "red":
                this.color = Color.RED;
                break;
            case "green":
                this.color = Color.GREEN;
                break;
            case "blue":
                this.color = Color.BLUE;
                break;
            default:
                break;
        }
    }

    public void changeX()
    {
        xv+= GameViewManager.panelXV;
        if (xv > 5)
        {
            xv = 5;
        }
        if (xv < -5)
        {
            xv = -5;
        }
    }
    public void reverseX()
    {
        xv*=-1;
    }

    public void update()
    {
        x+=xv;
        y+=yv;
    }

    public Color getColor()
    {
        return color;
    }
}