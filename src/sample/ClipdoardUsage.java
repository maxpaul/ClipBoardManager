package sample;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClipdoardUsage {

    public static void main(String[] args) {

        // Create a frame
        Frame frame = new Frame("Example Frame");

    /*

* Create a container with a flow layout, which arranges its children

* horizontally and center aligned.

* A container can also be created with a specific layout using

* Panel(LayoutManager) constructor, e.g.

* Panel(new FlowLayout(FlowLayout.RIGHT)) for right alignment

*/
        Panel panel = new Panel();

        // Add a text area in the center of the frame
        final TextArea textArea = new TextArea("This is a sample text...");
        frame.add(textArea, BorderLayout.CENTER);

        // Add several buttons to the container
        Button copyToClipboardButton = new Button("Copy to clipboard");
        Button pasteFromClipboardButton = new Button("Paste from clipboard");
        panel.add(copyToClipboardButton);
        panel.add(pasteFromClipboardButton);

        // Add action listener to copyToClipboardButton for copying textArea content to clipboard
        copyToClipboardButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent evt) {

                setStringToClipboard(textArea.getText());

            }
        });

        // Add action listener to pasteFromClipboardButton for pasting clipboard content to textArea
        pasteFromClipboardButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent evt) {

                textArea.setText(getStringFromClipboard());

            }
        });

        // Add the container to the bottom of the frame
        frame.add(panel, BorderLayout.SOUTH);

        // Display the frame
        int frameWidth = 300;
        int frameHeight = 300;
        frame.setSize(frameWidth, frameHeight);

        frame.setVisible(true);

    }

    // If a string is on the system clipboard, this method returns it; otherwise it returns null.
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

}