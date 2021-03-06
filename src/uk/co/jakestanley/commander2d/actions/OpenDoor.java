package uk.co.jakestanley.commander2d.actions;

import uk.co.jakestanley.commander2d.exceptions.IllegalAction;
import uk.co.jakestanley.commander2d.exceptions.UnnecessaryAction;
import uk.co.jakestanley.commander2d.main.Door;
import uk.co.jakestanley.commander2d.mobs.Mob;

/**
 * Created by stanners on 24/05/2015.
 */
public class OpenDoor extends Action { // TODO some handling for if the door is already open?

    private Door door;

    public OpenDoor(Mob mob, Door door){
        super(mob, OPEN_DOOR);
        this.door = door;
    }

    @Override
    public void execute() throws IllegalAction, UnnecessaryAction {
        if(!door.isOpen()){
            door.toggle();
        } else {
            throw new UnnecessaryAction("Door is already open. This action does not count");
        }
    }
}
