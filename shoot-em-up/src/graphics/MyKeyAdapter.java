package graphics;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyAdapter extends KeyAdapter {

    private final int[] codes = {
            KeyEvent.VK_UP,
            KeyEvent.VK_DOWN,
            KeyEvent.VK_LEFT,
            KeyEvent.VK_RIGHT,
            KeyEvent.VK_CONTROL,
            KeyEvent.VK_ESCAPE,
            KeyEvent.VK_R,
    };

    private final boolean[] keyStates;
    private final long[] releaseTimeStamps;

    public MyKeyAdapter() {

        keyStates = new boolean[codes.length];
        releaseTimeStamps = new long[codes.length];
    }

    public int getIndexFromKeyCode(int keyCode) {

        for (int i = 0; i < codes.length; i++) {

            if (codes[i] == keyCode) return i;
        }

        return -1;
    }

    public void keyPressed(KeyEvent e) {

        //System.out.println("KeyPressed " + e.getWhen() + " " + System.currentTimeMillis());

        int index = getIndexFromKeyCode(e.getKeyCode());

        if (index >= 0) {

            keyStates[index] = true;
        }
    }

    public void keyReleased(KeyEvent e) {

        //System.out.println("KeyReleased " + e.getWhen() + " " + System.currentTimeMillis());

        int index = getIndexFromKeyCode(e.getKeyCode());

        if (index >= 0) {

            keyStates[index] = false;
            releaseTimeStamps[index] = System.currentTimeMillis();
        }
    }

    public boolean isKeyPressed(int index) {

        boolean keyState = keyStates[index];
        long keyReleaseTime = releaseTimeStamps[index];

        return keyState || System.currentTimeMillis() - keyReleaseTime <= 5;
    }

    public void debug() {

        System.out.print("Key states = {");

        for (int i = 0; i < codes.length; i++) {

            System.out.print(" " + keyStates[i] + (i < (codes.length - 1) ? "," : ""));
        }

        System.out.println(" }");
    }
}