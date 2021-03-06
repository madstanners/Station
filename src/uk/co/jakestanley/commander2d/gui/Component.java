package uk.co.jakestanley.commander2d.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 26/05/2015.
 */
public abstract class Component { // TODO "implements Renderable" ?

    protected boolean isVisible;
    protected int x, y, width, height, border;
    protected Color bgCol, fgCol, bdCol, tCol; // background, foreground, border, and text colors
    protected Rectangle rect;
    protected Component parent;
    protected ArrayList<Component> children;

    public Component(Component parent,
                     Color bgCol, Color fgCol, Color bdCol, Color tCol,
                     int x, int y, int width, int height, int border){


        this.isVisible = true;

        this.parent = parent;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        children = new ArrayList<Component>();

        // set colours
        this.border = border;
        this.bgCol = bgCol;
        this.fgCol = fgCol;
        this.bdCol = bdCol;
        this.tCol = tCol;

        // create shapes
        this.rect = new Rectangle(x, y, width, height);

    }

    public abstract void init();

    public abstract void update();

    public abstract void clickAction();

    public abstract void hoverAction();

    public abstract void draw(Graphics screen);

    /**
     * Pass click to children. Do perform this component's click action after
     * @param mouse
     * @return
     */
    public void click(Point mouse){
        // if the mouse is over me
        if(isMouseOver(mouse) && isVisible) {
            // do my click action
            clickAction();
            // then do my children's uk.co.jakestanley.commander2d.actions
            for (Iterator<Component> iterator = children.iterator(); iterator.hasNext(); ) {
                Component next = iterator.next();
                next.click(mouse);
            }
        }
    }

    /**
     * Similar to click. Passes the mouse hover to children
     * @param mouse
     */
    public void hover(Point mouse){
        if(isMouseOver(mouse) && isVisible) {
            // do my hover action
            hoverAction();
            // then do my children's uk.co.jakestanley.commander2d.actions
            for (Iterator<Component> iterator = children.iterator(); iterator.hasNext(); ) {
                Component next = iterator.next();
                next.hover(mouse);
            }
        }
    }

    public void render(Graphics screen) throws Exception {

        if(y < 0){
            throw new Exception("Y coordinate is less than one. Parent has not yet defined this value");
        }

        if(isVisible) {

            // draw container body
            screen.setColor(bgCol);
            screen.fill(rect);
            screen.setColor(bdCol);
            screen.draw(rect);

            // render me
            draw(screen);

            // render my children
            for (Iterator<Component> iterator = children.iterator(); iterator.hasNext(); ) {
                Component next = iterator.next();
                next.render(screen);
            }

        }

    }

    public void addChild(Component c) {// throws ComponentChildSizeException{
        children.add(c);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x){
        this.x = x; // TODO CONSIDER protected only?
        resetRect();
    }

    public void setY(int y) {
        this.y = y;
        resetRect();
    }

    public int getWidth(){
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width){
        this.width = width;
        resetRect();
    }

    public void setHeight(int height){
        this.height = height;
        resetRect();
    }

    public boolean isMouseOver(Point mouse){
        int mouseX = (int) mouse.getX();
        int mouseY = (int) mouse.getY();
        return (x <= mouseX) && (mouseX <= x + width) && (y <= mouseY) && (mouseY <= y + height);
    }

    public boolean isVisible(){
        return isVisible;
    }

    public void show(){
        isVisible = true;
    }

    public void hide(){
        isVisible = false;
    }

    protected void resetRect(){
        this.rect = new Rectangle(x, y, width, height);
    }

}
