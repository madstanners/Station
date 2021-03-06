package uk.co.jakestanley.commander.rendering.world.tools;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.rendering.world.entities.Camera;

/**
 * Created by jp-st on 13/11/2015.
 */
public class Maths {

    // view matrix variables
    public static final float NEAR_PLANE = 0.01f;
    public static final float FAR_PLANE = 1000f;
    public static final float ORTHOGRAPHIC_NEAR_PLANE = 1000f;
    public static final float ORTHOGRAPHIC_FAR_PLANE = 2800f;

    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale){ // only good for uniform scale
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity(); // 1s on diagonal
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
        Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera){
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMatrix, viewMatrix); // apply pitch to source matrix output source matrix
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewMatrix, viewMatrix); // apply yaw
        Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0,0,1), viewMatrix, viewMatrix); // apply roll TODO test this
        Vector3f cameraPosition = camera.getPosition();
        Vector3f negativeCameraPosition = new Vector3f(-cameraPosition.getX(), -cameraPosition.getY(), -cameraPosition.getZ()); // moving world in opposite direction
        Matrix4f.translate(negativeCameraPosition, viewMatrix, viewMatrix);
        return viewMatrix;
    }

    public static Matrix4f createPerspectiveProjectionMatrix(float fieldOfView) {



        Matrix4f perspectiveProjectionMatrix = new Matrix4f();

        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(fieldOfView / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        perspectiveProjectionMatrix.m00 = x_scale;
        perspectiveProjectionMatrix.m11 = y_scale;
        perspectiveProjectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        perspectiveProjectionMatrix.m23 = -1;
        perspectiveProjectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        perspectiveProjectionMatrix.m33 = 0;

        return perspectiveProjectionMatrix;

    }

    public static Matrix4f createOrthographicProjectionMatrix(){

        Matrix4f orthographicProjectionMatrix = new Matrix4f();

        float left = 0.0f;
        float right = Display.getWidth();
        float top = Display.getHeight();
        float bottom = 0.0f;
        float near = ORTHOGRAPHIC_NEAR_PLANE;
        float far = ORTHOGRAPHIC_FAR_PLANE;

        orthographicProjectionMatrix.m00 = 2.0f / (right - left);
        orthographicProjectionMatrix.m01 = 0.0f;
        orthographicProjectionMatrix.m02 = 0.0f;
        orthographicProjectionMatrix.m03 = 0.0f;

        orthographicProjectionMatrix.m10 = 0.0f;
        orthographicProjectionMatrix.m11 = 2.0f / (top - bottom);
        orthographicProjectionMatrix.m12 = 0.0f;
        orthographicProjectionMatrix.m13 = 0.0f;

        orthographicProjectionMatrix.m20 = 0.0f;
        orthographicProjectionMatrix.m21 = 0.0f;
        orthographicProjectionMatrix.m22 = -2.0f / (far - near);
        orthographicProjectionMatrix.m23 = 0.0f;

        orthographicProjectionMatrix.m30 = -(right + left  ) / (right - left  );
        orthographicProjectionMatrix.m31 = -(top   + bottom) / (top   - bottom);
        orthographicProjectionMatrix.m32 = -(far   + near  ) / (far   - near  );
        orthographicProjectionMatrix.m33 = 1.0f;

        orthographicProjectionMatrix = orthographicProjectionMatrix.scale(new Vector3f(10,10,10));

        return orthographicProjectionMatrix;

    }



}
