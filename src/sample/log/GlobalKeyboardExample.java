/**
 * Copyright (c) 2016 Kristian Kraljic
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package sample.log;


import javafx.scene.input.Clipboard;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import org.apache.log4j.Logger;


import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;


public class GlobalKeyboardExample {

	private static boolean run = true;
	private static boolean crtlC = false;
	static Logger log = Logger.getLogger(GlobalKeyboardExample.class.getName());

	public static void main(String[] args) {
		// might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails 
		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();

		log.info("Global keyboard hook successfully started, press [escape] key to shutdown.");
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			@Override public void keyPressed(GlobalKeyEvent event) {
				log.info("event" + event);
				if (event.isShiftPressed()) {
					if (event.getVirtualKeyCode() == 90) {
						javafx.application.Application.launch(sample.ClipBoard.class);
					}
				} else {
					if (event.isControlPressed()) {
						if (event.getVirtualKeyCode() == 67) {
							crtlC = true;

						} else {
							crtlC = false;
						}
					}
				}

				if(event.getVirtualKeyCode()== GlobalKeyEvent.VK_ESCAPE)
					run = false;
			}
			@Override public void keyReleased(GlobalKeyEvent event) {
				log.info("event" + event);


				if (crtlC) {
					String clipBoard = getStringFromClipboard();
					log.info(clipBoard);
					crtlC = false;
				}
				/*
				char c = event.getKeyChar();

				int tran = event.getTransitionState();
				int virt = event.getVirtualKeyCode();
				boolean b = event.isControlPressed();
				log.info("char " +c);
				log.info("tran " +tran);
				log.info("virt " +virt);
				log.info("contorl " +b);
				*/

			}

		});

		try {

			while(run) Thread.sleep(64);

		} catch(InterruptedException e) { /* nothing to do here */ }
		  finally { keyboardHook.shutdownHook(); }
	}
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