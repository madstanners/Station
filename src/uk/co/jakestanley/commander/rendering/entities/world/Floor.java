package uk.co.jakestanley.commander.rendering.entities.world;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.CommanderGame3D;
import uk.co.jakestanley.commander.rendering.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.RawModel;

/**
 * Created by jake on 03/12/2015.
 */
public class Floor extends RenderEntity {

    private static final Vector2f[] DEFAULT_VERTICES = { new Vector2f(2.5f,2.5f), new Vector2f(-2.5f,2.5f), new Vector2f(-2.5f,-2.5f), new Vector2f(2.5f,-2.5f)};
    private static final int[]      DEFAULT_INDICES = { 0, 1, 2, 3, 0, 2 }; // TODO figure out how to generate these

    private static final float DEFAULT_X = 0;
    private static final float DEFAULT_Y = 0;
    private static final float DEFAULT_Z = 0;
    private static final float DEFAULT_ROT_X = 0;
    private static final float DEFAULT_ROT_Y = 0;
    private static final float DEFAULT_ROT_Z = 0;
    private static final float DEFAULT_SCALE = 1;
    private static final boolean DEFAULT_TEXTURED = false;

    private static final float DEFAULT_HEIGHT = -1f;

    /**
     * Constructor to generate the default floor (testing only, pretty much)
     */
    public Floor(){
        super(new Vector3f(DEFAULT_X, DEFAULT_Y, DEFAULT_Z), DEFAULT_ROT_X, DEFAULT_ROT_Y, DEFAULT_ROT_Z, DEFAULT_SCALE, DEFAULT_TEXTURED);
        rawModel = generateFloorModel(); // generate default floor model
    }

    public Floor(float width, float height){ // TODO render offset for this and other map components for loading from file, for example.
        super(new Vector3f(DEFAULT_X, DEFAULT_Y, DEFAULT_Z), DEFAULT_ROT_X, DEFAULT_ROT_Y, DEFAULT_ROT_Z, DEFAULT_SCALE, DEFAULT_TEXTURED);
        // TODO just translate model
        rawModel = generateFloorModel(width, height);
    }

    public Floor(Vector2f[] vertices, int[] indices){
        super(new Vector3f(DEFAULT_X, DEFAULT_Y, DEFAULT_Z), DEFAULT_ROT_X, DEFAULT_ROT_Y, DEFAULT_ROT_Z, DEFAULT_SCALE, DEFAULT_TEXTURED); // TODO more arguments
        rawModel = generateFloorModel(vertices, indices);
    }

    private static RawModel generateFloorModel(){
        float[] floor3dVertices = generateVertexPositions(DEFAULT_VERTICES);
        return CommanderGame3D.loader.loadToVAO(floor3dVertices, DEFAULT_INDICES);
    }

    /**
     * Generate a square floor
     * @param width
     * @param height
     * @return
     */
    private static RawModel generateFloorModel(float width, float height){
        Vector2f[] floor2dVertices = { new Vector2f(width,height), new Vector2f(0,height), new Vector2f(0,0), new Vector2f(width,0)};
        float[] floor3dVertices = generateVertexPositions(floor2dVertices);
        return CommanderGame3D.loader.loadToVAO(floor3dVertices, DEFAULT_INDICES);
    }

    private static RawModel generateFloorModel(Vector2f[] floor2dVertices, int[] floorIndices){
        float[] floor3dVertices = null;
        if(floor2dVertices.length < 3){
            floor3dVertices = generateVertexPositions(DEFAULT_VERTICES);
        } else {
            floor3dVertices = generateVertexPositions(floor2dVertices);
        }
        return CommanderGame3D.loader.loadToVAO(floor3dVertices, floorIndices); // TODO
    }

    /**
     * Convert 2D vector array to a floor
     * @param positions
     * @return
     */
    private static float[] generateVertexPositions(Vector2f[] positions){
        int positionCount = positions.length;
        float[] vertexPositions = new float[positions.length * 3];
        for (int i = 0; i < positionCount * 3; i += 3) {
            vertexPositions[i] = positions[i/3].getX();
            vertexPositions[i+1] = DEFAULT_HEIGHT;
            vertexPositions[i+2] = positions[i/3].getY();
        }
        return vertexPositions;
    }

}
