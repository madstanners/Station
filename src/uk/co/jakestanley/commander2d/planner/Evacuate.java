package uk.co.jakestanley.commander2d.planner;

import uk.co.jakestanley.commander2d.actions.Move;
import uk.co.jakestanley.commander2d.actions.OpenDoor;
import uk.co.jakestanley.commander2d.exceptions.ImpossibleGoal;
import uk.co.jakestanley.commander2d.main.Door;
import uk.co.jakestanley.commander2d.main.GameController;
import uk.co.jakestanley.commander2d.main.Room;
import uk.co.jakestanley.commander2d.mobs.Mob;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by stanners on 31/05/2015.
 */
public class Evacuate extends Planner {

    public Evacuate(Mob mob){ // current location, target location
        super(mob, GOAL_EVACUATE);
    }

    @Override
    public void calculate() throws ImpossibleGoal { // TODO ensure always moves to closest possible room
        List<Room> rooms = GameController.mapController.getRooms();
        List<Point> path = null;
        loop:
        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) {
            Room next = iterator.next();
            if(!next.isEvacuate()){
                path = GameController.mapController.getTraversiblePath(mob.getPoint(), next.getRandomTile().getPoint()); // TODO expand in here to include non locked doors
                break loop;
            }
        }

        if(path == null){
            throw new ImpossibleGoal("Can't find a path");
        } else {
            for(int i = 0; i < path.size(); i++){
                if(i < path.size() - 1){
                    Point t = path.get(i+1);
                    Door door = GameController.mapController.getDoor(path.get(i), path.get(i+1));
                    if(door != null){
                        actions.add(new OpenDoor(mob, door));
                        actions.add(new Move(mob, (int) t.getX(), (int) t.getY()));
                    } else {
                        actions.add(new Move(mob, (int) t.getX(), (int) t.getY()));
                    }
                }
            }
        }

    }

    @Override
    public boolean achieved() {
        return !mob.getRoom().isEvacuate();
    }
}
