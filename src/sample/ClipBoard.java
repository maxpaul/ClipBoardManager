package sample;/**
 * Created by itsab on 2016-09-05.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import org.apache.log4j.Logger;
import sample.log.GlobalKeyboardExample;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.URL;

public class ClipBoard extends Application {

    public ClipBoardController controller;
    private BorderPane mainLayout;
    private Stage primaryStage;
    private static boolean run = true;
    private static boolean crtlC = false;
    private static String prevClip = "";
    static Logger log = Logger.getLogger(GlobalKeyboardExample.class.getName());

    @Override
    public void start(Stage primaryStage) throws IOException {
// Load the GUI. The MainController class will be automagically created and wired up.
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MaxPaul ClipBoard Manager");

        URL location = getClass().getResource("ClipBoard.fxml");
        FXMLLoader loader = new FXMLLoader(location);
        mainLayout = loader.load();
        controller = loader.getController();

        Scene scene = new Scene(mainLayout);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            run = false;
        });
    }
    public static void main(String[] args) {
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();
        log.info("Global keyboard hook successfully started.");
        keyboardHook.addKeyListener(new GlobalKeyAdapter() {
            @Override public void keyPressed(GlobalKeyEvent event) {
                //log.info("event" + event);
                if (event.isControlPressed()) {
                    if (event.getVirtualKeyCode() == 67) {
                        crtlC = true;
                    } else {
                        crtlC = false;
                    }
                }
                if(event.getVirtualKeyCode()== GlobalKeyEvent.VK_ESCAPE)
                    run = false;
            }
            @Override public void keyReleased(GlobalKeyEvent event) {
               // log.info("event" + event);
                if (crtlC) {
                    String clipBoard = getStringFromClipboard();
                    if (!clipBoard.trim().isEmpty()) {
                 //   if (clipBoard.matches(".*\\w.*")){
                        if (prevClip.equals(clipBoard)) {
                            log.info("current clip equal prev clip");
                        } else {
                            log.info(clipBoard);
                            prevClip = clipBoard;
                        }
                    } else {
                        log.info("current clip is empty");
                    }
                    crtlC = false;
                }
            }
        });

        launch(args);

        // keyboard hook thread ends with closing of javaFx app
        try {
            while(run) Thread.sleep(128);
        } catch(InterruptedException e) { /* nothing to do here */ }
        finally { keyboardHook.shutdownHook(); }
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

