package sample;

import org.apache.log4j.Logger;
import org.boris.winrun4j.AbstractService;
import org.boris.winrun4j.ServiceException;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Created by itsab on 2016-09-11.
 */

public class ClipCheckService extends AbstractService {
    static Logger log = Logger.getLogger(ClipCheckService.class.getName());
    private static String prevClip = "";
    private static String clipBoard = "";
    public int serviceMain(String[] args) throws ServiceException {
        log.info("Clipboard check started.");
        while (!shutdown) {
           // String clipBoard = getStringFromClipboard();
            log.info("inside shutdown");
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            try {
              //  log.info("inside fst try1" + (String) transferable.getTransferData(DataFlavor.stringFlavor));
                log.info("inside fst try2" + transferable.isDataFlavorSupported(DataFlavor.stringFlavor));
                if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    clipBoard = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                    log.info("inside trans if");
                }
            } catch (UnsupportedFlavorException e) {
                log.info("Clipboard content flavor is not supported " + e.getMessage());
            } catch (IOException e) {
                log.info("Clipboard content could not be retrieved " + e.getMessage());
            }
            if (!clipBoard.trim().isEmpty()) {
                if (!prevClip.equals(clipBoard)) {
                    log.info(clipBoard);
                    prevClip = clipBoard;
                }
            }
            try {
                log.info("before thread sleep" + clipBoard);
                Thread.sleep(1000);
                log.info("after thread sleep");
            } catch (InterruptedException ex) {
            }
        }
        return 0;
    }
    //retrieve string from clipboard
    public static String getStringFromClipboard() {
        Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        try {
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String text = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                return text;
            }
        } catch (UnsupportedFlavorException e) {
            log.info("Clipboard content flavor is not supported " + e.getMessage());
        } catch (IOException e) {
            log.info("Clipboard content could not be retrieved " + e.getMessage());
        }
        return null;
    }
}
