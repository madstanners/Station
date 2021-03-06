package uk.co.jakestanley.commander2d.planner;

import uk.co.jakestanley.commander2d.actions.Action;
import uk.co.jakestanley.commander2d.actions.AttackDoor;
import uk.co.jakestanley.commander2d.actions.Move;
import uk.co.jakestanley.commander2d.actions.OpenDoor;
import uk.co.jakestanley.commander2d.exceptions.ImpossibleGoal;
import uk.co.jakestanley.commander2d.main.Door;
import uk.co.jakestanley.commander2d.main.GameController;
import uk.co.jakestanley.commander2d.mobs.Mob;
import uk.co.jakestanley.commander2d.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * For uk.co.jakestanley.commander2d.mobs moving randomly
 * Created by stanners on 24/05/2015.
 */
public class Random extends Planner {

    public Random(Mob mob){
        super(mob, GOAL_RANDOM);
    }

    @Override
    public void calculate() throws ImpossibleGoal { // TODO need to move some of this stuff into superclass

        // initialising list of list of next uk.co.jakestanley.commander2d.actions
        ArrayList<ArrayList<Action>> options = new ArrayList<ArrayList<Action>>();

        Point point = mob.getPoint();

        ArrayList<Point> moves = Planner.getPossibleMoves((int) point.getX(), (int) point.getY());
        for (Iterator<Point> pointIterator = moves.iterator(); pointIterator.hasNext(); ) {

            // TODO sort
            Point next =  pointIterator.next();
            int tx = (int) next.getX(); // TODO remove this QD crap
            int ty = (int) next.getY(); // TODO replace this quick and dirty business

            Tile currentTile = GameController.mapController.getTile(mob.getPoint()); // TODO replace this quick and dirty business
            if(tx < 0 || ty < 0 || tx > GameController.mapController.getWidth() - 1 || ty > GameController.mapController.getHeight() - 1){
                throw new ImpossibleGoal("Tried to array out of bounds values"); // TODO prevent this from happening
            }

            Tile nextTile = GameController.mapController.getTile(next); // TODO fix this workaround

            if((tx >= 0) && (ty >= 0) && !nextTile.isVoid()){ // TODO also check that the border uk.co.jakestanley.commander2d.tiles aren't too big, as there are upper limits too

                Door door = GameController.mapController.getDoor(currentTile.getPoint(), nextTile.getPoint()); // TODO sort

                ArrayList<Action> actions = new ArrayList<Action>();
                if(door == null){ // if there's no door, go straight through
                    actions.add(new Move(mob, tx, ty));
                } else if(door.isOpen()){ // if the door is open, go straight through
                    actions.add(new Move(mob, tx, ty));
                } else if(mob.canOpen() && !door.isLocked() && door.isEnabled()) { // if the door is shut, but the mob can open it, and it's not locked, and it's enabled open it and go through
                    if (!door.isOpen()) { // if there is a door, check if it's locked
                        actions.add(new OpenDoor(mob, door));
                        actions.add(new Move(mob, tx, ty));
                    } else {
                        actions.add(new Move(mob, tx, ty));
                    }
                } else if(!door.isOpen() && !mob.canOpen()){
                    actions.add(new AttackDoor(mob, door));
                }
                options.add(actions); // TODO get better names for these list variables

            }
        }

        // Clarify legal options
        int count = options.size();
        if(count > 0){
            actions = options.get(GameController.random.nextInt(options.size()));
        } else {
            throw new ImpossibleGoal("Cannot move mob as there are no valid moves. SECOND");
        }

    }

    @Override
    public boolean achieved() {
        return true; // TODO make more dynamic
    }
}
