package uk.co.jakestanley.commander2d.gui.inspectors.boxes;

/**
 * Created by stanners on 26/05/2015.
 */
public class MessagePanel {

    private String message;
    private int age;

    public MessagePanel(String message){ // TODO use stack for messages
        this.message = message;
        this.age = 0;
    }

    public void increaseAge(){
        age++;
    }

    public int getAge(){
        return age;
    }

    public String getMessage(){
        return message;
    }

}
