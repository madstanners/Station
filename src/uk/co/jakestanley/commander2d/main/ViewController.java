package uk.co.jakestanley.commander2d.main;

import java.awt.*;

/**
 * Created by stanners on 19/07/2015.
 */
public class ViewController {

    private int viewOffsetX;
    private int viewOffsetY;

    public ViewController(){
        viewOffsetX = 0;
        viewOffsetY = 0;
    }

    public ViewController(int viewOffsetX, int viewOffsetY){
        this.viewOffsetX = viewOffsetX;
        this.viewOffsetY = viewOffsetY;
    }

    public int getViewOffsetY() {
        return viewOffsetY;
    }

    public int getViewOffsetX() {
        return viewOffsetX;
    }

    public Point getViewOffsetPoint(){
        return new Point(getViewOffsetX(), getViewOffsetY());
    }

    public void increaseViewOffsetX(){
        viewOffsetX++;
    }

    public void decreaseViewOffsetX(){
        viewOffsetX--;
    }

    public void increaseViewOffsetY(){
        viewOffsetY++;
    }

    public void decreaseViewOffsetY(){
        viewOffsetY--;
    }

}
