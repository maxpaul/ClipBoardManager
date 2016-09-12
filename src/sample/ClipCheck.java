package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.URL;

/**
 * Created by itsab on 2016-09-11.
 */

public class ClipCheck extends Application {
    public ClipBoardController controller;
    private BorderPane mainLayout;
    private Stage primaryStage;
    static Logger log = Logger.getLogger(ClipCheck.class.getName());
    private static String prevClip = "";
    private static String clipBoard = "";
    private static boolean endLess = true;
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
            endLess = false;
        });
    }
    public static void main(String[] args)  {
        log.info("Clipboard check started.");
        // separate non-FX thread
        Runnable r = new Runnable() {
            public void run() {
                do {
                    String clipBoard = getStringFromClipboard();

                    if (!clipBoard.trim().isEmpty()) {
                        if (!prevClip.equals(clipBoard)) {
                            log.info(clipBoard);
                            prevClip = clipBoard;
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                } while (endLess) ;
            }
        };

        new Thread(r).start();

        launch(args);

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
