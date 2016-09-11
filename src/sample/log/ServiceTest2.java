package sample.log;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import org.apache.log4j.Logger;
import org.boris.winrun4j.AbstractService;
import org.boris.winrun4j.EventLog;
import org.boris.winrun4j.ServiceException;

/**
 * A basic service.
 */
public class ServiceTest2 extends AbstractService  {
    static Logger log = Logger.getLogger(ServiceTest2.class.getName());
    private static boolean run = true;
    public int serviceMain(String[] args) throws ServiceException {

        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();

        log.info("Global keyboard hook successfully started, press [escape] key to shutdown.");
        keyboardHook.addKeyListener(new GlobalKeyAdapter() {
            @Override
            public void keyPressed(GlobalKeyEvent event) {
                log.info(event);
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE)
                    run = false;
            }

            @Override
            public void keyReleased(GlobalKeyEvent event) {
                log.info(event);
            }
        });

        try {
            while (run) Thread.sleep(128);
        } catch (InterruptedException e) { /* nothing to do here */ } finally {
            keyboardHook.shutdownHook();
        }
        return 0;
    }
}
