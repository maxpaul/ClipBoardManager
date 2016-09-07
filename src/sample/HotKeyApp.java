
package sample;

/*
* KeyEventDemo
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CountDownLatch;

public class HotKeyApp {

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(1);
        KeyEventDispatcher dispatcher = new KeyEventDispatcher() {
            // Anonymous class invoked from EDT
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_C)
                    latch.countDown();
                return false;
            }
        };
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(dispatcher);
        try {
            latch.await();  // current thread waits here until countDown() is called
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(dispatcher);
    }
}