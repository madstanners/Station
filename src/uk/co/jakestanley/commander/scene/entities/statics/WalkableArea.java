package uk.co.jakestanley.commander.scene.entities.statics;

import uk.co.jakestanley.commander.scene.entities.PhysicalEntity;
import uk.co.jakestanley.commander.scene.entities.shapes.Shape;

/**
 * Created by jp-st on 08/11/2015.
 */
public class WalkableArea extends PhysicalEntity {

    public WalkableArea(String id, Shape shape){
        super(id, shape);
    }

//    public WalkableArea(String id, float xLocal, float yLocal, float zLocal, float width, float depth, float height) {
//        super(id, xLocal, yLocal, zLocal, width, depth, height);
//    }

}