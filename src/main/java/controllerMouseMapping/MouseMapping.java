package controllerMouseMapping;

import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.InputEvent;

public class MouseMapping implements ButtonMapping {
    private String controllerButton;
    private String controllerButtonJustPressed;
    private String mouseButton;

    private Robot output;

    private boolean isPressed;

    public MouseMapping(String controllerButton, String controllerButtonJustPressed, String mouseButton, Robot output) {
        this.controllerButton = controllerButton;
        this.controllerButtonJustPressed = controllerButtonJustPressed;
        this.mouseButton = mouseButton;
        this.output = output;
        this.isPressed = false;
    }

    public void map(ControllerState state) {
        try {
            if (state.getClass().getDeclaredField(controllerButtonJustPressed).getBoolean(state)) {
                output.mousePress(InputEvent.class.getDeclaredField(mouseButton).getInt(null));
                isPressed = true;
            } else if (state.getClass().getDeclaredField(controllerButton).getBoolean(state) != isPressed) {
                output.mouseRelease(InputEvent.class.getDeclaredField(mouseButton).getInt(null));
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
