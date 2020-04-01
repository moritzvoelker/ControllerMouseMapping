package controllerMouseMapping;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.util.LinkedList;

public class Main {
    public static final int DELAY = 10;

    private static boolean lastTurn = false;
    private static boolean isActivated = true;

    public static void main(String[] args) throws AWTException {
        ControllerManager controllerManager = new ControllerManager();
        controllerManager.initSDLGamepad();
        Robot output = new Robot();
        output.setAutoDelay(DELAY);
        ControllerState state;

        java.util.List<ButtonMapping> mappings = new LinkedList<>();
        mappings.add(new MouseMapping(Constants.A, Constants.A_JUST_PRESSED, Constants.MB1, output));
        mappings.add(new MouseMapping(Constants.X, Constants.X_JUST_PRESSED, Constants.MB3, output));
        mappings.add(new MouseMapping(Constants.RS, Constants.RS_JUST_PRESSED, Constants.MB2, output));
        mappings.add(new KeyBoardMapping(Constants.B, Constants.B_JUST_PRESSED, Constants.ESC, output));

        do {
            state = controllerManager.getState(0);
            setActivated(state);
            if (isActivated) {
                int dx = (int) (state.leftStickX * (DELAY * 2));
                if (dx >= -(DELAY / 5) && dx <= (DELAY / 5)) {
                    dx = 0;
                }
                int dy = (int) (state.leftStickY * -(DELAY * 2));
                if (dy >= -(DELAY / 5) && dy <= (DELAY / 5)) {
                    dy = 0;
                }
                output.mouseMove(MouseInfo.getPointerInfo().getLocation().x + dx, MouseInfo.getPointerInfo().getLocation().y + dy);
                output.mouseWheel((int) -state.rightStickY);

                for (ButtonMapping mapping: mappings) {
                    mapping.map(state);
                }

                if (state.dpadDownJustPressed) {
                    output.mouseWheel(6);
                } else if (state.dpadUpJustPressed) {
                    output.mouseWheel(-6);
                }
            }
        } while (state.isConnected);
    }

    private static void setActivated(ControllerState state) {
        boolean pressed = state.lb && state.rb && state.leftStickClick && state.rightStickClick;
        if (pressed != lastTurn && !lastTurn) {
            isActivated = !isActivated;
        }
        lastTurn = pressed;
    }
}
