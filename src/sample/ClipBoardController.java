package sample;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Created by advman on 2016-09-06.
 */
public class ClipBoardController {
@FXML
    public TextArea clipText;
    private SimpleStringProperty environment = new SimpleStringProperty();
    @FXML
    private void onKeyPress(ActionEvent event) {
        System.out.println("key pressed ");
    }
    @FXML
    private void onMouseClick(ActionEvent event) {
        System.out.println("mouse clicked ");
    }
    @FXML
    private void onClickPasteFrom(ActionEvent event) {
        String clipBoard = getStringFromClipboard();
        System.out.println("from button pressed " + clipBoard);
        environment.set(clipBoard);
        clipText.textProperty().bind(environmentProperty());
        clipText.textProperty().unbind();
    }

    @FXML
    private void onClickPasteTo(ActionEvent event) {
        setStringToClipboard(clipText.getText().toString());
        System.out.println("to button pressed " + clipText.getText().toString());
    }

    public static String getStringFromClipboard() {
        Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

        try {

            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {

                String text = (String) transferable.getTransferData(DataFlavor.stringFlavor);

                return text;

            }
        } catch (UnsupportedFlavorException e) {

            System.out.println("Clipboard content flavor is not supported " + e.getMessage());
        } catch (IOException e) {

            System.out.println("Clipboard content could not be retrieved " + e.getMessage());
        }
        return null;
    }

    // This method writes a string to the clipboard.
    public static void setStringToClipboard(String stringContent) {
        StringSelection stringSelection = new StringSelection(stringContent);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }
    public StringExpression environmentProperty() { return environment; }

}

