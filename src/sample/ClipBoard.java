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

import java.io.IOException;
import java.net.URL;

public class ClipBoard extends Application {

    public ClipBoardController controller;
    private BorderPane mainLayout;
    private Stage primaryStage;


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
    }

    public static void main(String[] args) {

        launch(args);
    }
}
