package map;

import guicomponents.GuiComponent;
import main.ContextController;
import main.Display;
import main.GameController;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import resources.Converter;

import java.awt.*;

/**
 * Created by stanners on 26/07/2015.
 */
public class MouseController implements MouseListener { // TODO improve and abstract

    private boolean dragMode;
    private Point clickPoint, hoverPoint;

    public MouseController(){
        dragMode = false;
    } // TODO check context

    public void setMousePoint(Point mousePoint){

        // Clear map hover objects
        GameController.mapController.clearHoverObjects();

        if(isMouseOverMap(mousePoint) && (GameController.contextController.getContext() == ContextController.CONSTRUCTION)){

            if(!dragMode && GameController.mapController.setHoverDoor(mousePoint)) { // if hover door was set

            } else if(dragMode){
                hoverPoint = Converter.mouseToTile(mousePoint, Converter.OFFSET_ADDED);
                GameController.mapController.setDragSelection(clickPoint, hoverPoint);
            }
        }
    }

    public void setClickPoint(Point mousePoint){

        ContextController cc    = GameController.contextController;
        MapController mc = GameController.mapController;

        // Get focus and click it if it exists
        GuiComponent focus = GameController.guiController.getFocus(); // TODO make less bad
        if(focus != null){
            focus.click(mousePoint);
            return;
        }

        int context = cc.getContext(); // TODO make default context -1

        if((cc.getContext() == ContextController.CONSTRUCTION) && isMouseOverMap(mousePoint)){ // If in construction context and mouse is over map

            if(mc.setHoverDoor(mousePoint)){ // if hover door was set
                mc.getHoverDoor().enable(); // enable the door
            } else { // if door is null, process tile click
                dragMode = true;
                clickPoint = Converter.mouseToTile(mousePoint, Converter.OFFSET_ADDED);
                hoverPoint = clickPoint;
                mc.setDragSelection(clickPoint, clickPoint);
            }
        }

        // Menus should be clickable in any context except dialogs, as arbitrarily, they should block.

    }



    public void setMouseRelease() { // TODO CONSIDER MouseController ?
        if(dragMode){
            GameController.mapController.releaseDrag();
            dragMode = false;
        }
    }

    private boolean isMouseOverMap(Point point){

        int x = (int) point.getX();
        int y = (int) point.getY();
        int xLowerBound = 0; // Display.DISPLAY_START_X; // TODO
        int xUpperBound = Display.LEFT_COLUMN_WIDTH;
        int yLowerBound = Display.TEXT_PANEL_HEIGHT;
        int yUpperBound = Display.DISPLAY_HEIGHT;
        return x >= xLowerBound && x <= xUpperBound && y >= yLowerBound && y <= yUpperBound;
    }

    @Override
    public void mouseWheelMoved(int i) {

    }

    @Override
    public void mouseClicked(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mousePressed(int i, int i1, int i2) {

    }

    @Override
    public void mouseReleased(int i, int i1, int i2) {

    }

    @Override
    public void mouseMoved(int i, int i1, int newX, int newY) {

        Point mousePoint = new Point(newX, newY);


        // TODO make sure that we're in game context (i.e not a menu)



        // Clear map hover objects
        GameController.mapController.clearHoverObjects();
        GameController.mapController.setHoverDoor(mousePoint);
        GameController.mapController.setHoverRoom(mousePoint);

    }

    @Override
    public void mouseDragged(int oldX, int oldY, int newX, int newY) {
//        System.out.println("mouse dragged: [" + oldX + ", " + oldY + "] to [" + newX + ", " + newY + "]"); // doesn't work in a helpful way
    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }

    public Point getMouse() {
        return hoverPoint;
    }
}
