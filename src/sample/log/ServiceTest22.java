package sample.log;

import org.apache.log4j.Logger;
import org.boris.winrun4j.AbstractService;
import org.boris.winrun4j.EventLog;
import org.boris.winrun4j.ServiceException;

import java.awt.event.KeyEvent;


/**
 * A basic service.
 */
public class ServiceTest22 {
    static Logger log = Logger.getLogger(ServiceTest22.class.getName());
    public static void main(String[] args) {
        int count = 0;
        while (count != -1)

        {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
            }

            if (++count % 10 == 0)
                log.info("Hello this is an info message");
        }


    }
    /** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {
        displayInfo(e, "KEY TYPED: ");
    }

    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
        displayInfo(e, "KEY PRESSED: ");
    }

    /** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) {
        displayInfo(e, "KEY RELEASED: ");
    }

    private void displayInfo(KeyEvent e, String keyStatus){
        System.out.println("a key has been" + keyStatus);
    }

}
