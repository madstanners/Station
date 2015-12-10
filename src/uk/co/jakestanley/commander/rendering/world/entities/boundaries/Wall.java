package uk.co.jakestanley.commander.rendering.world.entities.boundaries;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.rendering.world.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.world.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.world.textures.ModelTexture;

/**
 * Created by jake on 10/12/2015.
 */
public class Wall extends Boundary {

    private static final float DEFAULT_ROT_X = 0;
    private static final float DEFAULT_ROT_Y = 0;
    private static final float DEFAULT_ROT_Z = 0;
    private static final float DEFAULT_SCALE = 1;

    public static final float DEFAULT_WIDTH = 5f;
    public static final float DEFAULT_HEIGHT = 20f;

    @Getter private boolean placed;
    @Getter private float round;
    @Getter private Vector2f start;
    @Getter private Vector2f end;

    public Wall(Vector2f start){ // TODO check if placement is valid
        super(new Vector3f(start.getX(), DEFAULT_HEIGHT, start.getX()), DEFAULT_ROT_X, DEFAULT_ROT_Y, DEFAULT_ROT_Z, DEFAULT_SCALE, RenderEntity.UNTEXTURED_MODEL, RenderEntity.SINGLE_MODEL); // TODO remove untextured/textured model attribute
        this.round = DEFAULT_WIDTH;
        this.placed = false;
        Vector2f wallCoordinates = convertToWallCoordinates(start, round);
        this.start = wallCoordinates;
        setEnd(wallCoordinates);
    }

    public Wall(Vector2f start, Vector2f end){
        super(new Vector3f(start.getX(), DEFAULT_HEIGHT, start.getX()), DEFAULT_ROT_X, DEFAULT_ROT_Y, DEFAULT_ROT_Z, DEFAULT_SCALE, RenderEntity.UNTEXTURED_MODEL, RenderEntity.SINGLE_MODEL);
        this.round = DEFAULT_WIDTH;
        this.placed = true;
        this.start = convertToWallCoordinates(start, round);
        setEnd(end);
    }

    public void setEnd(Vector2f end){
        this.end = convertToWallCoordinates(end, round);
        this.rawModel = generateWallModel(start, this.end);
        texturedModel = new TexturedModel(rawModel, new ModelTexture(Main.getGame().loader.loadTexture("test/green")));
    }

    public boolean place(){
        if(!placed){
            placed = false;
            return true;
        }
        return false; // can't place if already placed
    }

    private static RawModel generateWallModel(Vector2f start, Vector2f end){ // TODO height, width variables, etc
        System.out.println("Start: [" + start.getX() + ", " + start.getY() + "] - End: [" + end.getX() + ", " + end.getY() + "]");
//        { new Vector2f(2.5f,2.5f), new Vector2f(-2.5f,2.5f), new Vector2f(-2.5f,-2.5f), new Vector2f(2.5f,-2.5f)};
        Vector2f[] floor2dVertices = {
                new Vector2f(end.getX() + DEFAULT_WIDTH, end.getY() + DEFAULT_WIDTH),
                new Vector2f(end.getX(), end.getY() + DEFAULT_WIDTH),
                new Vector2f(end.getX(), end.getY()), // TODO optimise
                new Vector2f(end.getX() + DEFAULT_WIDTH, end.getY())
        };
        float[] floor3dVertices = generateVertexPositions(floor2dVertices, DEFAULT_HEIGHT);
        return Main.getGame().loader.loadToVAO(floor3dVertices, DEFAULT_INDICES);
    }

    private static Vector2f convertToWallCoordinates(Vector2f realCoordinates, float round){ // TODO consider putting somewhere else?
        Vector2f wallCoordinates = new Vector2f();
        wallCoordinates.setX((float) (round * (Math.round(Math.abs(realCoordinates.getX() / round))))); // TODO offset if working with negative values
        wallCoordinates.setY((float) (round * (Math.round(Math.abs(realCoordinates.getY() / round)))));
        return wallCoordinates;
    }
}
