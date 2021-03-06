package uk.co.jakestanley.commander.rendering.world.entities;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jake on 07/12/2015.
 */
@Getter
public abstract class Renderable {

    protected static final int DEFAULT_SCALE = 1;

    protected boolean hidden;
    protected Vector3f position;
    protected float rotX, rotY, rotZ;
    protected String identifier;
    protected List<RenderEntity> allRenderEntities;
    protected List<RenderEntity> visibleRenderEntities;

    public Renderable(String identifier, Vector3f position, float rotX, float rotY, float rotZ, boolean hidden){
        this.hidden = hidden;
        this.identifier = identifier;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.allRenderEntities = new ArrayList<RenderEntity>();
        this.visibleRenderEntities = new ArrayList<RenderEntity>();
    }

    public void show(){
        hidden = false;
    }

    public void hide(){
        hidden = true;
    }

    public boolean hasVisibleRenderEntities(){
        return (visibleRenderEntities.size() > 0);
    }

    public void updatePosition(Vector3f position){
        for (Iterator<RenderEntity> iterator = allRenderEntities.iterator(); iterator.hasNext(); ) {
            RenderEntity next = iterator.next();
            next.setPosition(position);
        }
    }

    protected abstract void loadRenderEntities();

}
