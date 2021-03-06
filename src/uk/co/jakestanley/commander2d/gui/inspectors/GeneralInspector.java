package uk.co.jakestanley.commander2d.gui.inspectors;

import uk.co.jakestanley.commander2d.gui.inspectors.boxes.InfoBox;
import uk.co.jakestanley.commander2d.gui.inspectors.boxes.MessageBox;
import uk.co.jakestanley.commander2d.gui.inspectors.boxes.MobsBox;

/**
 * Created by stanners on 12/09/2015.
 */
public class GeneralInspector extends Inspector {

    private InfoBox infoBox; // move these into an inspector
    private MobsBox mobsBox;
    private MessageBox messageBox;

    public GeneralInspector(){
        super();
    }


    @Override
    public void init() {

//        infoBox     = new InfoBox();
        mobsBox     = new MobsBox(this, y);
        messageBox  = new MessageBox(this, y + mobsBox.getHeight());

//        infoBox.init();
        mobsBox.init();
        messageBox.init();

        // TODO do this and build relatively
//        infoBox     = new InfoBox();


//        addChild(infoBox);
//        try {
        addChild(mobsBox);
        addChild(messageBox);
//        } catch(ComponentChildSizeException e){
//            e.printStackTrace();
//        }
    }

    @Override
    public void update() {

    }

    @Override
    public void clickAction() {
        // does nothing
    }

}
