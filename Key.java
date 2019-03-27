package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.KeyCode;

public class Key {
    private SimpleBooleanProperty pressed;
    private KeyCode code;

    public Key(KeyCode keyCode){
        this.pressed = new SimpleBooleanProperty();
        this.pressed.setValue(false);
        this.code = keyCode;
    }
    public void setPressed(){
        this.pressed.setValue(true);
    }
    public boolean isPressed() {
        return pressed.getValue();
    }

    public void setReleased() {
        this.pressed.setValue(false);
    }

    public KeyCode getCode() {
        return code;
    }

    public void setCode(KeyCode code) {
        this.code = code;
    }
    public SimpleBooleanProperty getPressedProprety(){
        return this.pressed;
    }
}
