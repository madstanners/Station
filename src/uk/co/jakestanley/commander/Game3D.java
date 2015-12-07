package uk.co.jakestanley.commander;

import lombok.Getter;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.gui.GuiController;
import uk.co.jakestanley.commander.input.InputController;
import uk.co.jakestanley.commander.rendering.DisplayManager;
import uk.co.jakestanley.commander.rendering.world.Renderer;
import uk.co.jakestanley.commander.rendering.world.entities.*;
import uk.co.jakestanley.commander.rendering.gui.GuiRenderer;
import uk.co.jakestanley.commander.rendering.world.Loader;
import uk.co.jakestanley.commander.rendering.world.entities.Character;
import uk.co.jakestanley.commander.rendering.world.models.ObjLoader;
import uk.co.jakestanley.commander.rendering.world.shaders.StaticShader;
import uk.co.jakestanley.commander.scene.SceneController;
import uk.co.jakestanley.commander.scene.entities.mobiles.Crewman;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by jp-st on 10/11/2015.
 */
@Getter
public class Game3D {

    private boolean debug;
    private boolean caching;
    private int displayWidth; // TODO allow these to be changed with arguments
    private int displayHeight;

    private Random random;
    private InputController inputController;
    private SceneController sceneController;
    private GuiController guiController;

    public Renderer worldRenderer;
    public GuiRenderer guiRenderer;

    public ObjLoader objLoader; // TODO make private with getters only
    public Loader loader;

    public static StaticShader shader;

    // entities
    private List<Light> lights;
    private List<Renderable> renderables;

    // entities you should probably keep track of
    private Camera camera;
    private RenderEntity floor;
    private Ship ship;
    private Character character;

    public Game3D(boolean debug, boolean caching, int displayWidth, int displayHeight){
        this.debug = debug;
        this.caching = caching;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        // initialise game logic objects
        random = new Random();
        objLoader = new ObjLoader(ObjLoader.ENABLE_CACHING);
        inputController = new InputController();
        sceneController = new SceneController();
        sceneController.addMobileEntity(new Crewman("terry", 0f, 0f, 0f)); // TODO put in method and generate names
        guiController = new GuiController();

        // initialise the renderer objects
        guiRenderer = new GuiRenderer(displayWidth, displayHeight);

    }

    protected void init() {

        DisplayManager.createDisplay();
        loader = new Loader(Loader.ENABLE_CACHING); // requires the OpenGL context
        shader = new StaticShader();
        worldRenderer = new Renderer(shader, Renderer.ORTHOGRAPHIC);
        worldRenderer.init();

        // initialise lights - TODO get from scene controller?
        lights = new ArrayList<Light>();
        lights.add(new Light(new Vector3f(5,30,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(10,30,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(0,100,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(-10,30,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(0,30,-10), new Vector3f(1,1,1)));

        // initialise visible entities
        renderables = new ArrayList<Renderable>();
        floor = new Floor(100, 50); // use default for now // TODO make a proper entity that extends renderable
        ship = new Ship("gatlinburg", new Vector3f(0,0,0));

        // stress testing
        for(int i = 0; i < 1000; i++){
            System.out.println("Initialising character: " + (i+1));
            character = new Character("stan", new Vector3f(random.nextInt(100) - 50,0,random.nextInt(100) - 50));
            renderables.add(character);
        }

        // add renderables
        renderables.add(ship);
        renderables.add(character);

        // create camera
        camera = new Camera(new Vector3f(55,80,155), 35, -45, 0);
    }

    public void update(){
        sceneController.update();
        inputController.update();
        guiController.update();
        worldRenderer.update();
    }

    public void render(){

        camera.move(); // TODO when i sort everything out, maintain this order
        shader.start();

        // load the lights
        for (Iterator<Light> iterator = lights.iterator(); iterator.hasNext(); ) {
            Light next =  iterator.next();
            shader.loadLight(next);
        }

        shader.loadViewMatrix(camera);

        // batch up the render entities
        for (Iterator<Renderable> iterator = renderables.iterator(); iterator.hasNext(); ) {
            Renderable renderable = iterator.next();
            for (Iterator<RenderEntity> it = renderable.getVisibleRenderEntities().iterator(); it.hasNext(); ) {
                RenderEntity renderEntity = it.next();
                worldRenderer.addToRenderQueue(renderEntity);
            }
        }

        // run the render
        worldRenderer.render(shader);

        shader.stop();
        DisplayManager.updateDisplay(); // last part of update loop
    }

    public boolean hasCloseCondition() {
        return Display.isCloseRequested();
    }

    public void addRenderable(Renderable renderable){
        renderables.add(renderable);
    }

    public void close() {
        shader.cleanup();
        loader.cleanup();
        DisplayManager.closeDisplay();
    }

}