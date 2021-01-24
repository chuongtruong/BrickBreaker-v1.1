package model;

import javafx.scene.paint.Color;

public class Brick {

    private int type;
    private int durability;
    private Color brickColor;
    private boolean alive;


    public Brick(int type)
    {
        alive = true;
        this.type = type;
        initBrick();
    }

    public Brick(boolean f)
    {
        alive = false;
        type = 0;
        brickColor = null;
        durability = 0;
    }

    private void initBrick()
    {
        if (type == 1)
        {
            durability = 1;
            brickColor = Color.BLUE;
        }
        else if (type == 2)
        {
            durability = 2;
            brickColor = Color.RED;
        }
        else if (type == 3)
        {
            durability = 1;
            brickColor = Color.WHITE;
        }
        else if (type == 4)
        {
            durability = 3;
            brickColor = Color.YELLOW;
        }
    }

    private void updateColor()
    {
        if (type != 3)
        {
            if (durability == 1)
            {
                brickColor = Color.BLUE;
            }
            else if (durability == 2)
            {
                brickColor = Color.RED;
            }
        }
    }

    public void update()
    {
        durability-=1;
        if (durability == 0)
        {
            alive = false;
        }
        updateColor();
    }

    public boolean isAlive()
    {
        return alive;
    }

    public Color getColor()
    {
        return brickColor;
    }

    public int getType()
    {
        return type;
    }
}