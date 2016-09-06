package sample;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

public class GetStringFromClipboard {

    public static void main(String[] args) throws Exception {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        String result = (String) clipboard.getData(DataFlavor.stringFlavor);
        System.out.println("String from Clipboard:" + result);
    }

}