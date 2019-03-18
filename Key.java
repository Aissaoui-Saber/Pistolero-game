package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.KeyCode;

public class Key {
    private SimpleBooleanProperty pressed;
    private SimpleBooleanProperty released;
    private KeyCode code;

    public Key(KeyCode keyCode){
        this.pressed = new SimpleBooleanProperty();
        this.released = new SimpleBooleanProperty();
        this.pressed.setValue(false);
        this.released.setValue(false);
        this.code = keyCode;
    }
    public void setPressed(boolean v){
        this.pressed.setValue(v);
    }
    public boolean isPressed() {
        return pressed.getValue();
    }

    public void setReleased(boolean released) {
        this.released.set(released);
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
    public SimpleBooleanProperty getReleasedProperty(){
        return this.released;
    }
}
