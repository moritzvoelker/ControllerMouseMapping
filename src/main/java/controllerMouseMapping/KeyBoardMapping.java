package controllerMouseMapping;

import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyBoardMapping implements ButtonMapping {
    private String controllerButton;
    private String controllerButtonJustPressed;
    private String key;

    private Robot output;

    private boolean isPressed;

    public KeyBoardMapping(String controllerButton, String controllerButtonJustPressed, String key, Robot output) {
        this.controllerButton = controllerButton;
        this.controllerButtonJustPressed = controllerButtonJustPressed;
        this.key = key;
        this.output = output;
        this.isPressed = false;
    }

    public void map(ControllerState state) {
        try {
            if (state.getClass().getDeclaredField(controllerButtonJustPressed).getBoolean(state)) {
                output.keyPress(KeyEvent.class.getDeclaredField(key).getInt(null));
                isPressed = true;
            } else if (state.getClass().getDeclaredField(controllerButton).getBoolean(state) != isPressed) {
                output.keyRelease(KeyEvent.class.getDeclaredField(key).getInt(null));
                isPressed = false;
            }
        } catch (NoSuchFieldException e) {
            System.out.println("FATAL: Unknown controller button '" + controllerButton + "'");
            System.exit(1);
        } catch (IllegalAccessException e) {
            System.out.println("FATAL: What the hell are you trying to do with '" + controllerButton + "'");
            System.exit(2);
        }
    }
}
